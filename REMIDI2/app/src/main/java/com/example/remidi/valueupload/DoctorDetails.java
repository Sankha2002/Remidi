package com.example.remidi.valueupload;

import java.io.Serializable;

public class DoctorDetails implements Serializable {
    String doctorName, doctorSpecialist, checkupDate;

    public DoctorDetails(String doctorName, String doctorSpecialist, String checkupDate) {
        this.doctorName = doctorName;
        this.doctorSpecialist = doctorSpecialist;
        this.checkupDate = checkupDate;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorSpecialist() {
        return doctorSpecialist;
    }

    public void setDoctorSpecialist(String doctorSpecialist) {
        this.doctorSpecialist = doctorSpecialist;
    }

    public String getCheckupDate() {
        return checkupDate;
    }

    public void setCheckupDate(String checkupDate) {
        this.checkupDate = checkupDate;
    }
}
