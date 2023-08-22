package com.example.remidi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {


    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;

    private String BASE_URL = "http://192.168.0.171:8000";
    EditText eUsername,eEmail,eContact,ePassword,eConfirmpassword;
    TextView tvRegister,tvRemidi,tvExistinguser;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);


        eUsername=findViewById(R.id.editTextRegPersonName);
        eEmail=findViewById(R.id.editTextRegemail);
        eContact=findViewById(R.id.editTextRegcontactnumber);
        ePassword=findViewById(R.id.editTextRegpassword);
        eConfirmpassword=findViewById(R.id.editTextRegconfirmPassword);

        btn=findViewById(R.id.buttonRegister);

        tvRegister=findViewById(R.id.tvReg);
        tvRemidi=findViewById(R.id.tvRemidi);
        tvExistinguser=findViewById(R.id.textViewexistinguser);

        tvExistinguser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=eUsername.getText().toString();
                String email =eEmail.getText().toString();
                String contact =eContact.getText().toString();
                String password=ePassword.getText().toString();
                String confirmpass=eConfirmpassword.getText().toString();


                HashMap<String, String> map = new HashMap<>();

                map.put("name",username);
                map.put("email", email);
                map.put("contact", contact);
                map.put("password", password);

                Call<Void> call = retrofitInterface.executeSignup(map);

                if(username.length()==0 || email.length()==0 || contact.length()==0 || password.length()==0 || confirmpass.length()==0)
                {
                    Toast.makeText(getApplicationContext(),"Please fill All details",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(password.compareTo(confirmpass)==0){
                        if(isValid(password)){

                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.code() == 201) {
                                        Toast.makeText(RegisterActivity.this,"Record inserted succesfully",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                    }
//                                    else if (response.code() == 400) {
//                                        Toast.makeText(RegisterActivity.this,
//                                                "Already registered", Toast.LENGTH_LONG).show();
//                                    }

                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(RegisterActivity.this, t.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            });


                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Password must contain atleast 8 characters, having letter,digit and special symbol",Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Password and Confirm Password didn't match",Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });
    }

    //password having the combination of 1 special character,digit,one letter and min length of 8
    public static boolean isValid(String passwordhere){
        int f1=0,f2=0,f3=0;
        if(passwordhere.length()<8){
            return false; //if password having less than 8 character
        }
        else {
            for (int p=0;p<passwordhere.length();p++){
                if(Character.isLetter(passwordhere.charAt(p))){
                    f1=1;  // password contain letter
                }
            }
            for (int r=0;r<passwordhere.length();r++){
                if (Character.isDigit(passwordhere.charAt(r))){
                    f2=1;  // password contain digit
                }
            }
            for (int s=0;s<passwordhere.length();s++){
                char c =passwordhere.charAt(s); // password contain one character in between
                if(c>=33 && c<=46 || c==64){
                    f3=1;
                }
            }
            if (f1==1 && f2==1 && f3==1){
                return true;
            }
            return false;
        }
    }
}