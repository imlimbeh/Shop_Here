package com.example.shophere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.shophere.ui.login.LoginActivity;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
    }
    public void btn_create_account(View view){

    }
    public  void btn_sign_in_now(View view){
        Intent intent = new Intent(SignUp.this, LoginActivity.class);
        startActivity(intent);
    }
}