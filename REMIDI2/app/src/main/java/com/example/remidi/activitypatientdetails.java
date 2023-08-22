package com.example.remidi;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.remidi.valueupload.PatientDetails;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class activitypatientdetails extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        // Initialize UI components

        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user inputs
                String username = String.valueOf( ((TextInputEditText)findViewById(R.id.edit_username)).getText() );
                String phone = String.valueOf( ((TextInputEditText)findViewById(R.id.edit_phone)).getText() );
                String disease = String.valueOf( ((TextInputEditText)findViewById(R.id.edit_disease)).getText() );
                String email = String.valueOf( ((TextInputEditText)findViewById(R.id.edit_email)).getText() );

                if( TextUtils.isEmpty(username) || TextUtils.isEmpty(phone) ||TextUtils.isEmpty(email) || TextUtils.isEmpty(disease))
                {
                    Toast.makeText(getApplicationContext(),"Empty Credential!",Toast.LENGTH_SHORT).show();
                }
                else{
                    PatientDetails patient = new PatientDetails(username,disease,phone,email);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("patient", patient);
                    Intent intent = new Intent(activitypatientdetails.this,activitydoctordetails.class);
                    intent.putExtras(bundle);
                    Toast.makeText(activitypatientdetails.this, "Added!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                }

            }

        });
    }
}