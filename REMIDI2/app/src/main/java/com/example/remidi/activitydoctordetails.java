package com.example.remidi;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.remidi.valueupload.DoctorDetails;
import com.example.remidi.valueupload.PatientDetails;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class activitydoctordetails extends AppCompatActivity {

    private ArrayList<DoctorDetails> doctors = new ArrayList<>();
    private Calendar calendar;
    private SimpleDateFormat dateFormat;

    //@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        findViewById(R.id.edit_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();

            }
        });

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doctorName = String.valueOf( ((TextInputEditText)findViewById(R.id.edit_doctorname)).getText() );
                String doctorSpecialist = String.valueOf( ((TextInputEditText)findViewById(R.id.edit_doctorspecialist)).getText() );
                String checkupDate = String.valueOf( ((TextInputEditText)findViewById(R.id.edit_date)).getText() );

                if (TextUtils.isEmpty(doctorName) || TextUtils.isEmpty(doctorSpecialist) || TextUtils.isEmpty(checkupDate) ) {
                    Toast.makeText(activitydoctordetails.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                }
                else{
                    DoctorDetails doctor = new DoctorDetails(doctorName, doctorSpecialist, checkupDate);
                    doctors.add(doctor);

                    Toast.makeText(activitydoctordetails.this, "Added!", Toast.LENGTH_SHORT).show();
                    ((TextInputEditText) findViewById(R.id.edit_doctorname)).getText().clear();
                    ((TextInputEditText) findViewById(R.id.edit_doctorspecialist)).getText().clear();
                    ((TextInputEditText) findViewById(R.id.edit_date)).getText().clear();
                }
            }
        });

        findViewById(R.id.btn_submit2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doctorName = String.valueOf( ((TextInputEditText)findViewById(R.id.edit_doctorname)).getText() );
                String doctorSpecialist = String.valueOf( ((TextInputEditText)findViewById(R.id.edit_doctorspecialist)).getText() );
                String checkupDate = String.valueOf( ((TextInputEditText)findViewById(R.id.edit_date)).getText() );
                if(doctors.size() == 0){
                    Toast.makeText(activitydoctordetails.this, "Please add atleast one doctor details", Toast.LENGTH_SHORT).show();
                }
                else if( !TextUtils.isEmpty(doctorName) || !TextUtils.isEmpty(doctorSpecialist) || !TextUtils.isEmpty(checkupDate)) {
                    Toast.makeText(activitydoctordetails.this, "Add The Doctor Details Before Proceeding", Toast.LENGTH_SHORT).show();
                }
                else {
                    Bundle extras = getIntent().getExtras();
                    PatientDetails patient = (PatientDetails) extras.getSerializable("patient");

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("doctors", doctors);
                    bundle.putSerializable("patient", patient);
                    Intent intent = new Intent(activitydoctordetails.this, MedicineDetailsActivity.class);
                    intent.putExtras(bundle);
                    Toast.makeText(getApplicationContext(), "Added!", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            }
        });


    }


    private void showDatePickerDialog() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateCheckupDateEditText();

                    }
                },
                year, month, day);

        datePickerDialog.show();
    }

    private void updateCheckupDateEditText() {
        String formattedDate = dateFormat.format(calendar.getTime());
        ((TextView) findViewById(R.id.edit_date)).setText(formattedDate);
    }
}
