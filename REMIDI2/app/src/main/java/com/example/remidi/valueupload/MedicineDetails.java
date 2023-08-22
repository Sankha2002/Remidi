package com.example.remidi.valueupload;

import java.io.Serializable;

public class MedicineDetails  implements Serializable {
    String Medicine_Name, Doctors_Name, Medicine_Duration, Medicine_Quantity, Medicine_Intake_Type, Medicine_Intake_per_Day;

    public MedicineDetails(String medicine_Name, String doctors_Name, String medicine_Duration, String medicine_Quantity, String medicine_Intake_Type, String medicine_Intake_per_Day) {
        Medicine_Name = medicine_Name;
        Doctors_Name = doctors_Name;
        Medicine_Duration = medicine_Duration;
        Medicine_Quantity = medicine_Quantity;
        Medicine_Intake_Type = medicine_Intake_Type;
        Medicine_Intake_per_Day = medicine_Intake_per_Day;
    }

    public String getMedicine_Name() {
        return Medicine_Name;
    }

    public String getDoctors_Name() {
        return Doctors_Name;
    }

    public String getMedicine_Duration() {
        return Medicine_Duration;
    }

    public String getMedicine_Quantity() {
        return Medicine_Quantity;
    }

    public String getMedicine_Intake_Type() {
        return Medicine_Intake_Type;
    }

    public String getMedicine_Intake_per_Day() {
        return Medicine_Intake_per_Day;
    }
}
