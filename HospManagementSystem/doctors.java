package HospManagementSystem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class doctors {
	 static Scanner scanner =new Scanner(System.in);
 public void viewDoctors(){
        String query = "select * from doctors";
        try{
        	// to display doctor table
        	Connection connection = DbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("+------------+--------------------+------------------------+-----------------+--------------------------------------------+---------+");
            System.out.println("| Doctor Id  | Name               | Specialization         | Phone Number    | Patient Address                            | Age     |");
            System.out.println("+------------+--------------------+------------------------+-----------------+--------------------------------------------+---------+");
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                String ph_no = resultSet.getString("ph_number");
                String doc_add=resultSet.getString("doc_add");
                int age=resultSet.getInt("age");
                System.out.printf("| %-10s | %-18s | %-22s | %-15s | %-42s | %-7s |\n", id, name, specialization,ph_no,doc_add,age);
                System.out.println("+------------+--------------------+------------------------+-----------------+--------------------------------------------+---------+");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
     
    }
    public static boolean getDoctorById(int id){
    	//to check if the doctors is avail
        String query = "SELECT * FROM doctors WHERE id = ?";
        try{
        	Connection connection = DbConnection.getConnection();
        	PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }
        }catch (SQLException e){
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
    public static void addDoctor(){
		// to add doctors
        System.out.println("Enter Doctor Name: ");
        String name = scanner.nextLine();
        
        System.out.println("Enter Doctor's Specialization: ");
        String specialization = scanner.nextLine();
        System.out.println("Enter username:");
        String uname=scanner.next();
        System.out.println("Enter new password:");
        String pword=scanner.next();
        System.out.println("Enter Doctor's phone number:");
        String ph_number=scanner.next();
        scanner.nextLine();
        System.out.println("Enter Doctor's Residential Address:");
        String doc_add= scanner.nextLine();
        System.out.println("Enter Doctor's D_O_B (YYYY-MM_DD):");
        String D_O_B=scanner.next();
        try{
        	Connection connection = DbConnection.getConnection();
            String query = "INSERT INTO doctors(name,specialization,uname,pword,ph_number,doc_add,D_O_B,age) VALUES(?,?,?, ?, ?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dob = LocalDate.parse(D_O_B, formatter);
            LocalDate currentDate = LocalDate.now();
            int age = java.time.Period.between(dob, currentDate).getYears();
            
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, specialization);
            preparedStatement.setString(3, uname);
            preparedStatement.setString(4, pword);
            preparedStatement.setString(5, ph_number);
            preparedStatement.setString(6, doc_add);
            preparedStatement.setString(7, D_O_B);
            preparedStatement.setInt(8, age);
            int affectedRows = preparedStatement.executeUpdate();
            int id=dis();;
            if(affectedRows>0){
            	System.out.println("Doctor Added Successfully!!");
            	System.out.println("Your ID is: "+id);
                
            }else{
                System.out.println("Failed to add Doctor!!");
            }

        }catch (DateTimeParseException  e){
            e.printStackTrace();}
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static int dis()
    {
   	 //to display the id generated for doctor
   	 int l=0;
   	 try
   	 {
   		 Connection connection = DbConnection.getConnection();
   		 String query1="select max(id) from doctors";
            PreparedStatement Statement = connection.prepareStatement(query1);
            ResultSet resultSet =Statement.executeQuery();
           if(resultSet.next())
           {
            l=resultSet.getInt(1);
           }
   	 }
   	 catch(SQLException e){
   	            e.printStackTrace();
   	        }
   	return l;
    }
    
    
	public static void updateDoctor(int id, String fieldToUpdate, String newValue) {
		
		 try {
	            Connection connection = DbConnection.getConnection();
	            String query = "";

	            switch (fieldToUpdate.toLowerCase()) {
	                case "ph_number":
	                    query = "UPDATE doctors SET ph_number=? WHERE id=?";
	                    System.out.println();
	                    break;
	                case "doc_add":
	                	System.out.println();
	                    query = "UPDATE doctors SET doc_add=? WHERE id=?";
	                    break;
	                default:
	                    System.out.println("Invalid field to update.");
	                    return;
	            }

	            PreparedStatement statement = connection.prepareStatement(query);

	            // Set the parameters for the prepared statement
	            statement.setString(1, newValue);
	            statement.setInt(2, id);

	            // Execute the update query
	            int rowsUpdated = statement.executeUpdate();

	            if (rowsUpdated > 0) {
	                System.out.println("Doctors details updated successfully!");
	            } else {
	                System.out.println("No Doctors found with the given ID.");
	            }

	            // Close resources
	            statement.close();
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}
	
    }
    
