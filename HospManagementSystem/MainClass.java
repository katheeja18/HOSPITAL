package HospManagementSystem;

import HospManagementSystem.controller.HospitalController;
import HospManagementSystem.model.DoctorModel;
import HospManagementSystem.model.PatientModel;
import HospManagementSystem.view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        DoctorModel doctorModel = new DoctorModel();
        PatientModel patientModel = new PatientModel();
        ConsoleView view = new ConsoleView(new HospitalController(doctorModel, patientModel, view));
        view.showMainMenu();
    }
}
