package HospManagementSystem.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import HospManagementSystem.DbConnection;

public class PatientModel {
	public static void addPatient(){
		// to add patient 
        System.out.print("Enter Patient Name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter Patient Gender: ");
        String gender = scanner.next();
        System.out.print("Enter username:");
        String uname=scanner.next();
        System.out.println("Enter new password:");
        String pword=scanner.next();
        System.out.println("Enter your phone number:");
        String ph_no=scanner.next();
        scanner.nextLine();
        System.out.println("Enter your Residential Address:");
        String pat_add= scanner.nextLine();
        System.out.println("Enter your D_O_B (YYYY-MM_DD):");
        String D_O_B=scanner.next();
        
        
        try{
        	Connection connection = DbConnection.getConnection();
            String query = "INSERT INTO patients(name, gender,uname,pword,ph_no,pat_add,D_O_B,age) VALUES(?, ?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dob = LocalDate.parse(D_O_B, formatter);
            LocalDate currentDate = LocalDate.now();
            int age = java.time.Period.between(dob, currentDate).getYears();
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, gender);
            preparedStatement.setString(3, uname);
            preparedStatement.setString(4, pword);
            preparedStatement.setString(5, ph_no);
            preparedStatement.setString(6, pat_add);
            preparedStatement.setString(7, D_O_B);
            preparedStatement.setInt(8, age);
            
            int affectedRows = preparedStatement.executeUpdate();
            int id=display();;
            if(affectedRows>0){
            	System.out.println("Patient Added Successfully!!");
            	System.out.println("Your ID is: "+id);
                
            }else{
                System.out.println("Failed to add Patient!!");
            }

        }catch (DateTimeParseException  e){
            e.printStackTrace();}
            catch(SQLException e) {
            	e.printStackTrace(); }

    }
	 public static void viewPatients(){
	    	// to vie the patient table
	        String query = "select * from patients";
	        try{
	        	Connection connection = DbConnection.getConnection();
	            PreparedStatement preparedStatement = connection.prepareStatement(query);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            System.out.println("Patients: ");
	            System.out.println("+------------+--------------------+-----------+-----------------+---------------------+---------+");
	            System.out.println("| Patient Id | Name               | Gender    | Phone Number    | Patient Address     | Age     |");
	            System.out.println("+------------+--------------------+-----------+-----------------+---------------------+---------+");
	            while(resultSet.next()){
	                int id = resultSet.getInt("id");
	                String name = resultSet.getString("name");
	                String gender = resultSet.getString("gender");
	                String ph_no = resultSet.getString("ph_no");
	                String pat_add=resultSet.getString("pat_add");
	                int age=resultSet.getInt("age");
	                System.out.printf("| %-10s | %-18s | %-9s | %-15s | %-19s | %-7s |\n", id, name,gender,ph_no,pat_add,age);
	                System.out.println("+------------+--------------------+-----------+-----------------+---------------------+---------+");
	            }

	        }catch (SQLException e){
	            e.printStackTrace();
	        }
	    }
	 public static void updatePatient(int id, String fieldToUpdate, String newValue) {
		 try {
	            Connection connection = DbConnection.getConnection();
	            String query = "";

	            switch (fieldToUpdate.toLowerCase()) {
	                case "ph_no":
	                    query = "UPDATE patients SET ph_no=? WHERE id=?";
	                    System.out.println();
	                    break;
	                case "pat_add":
	                	System.out.println();
	                    query = "UPDATE patients SET pat_add=? WHERE id=?";
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
	                System.out.println("Patient details updated successfully!");
	            } else {
	                System.out.println("No Patient found with the given ID.");
	            }
	           
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}
	 public static boolean getPatientById(int id){
	    	// to get patient by their id
	        String query = "SELECT * FROM patients WHERE id = ?";
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
}
