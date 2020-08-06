package com.example.shophere;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    CheckBox stay;
    Boolean tf;
    EditText em, ps;
    Button bL, bSU;
    FirebaseAuth mFirebaseAuth;
    ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        em = (EditText)findViewById(R.id.email);
        ps = (EditText)findViewById(R.id.password);
        bL = (Button)findViewById(R.id.login);
        bSU = (Button)findViewById(R.id.sign_up);
        stay = (CheckBox)findViewById(R.id.stay_signin);
        progressBar = findViewById(R.id.progressBar);

        // stay logged in
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("stayLogged", "");
        if (checkbox.equals("false")){
            // Logout
            FirebaseAuth.getInstance().signOut();
            // Set stay logged checkbox
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("stayLogged","true");
            editor.apply();
        }

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null){
                    Toast.makeText(MainActivity.this, "You are logged in", Toast.LENGTH_LONG);
                    Intent i = new Intent(MainActivity.this, MainStore.class);
                    startActivity(i);
                }else{
                    Toast.makeText(MainActivity.this, "Please Login", Toast.LENGTH_LONG);
                }
            }
        };
        bL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = em.getText().toString();
                String password = ps.getText().toString();
                if(email.isEmpty()){
                    em.setError("Please enter Email!");
                    em.requestFocus();
                }else if (password.isEmpty()){
                    ps.setError("Please enter password!");
                    ps.requestFocus();
                }else if (password.length() < 6){
                    ps.setError("Please enter least 6 digit password!");
                    ps.requestFocus();
                }else if (password.length() > 16){
                    ps.setError("Password cannot more than 16 character!!!");
                    ps.requestFocus();
                } else if (email.isEmpty() && password.isEmpty()){
                    Toast.makeText(MainActivity.this,"Fields Are Empty!",Toast.LENGTH_SHORT);
                }else if (!(email.isEmpty() && password.isEmpty())){
                    progressBar.setVisibility(View.VISIBLE);
                    bL.setVisibility(View.GONE);
                    mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent loginSuccessful = new Intent(MainActivity.this, MainStore.class);
                                startActivity(loginSuccessful);
                            }else{
                                Toast.makeText(MainActivity.this, "Login Failed!!! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                bL.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }else{
                    Toast.makeText(MainActivity.this,"Error!!!",Toast.LENGTH_SHORT);
                }
            }
        });

        bSU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpPage = new Intent(MainActivity.this, SignUp.class);
                startActivity(signUpPage);
            }
        });

        stay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("stayLogged","true");
                    editor.apply();
                }else if (!compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("stayLogged","false");
                    editor.apply();
                }
            }
        });
    }



    @Override
    protected void onStart(){
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onBackPressed() {
        Intent exit = new Intent(Intent.ACTION_MAIN);
        exit.addCategory(Intent.CATEGORY_HOME);
        exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(exit);
        finish();
        System.exit(0);
    }
}