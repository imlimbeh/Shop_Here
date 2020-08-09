package com.example.shophere;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "TAG";
    Button bCA, bSI;
    TextView t;
    EditText un, em, ps;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mStore;
    ProgressBar progressBar;
    String userID;
    CheckBox showPassword;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        un = (EditText)findViewById(R.id.username);
        em = (EditText)findViewById(R.id.email);
        ps = (EditText)findViewById(R.id.password);
        bCA = (Button)findViewById(R.id.create_account);
        bSI = (Button)findViewById(R.id.sign_in_now);
        progressBar = findViewById(R.id.progressBar);
        t = (TextView)findViewById(R.id.or);
        showPassword = (CheckBox)findViewById(R.id.show_password);

        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    ps.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    ps.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        bCA.setOnClickListener(this);
        bSI.setOnClickListener(this);
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.create_account:
                final String username = un.getText().toString();
                final String email = em.getText().toString();
                String password = ps.getText().toString();
                if(username.isEmpty()){
                    un.setError("Please enter username!");
                    un.requestFocus();
                }else if (email.isEmpty()){
                    em.setError("Please enter email!");
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
                } else if (username.isEmpty() && email.isEmpty() && password.isEmpty()){
                    Toast.makeText(SignUp.this,"Fields Are Empty!",Toast.LENGTH_SHORT);
                }else if (!(username.isEmpty() && email.isEmpty() && password.isEmpty())){
                    progressBar.setVisibility(View.VISIBLE);
                    bCA.setVisibility(View.GONE);
                    t.setVisibility(View.GONE);
                    bSI.setVisibility(View.GONE);
                    mFirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(SignUp.this,"SignUp Unsuccessful!!! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                bCA.setVisibility(View.VISIBLE);
                                t.setVisibility(View.VISIBLE);
                                bSI.setVisibility(View.VISIBLE);
                            }else{
                                FirebaseUser fUser = mFirebaseAuth.getCurrentUser();
                                fUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(SignUp.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                    }
                                });
                                Toast.makeText(SignUp.this, "User Created.", Toast.LENGTH_SHORT).show();
                                userID = mFirebaseAuth.getCurrentUser().getUid();
                                database = FirebaseDatabase.getInstance();
                                myRef = database.getReference("users").child(userID);
                                myRef.child("userID").setValue(userID);
                                myRef.child("username").setValue(username);
                                myRef.child("email").setValue(email);
                                SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("stayLogged","false");
                                editor.apply();
                                finish();
                            }
                        }
                    });
                }else{
                    Toast.makeText(SignUp.this,"Error!!!",Toast.LENGTH_SHORT);
                }
                break;
            case R.id.sign_in_now:
                finish();
                break;
        }
    }
}