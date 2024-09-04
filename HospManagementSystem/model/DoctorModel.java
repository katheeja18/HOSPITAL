package HospManagementSystem.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import HospManagementSystem.DbConnection;

public class DoctorModel {

    public void viewDoctors() {
        String query = "SELECT * FROM doctors";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("Doctors: ");
            System.out.println("+------------+--------------------+------------------------+-----------------+--------------------------------------------+---------+");
            System.out.println("| Doctor Id  | Name               | Specialization         | Phone Number    | Patient Address                            | Age     |");
            System.out.println("+------------+--------------------+------------------------+-----------------+--------------------------------------------+---------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                String phNumber = resultSet.getString("ph_number");
                String docAdd = resultSet.getString("doc_add");
                int age = resultSet.getInt("age");
                System.out.printf("| %-10s | %-18s | %-22s | %-15s | %-42s | %-7s |\n", id, name, specialization, phNumber, docAdd, age);
                System.out.println("+------------+--------------------+------------------------+-----------------+--------------------------------------------+---------+");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDoctor(String name, String specialization, String uname, String pword, String phNumber, String docAdd, String dobStr) {
        String query = "INSERT INTO doctors(name, specialization, uname, pword, ph_number, doc_add, D_O_B, age) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dob = LocalDate.parse(dobStr, formatter);
            LocalDate currentDate = LocalDate.now();
            int age = java.time.Period.between(dob, currentDate).getYears();

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, specialization);
            preparedStatement.setString(3, uname);
            preparedStatement.setString(4, pword);
            preparedStatement.setString(5, phNumber);
            preparedStatement.setString(6, docAdd);
            preparedStatement.setString(7, dobStr);
            preparedStatement.setInt(8, age);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Doctor Added Successfully!!");
                // You might want to get the generated ID here if needed
            } else {
                System.out.println("Failed to add Doctor!!");
            }

        } catch (DateTimeParseException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDoctor(int id, String fieldToUpdate, String newValue) {
        String query = "";
        switch (fieldToUpdate.toLowerCase()) {
            case "ph_number":
                query = "UPDATE doctors SET ph_number=? WHERE id=?";
                break;
            case "doc_add":
                query = "UPDATE doctors SET doc_add=? WHERE id=?";
                break;
            default:
                System.out.println("Invalid field to update.");
                return;
        }

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, newValue);
            preparedStatement.setInt(2, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Doctor details updated successfully!");
            } else {
                System.out.println("No Doctor found with the given ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getDoctorById(int id) {
        String query = "SELECT * FROM doctors WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

