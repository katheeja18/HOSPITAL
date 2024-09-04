package HospManagementSystem.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import HospManagementSystem.DbConnection;
import HospManagementSystem.doctors;
import HospManagementSystem.patient;

public class AppointmentModel {
	  public static boolean checkAvailability(int doctorId, String appointmentDate) {
	        try {
	            Connection connection = DbConnector.getConnection();
	            String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
	            PreparedStatement preparedStatement = connection.prepareStatement(query);
	            preparedStatement.setInt(1, doctorId);
	            preparedStatement.setString(2, appointmentDate);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            if (resultSet.next()) {
	                int count = resultSet.getInt(1);
	                return count == 0;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	  public static void viewAppointment(){
	        String query = "select * from appointments";
	        try{
	        	// to display the appointment table
	        	Connection connection = DbConnection.getConnection();
	            PreparedStatement preparedStatement = connection.prepareStatement(query);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            System.out.println("Appointments: ");
	            System.out.println("+------------+-----------+--------------------+");
	            System.out.println("| Patient Id | Doctor Id | Appointment Date   |");
	            System.out.println("+------------+-----------+--------------------+");
	            while(resultSet.next()){
	                int patient_id = resultSet.getInt("patient_id");
	                String doctor_id = resultSet.getString("doctor_id");
	                String appointment_date = resultSet.getString("appointment_date");
	                System.out.printf("| %-10s | %-9s | %-18s |\n", patient_id, doctor_id,appointment_date );
	                System.out.println("+------------+-----------+--------------------+");
	            }

	        }catch (SQLException e){
	            e.printStackTrace();
	        }
	    }
	  public static void bookAppointment() {
		    try {
		        Connection connection = DbConnection.getConnection();
		        System.out.print("Enter Patient Id: ");
		        int patientId = sc.nextInt();
		        System.out.print("Enter Doctor Id: ");
		        int doctorId = sc.nextInt();
		        System.out.print("Enter appointment date (YYYY-MM-DD): ");
		        String appointmentDate = sc.next();
		        LocalDate inDate = LocalDate.parse(appointmentDate);

		        if (patient.getPatientById(patientId) && doctors.getDoctorById(doctorId)) {
		            if (inDate.isAfter(LocalDate.now())) { // Check if appointment date is in the future
		                if (checkAvailability(doctorId, appointmentDate)) { // Check doctor's availability
		                    String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?, ?, ?)";
		                    try {
		                        PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
		                        preparedStatement.setInt(1, patientId);
		                        preparedStatement.setInt(2, doctorId);
		                        preparedStatement.setString(3, appointmentDate);
		                        int rowsAffected = preparedStatement.executeUpdate();

		                        if (rowsAffected > 0) {
		                            System.out.println("Appointment Booked!");
		                        } else {
		                            System.out.println("Failed to Book Appointment!");
		                        }
		                    } catch (SQLException e) {
		                        e.printStackTrace();
		                    }
		                } else {
		                    System.out.println("Doctor not available on this date!!");
		                }
		            } else {
		                System.out.println("Enter a valid future date for appointment!");
		            }
		        } else {
		            System.out.println("Either doctor or patient doesn't exist!!!");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}

}
