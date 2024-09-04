package HospManagementSystem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.time.LocalDate;
public class login {
static	Scanner sc= new Scanner(System.in);
	public static void input()
	{
//to get the username and password for login class
System.out.println("Enter Username:");
String username =sc.next();
System.out.println("Enter Password:");
 String password= sc.next();
 DbConnector(username,password);
	}

public static void  DbConnector(String username, String password)
{  
  boolean found=false;
	try
	{  
    try{
    	//for admin 
    	Connection connection = DbConnection.getConnection();
    	String query="select * from admin where username=? AND password=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
        	found=true;
        	boolean f=false;
        	while(!f)
        	{
        		//admin page
        	System.out.println("*******Welcome Admin******");
        	 System.out.println("1. View Patients");
        	 System.out.println("2. View Doctors");
        	 System.out.println("3. View Appointments");
        	 System.out.println("4. Add doctors");
        	 System.out.println("5. Add patients");
        	 System.out.println("6. Update doctors");
        	 System.out.println("7. Update patients");
        	 System.out.println("8. Exit");
        	 System.out.println("Enter your choice:");
        	 int c=sc.nextInt();
        	 switch(c){
        	 case 1:{
                 
                 patient.viewPatients();
                 System.out.println();
                 break;}
        	 case 2:{
        		 doctors d=new doctors();;
     			d.viewDoctors();
                  System.out.println();
                  break;}
        	 case 3:{
        		 doctors.viewAppointment();
        		 System.out.println();
        		 break;
        	 }
        	 case 4:{
        		 doctors.addDoctor();
        		 System.out.println();
        		 break;
        		 
        	 }
        	 case 5:{
        		patient.addPatient();
        		System.out.println();
        		break;
        	 }
        	 case 6:{
        		 System.out.println("Enter Doctor Id:");
        		 int id=sc.nextInt();
        		 sc.nextLine();
        		 System.out.println("Enter the field to be updated:");
        		 String fieldToUpdate=sc.next();
        		 System.out.println("Enter the New value:");
        		 String newValue=sc.next();
        		 doctors.updateDoctor(id, fieldToUpdate, newValue);
        	System.out.println();
        	break;
        	 }
        	 case 7:{
        		 System.out.println("Enter patient Id:");
        		 int id=sc.nextInt();
        		 sc.nextLine();
        		 System.out.println("Enter the field to be updated:");
        		 String fieldToUpdate=sc.next();
        		 System.out.println("Enter the New value:");
        		 String newValue=sc.next();
        		 patient.updatePatient(id, fieldToUpdate, newValue);
        		 System.out.println();
             	break;
        	 }
        	 case 8:
        	 {
        		 f=true;
        		 HospMain h=new HospMain();
        		 h.det();
        		 
        	 }
        	 } 
        	}	
            }
             } 
    catch (SQLException e) {
        e.printStackTrace();
    }
    try{
    	//patient login
    	Connection connection = DbConnection.getConnection();	
    	String query="select * from patients where uname=? AND pword=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
        	found=true;
        	boolean f=false;
        	while(!f) {
        		//patient page
        	System.out.println("******Welcome******** ");
        	System.out.println("1. View Doctors");
        	System.out.println("2. Book Appointment");
        	System.out.println("3. Exit");
        	System.out.println("Enter your choice:");
       	 int t=sc.nextInt();
       	 switch(t) {
       	 case 1:
       		{doctors d=new doctors();;
			d.viewDoctors();
             System.out.println();
             break;}
       	 case 2:
       	 {
       		bookAppointment();
       		 System.out.println();
       		 break;
       	 }
       	 case 3:
       	 {
       		 f=true;
    		 HospMain h=new HospMain();
    		 h.det(); 
       	 }
             
             default:
                 System.out.println("Enter valid choice!!!");
                 break;
       	 }      
        	}
        }
    }
    catch (SQLException e){
        e.printStackTrace();
    }
    
    try{
    	//doctor login
		Connection connection = DbConnection.getConnection();
    	String query="select * from doctors where uname=? AND pword=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
 if(resultSet.next()){
        	found=true;
        	boolean f=false;
        	while(!f) {
        		//doctor page
        	System.out.println("******Welcome Doctor******** ");
        	System.out.println("1. View Patients");
        	System.out.println("2. Exit");
        	System.out.println("Enter your choice:");
       	 int t=sc.nextInt();
       	 switch (t) {
       	 case 1:
       	 {
       		 System.out.println("Enter Doctor Id: ");
       	    int doctorIdForPatientDet = sc.nextInt();
       	    PatientDet(doctorIdForPatientDet);
       	    System.out.println();
       	    break;
       	 }
       	 case 2:{
       		 f=true;
    		 HospMain h=new HospMain();
    		 h.det(); 
       	 }
       	 }}}}catch (SQLException e){ 	
         e.printStackTrace();
     }
	}
	catch(Exception e)
	{
	System.out.print("not found");
	}
	if(found==false)
	{
		System.out.println("User Not Found");
		input();
	}

}
//
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
//
private static boolean checkAvailability(int doctorid, String appointment_date) {
	//to check the availability of doctor
	    try{
	    	Connection connection = DbConnection.getConnection();
	   	 String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, doctorid);
			preparedStatement.setString(2, appointment_date);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        if(resultSet.next()){
	            int count = resultSet.getInt(1);
	            if(count==0){
	            	return true;  
	            }else{
	            	return false;
	            }
	        }
	    } catch (SQLException e){
	        e.printStackTrace();
	    }
	 return 
			 false;
	 }

	   public static void PatientDet(int doctorid) {
	try {
		// to display the patient name and appointment for a particular doctor
		System.out.println("hi!!!");
				Connection connection = DbConnection.getConnection();
				String query = "SELECT p.name, a.appointment_date " +
			               "FROM patients p " +
			               "JOIN appointments a ON p.id = a.patient_id " +
			               "WHERE a.doctor_id = ?";
		 PreparedStatement preparedStatement = connection.prepareStatement(query);
		 preparedStatement.setInt(1, doctorid);
		 ResultSet resultSet = preparedStatement.executeQuery(); 
		  while(resultSet.next()){
			  String pat_name = resultSet.getString("name");
	            String appointmentDate = resultSet.getString("appointment_date");
	            System.out.println("Patient Name: " + pat_name);
	            System.out.println("Appointment Date: " + appointmentDate);
	            System.out.println();
		  }	 
	}catch (SQLException e){
        e.printStackTrace();
    }
}}


