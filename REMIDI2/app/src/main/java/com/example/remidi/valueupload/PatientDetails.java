package com.example.remidi.valueupload;

import java.io.Serializable;

public class PatientDetails implements Serializable {
    String Patients_name, Infirmity_Occured, Patient_Phone_Number,  Patients_email;

    public PatientDetails(String patients_name, String infirmity_Occured, String patient_Phone_Number,  String patients_email) {
        Patients_name = patients_name;
        Patient_Phone_Number = patient_Phone_Number;
        Infirmity_Occured = infirmity_Occured;
        Patients_email = patients_email;
    }

    public String getPatients_name() {
        return Patients_name;
    }

    public void setPatients_name(String patients_name) {
        Patients_name = patients_name;
    }

    public String getPatient_Phone_Number() {
        return Patient_Phone_Number;
    }

    public void setPatient_Phone_Number(String patient_Phone_Number) {
        Patient_Phone_Number = patient_Phone_Number;
    }

    public String getInfirmity_Occured() {
        return Infirmity_Occured;
    }

    public void setInfirmity_Occured(String infirmity_Occured) {
        Infirmity_Occured = infirmity_Occured;
    }

    public String getPatients_email() {
        return Patients_email;
    }

    public void setPatients_email(String patients_email) {
        Patients_email = patients_email;
    }
}
