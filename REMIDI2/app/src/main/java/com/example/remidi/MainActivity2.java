package com.example.remidi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.remidi.valueupload.DoctorDetails;
import com.example.remidi.valueupload.MedicineDetails;
import com.example.remidi.valueupload.PatientDetails;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    private ArrayList<DoctorDetails> doctors = new ArrayList<>();
    private  ArrayList<MedicineDetails> med = new ArrayList<>();
    PatientDetails patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            patient = (PatientDetails) bundle.getSerializable("patient");
            doctors = (ArrayList<DoctorDetails>) bundle.getSerializable("doctors");
            med = (ArrayList<MedicineDetails>) bundle.getSerializable("medicines");

            //Toast.makeText(getApplicationContext(), doctors.get(0).getDoctorName() +" " +patient.getPatients_email()+" "+med.get(0).getDoctors_Name(), Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "Bundle is Empty!", Toast.LENGTH_SHORT).show();
        finish();

    }
}