package HospManagementSystem;
import java.util.*;
public class HospMain {
	public static void main(String args[])
	{
		det();
	}
public static void det()
{
	Scanner sc= new Scanner(System.in);
	boolean fon=false;
	while(!fon) {
	System.out.println("***HOSPITAL MANAGEMENT SYSTEM***");
	System.out.println("1.Login");
	System.out.println("2.Register");
	System.out.println("3.Exit");
	System.out.println("Enter your choice: ");
	int choice=sc.nextInt();
	switch(choice) {
	case 1:
	{
		login a=new login();
		login.input();
		break;
	}
	case 2:
	{
		patient j= new patient();
		
		patient.addPatient();
		break;
	}
	case 3:
	{
		System.out.println("****Thankyou!!!******");
		fon=true;
		break;
	}
		default:
			System.out.println("Enter valid choice");
			break;
	}
	}}
}
