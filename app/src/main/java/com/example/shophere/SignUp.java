package com.example.shophere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.shophere.ui.login.LoginActivity;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    Button bCA, bSI;
    EditText un, em, ps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        un = (EditText)findViewById(R.id.username);
        em = (EditText)findViewById(R.id.email);
        ps = (EditText)findViewById(R.id.password);
        bCA = (Button)findViewById(R.id.create_account);
        bSI = (Button)findViewById(R.id.sign_in_now);

        bCA.setOnClickListener(this);
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.create_account:
                break;
            case R.id.sign_in_now:
                Intent intent = new Intent(SignUp.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}