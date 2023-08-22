package com.example.remidi;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;

    private String BASE_URL = "http://192.168.0.171:8000";

    EditText editUsername,ePassword;
    Button btn;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);


        editUsername= findViewById(R.id.username);
        ePassword= findViewById(R.id.password);
        btn=findViewById(R.id.login);
        tv=findViewById(R.id.textViewnewuser);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = new HashMap<>();
                String username=editUsername.getText().toString();
                String password = ePassword.getText().toString();
                if(username.length()>0 || password.length()>0) {
                    if (isValid(password)) {
                        map.put("email", username);
                        map.put("password", password);
                        Call<LoginResult> call = retrofitInterface.executeLogin(map);

                        call.enqueue(new Callback<LoginResult>() {
                            @Override
                            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                                if (response.code() == 200) {
                                    LoginResult result = response.body();

                                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(LoginActivity.this, "Welcome, " + result.getName(), Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(LoginActivity.this, activitypatientdetails.class);
                                    intent.putExtra("name", result.getName());
                                    startActivity(intent);
                                } else if (response.code() == 404) {
                                    Toast.makeText(LoginActivity.this, "Password incorrect", Toast.LENGTH_LONG).show();
                                }
                                else if (response.code() == 504) {
                                    Toast.makeText(LoginActivity.this, "Wrong email", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<LoginResult> call, Throwable t) {
                                Toast.makeText(LoginActivity.this, t.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });


//                        Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(LoginActivity.this,activitypatientdetails.class));
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Password must contain atleast 8 characters, having letter,digit and special symbol",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please Fill All Details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }
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