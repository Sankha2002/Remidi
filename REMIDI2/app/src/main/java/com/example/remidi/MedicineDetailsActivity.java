package com.example.remidi;


import android.annotation.SuppressLint;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.remidi.valueupload.DoctorDetails;
import com.example.remidi.valueupload.MedicineDetails;
import com.example.remidi.valueupload.PatientDetails;
import com.google.android.material.textfield.TextInputEditText;


import java.util.ArrayList;
import java.util.Collections;


public class MedicineDetailsActivity extends AppCompatActivity {

    private ArrayList<DoctorDetails> doctors = new ArrayList<>();
    private  ArrayList<MedicineDetails> med = new ArrayList<>();
    PatientDetails patient;
    private String intakeType, doctorName;

    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine_details);

        //Data Extract from bundle
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            patient = (PatientDetails) bundle.getSerializable("patient");
            doctors = (ArrayList<DoctorDetails>) bundle.getSerializable("doctors");
        }
        else
            Toast.makeText(this, "Bundle is Empty!", Toast.LENGTH_SHORT).show();



        //Dropdown for Medicine Intake Type

        String[] IntakeType =  {"BDAC","BDPC","ODPC","TDPC"};
        AutoCompleteTextView autoIntakeType =findViewById(R.id.auto_complete_txt2);
        ArrayAdapter<String> adapterIntakeType;
        adapterIntakeType = new ArrayAdapter<String>(this,R.layout.list_item,IntakeType);
        autoIntakeType.setAdapter(adapterIntakeType);
        autoIntakeType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intakeType = parent.getItemAtPosition(position).toString();
                switch (intakeType) {
                        case "BDAC":
                        case "BDPC":
                            ((TextInputEditText) findViewById(R.id.edit_medicine_intake_per_day)).setText("2");
                            break;
                        case "ODPC":
                            ((TextInputEditText) findViewById(R.id.edit_medicine_intake_per_day)).setText("1");
                            break;
                        case "TDPC":
                            ((TextInputEditText) findViewById(R.id.edit_medicine_intake_per_day)).setText("3");
                            break;
                        default:
                            ((TextInputEditText) findViewById(R.id.edit_medicine_intake_per_day)).setText("NONE");
                            break;
                    }
            }
        });


        //doctorNames[] initialization for the doctor name dropdown
        String[] doctorNames = new String[doctors.size()];
        for(int i=0; i< doctors.size(); i++) {
            doctorNames[i] = doctors.get(i).getDoctorName();
        }
        //Dropdown for Doctor Name
        ArrayAdapter<String> adapterItems;
        AutoCompleteTextView autoDoctorName = findViewById(R.id.auto_complete_txt1);

        adapterItems = new ArrayAdapter<String>(MedicineDetailsActivity.this,R.layout.list_item,doctorNames);
        autoDoctorName.setAdapter(adapterItems);
        autoDoctorName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                doctorName = parent.getItemAtPosition(position).toString();
            }
        });


        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String medName = String.valueOf( ((TextInputEditText)findViewById(R.id.edit_medicine_name)).getText() );
                String medDuration = String.valueOf( ((TextInputEditText)findViewById(R.id.edit_medicine_duration)).getText() );
                String medQuantity = String.valueOf( ((TextInputEditText)findViewById(R.id.edit_medicine_quantity)).getText() );
                String intakePerDay = String.valueOf( ((TextInputEditText)findViewById(R.id.edit_medicine_intake_per_day)).getText() );
                String doctorname = String.valueOf( ((AutoCompleteTextView)findViewById(R.id.auto_complete_txt1)).getText() );
                String intakeType = String.valueOf( ((AutoCompleteTextView)findViewById(R.id.auto_complete_txt2)).getText() );

                if (TextUtils.isEmpty(medName) || TextUtils.isEmpty(intakePerDay)  || TextUtils.isEmpty(medDuration) || TextUtils.isEmpty(medQuantity) || TextUtils.isEmpty(doctorname) ||TextUtils.isEmpty(intakeType)  ) {
                    Toast.makeText(MedicineDetailsActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                }
                else {
                    MedicineDetails medicine = new MedicineDetails(medName, doctorName, medDuration, medQuantity, intakeType, intakePerDay);
                    med.add(medicine);

                    Toast.makeText(MedicineDetailsActivity.this, "Added!", Toast.LENGTH_SHORT).show();
                    ((TextInputEditText) findViewById(R.id.edit_medicine_name)).getText().clear();
                    ((TextInputEditText) findViewById(R.id.edit_medicine_duration)).getText().clear();
                    ((TextInputEditText) findViewById(R.id.edit_medicine_quantity)).getText().clear();
                    ((TextInputEditText) findViewById(R.id.edit_medicine_intake_per_day)).getText().clear();
                    autoDoctorName.getText().clear();
                    autoIntakeType.getText().clear();

                }
            }
        });


        findViewById(R.id.btn_proceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String medName = String.valueOf( ((TextInputEditText)findViewById(R.id.edit_medicine_name)).getText() );
                String medDuration = String.valueOf( ((TextInputEditText)findViewById(R.id.edit_medicine_duration)).getText() );
                String medQuantity = String.valueOf( ((TextInputEditText)findViewById(R.id.edit_medicine_quantity)).getText() );
                String intakePerDay = String.valueOf( ((TextInputEditText)findViewById(R.id.edit_medicine_intake_per_day)).getText() );
                String doctorname = String.valueOf( ((AutoCompleteTextView)findViewById(R.id.auto_complete_txt1)).getText() );
                String intakeType = String.valueOf( ((AutoCompleteTextView)findViewById(R.id.auto_complete_txt2)).getText() );
                if(med.size() == 0){
                    Toast.makeText(MedicineDetailsActivity.this, "Please Add Atleast one Medicine Details", Toast.LENGTH_SHORT).show();
                }
                else if( !TextUtils.isEmpty(medName) || !TextUtils.isEmpty(medDuration) || !TextUtils.isEmpty(medQuantity) || !TextUtils.isEmpty(intakePerDay) || !TextUtils.isEmpty(doctorname) || !TextUtils.isEmpty(intakeType)) {
                    Toast.makeText(MedicineDetailsActivity.this, "Add The Medicine Details Before Proceeding", Toast.LENGTH_SHORT).show();
                }
                else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("doctors", doctors);
                    bundle.putSerializable("medicines", med);
                    bundle.putSerializable("patient", patient);
                    Intent intent = new Intent(MedicineDetailsActivity.this, activity_alarmset.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });


    }
}
