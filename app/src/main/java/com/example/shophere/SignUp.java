package com.example.shophere;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_REPLY ="com.example.android.shophere.REPLY";

    Button bCA, bSI;
    EditText un, em, ps;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        mFirebaseAuth = FirebaseAuth.getInstance();
        un = (EditText)findViewById(R.id.username);
        em = (EditText)findViewById(R.id.email);
        ps = (EditText)findViewById(R.id.password);
        bCA = (Button)findViewById(R.id.create_account);
        bSI = (Button)findViewById(R.id.sign_in_now);

        bCA.setOnClickListener(this);
        bSI.setOnClickListener(this);
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.create_account:
                String username = un.getText().toString();
                String email = em.getText().toString();
                String password = ps.getText().toString();
                if(username.isEmpty()){
                    un.setError("Please enter username");
                    un.requestFocus();
                }else if (email.isEmpty()){
                    em.setError("Please enter email");
                    em.requestFocus();
                }else if (password.isEmpty()){
                    ps.setError("Please enter password");
                    ps.requestFocus();
                }else if (username.isEmpty() && email.isEmpty() && password.isEmpty()){
                    Toast.makeText(SignUp.this,"Fields Are Empty!",Toast.LENGTH_SHORT);
                }else if (!(username.isEmpty() && email.isEmpty() && password.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email,password)
                }

                finish();
                break;
            case R.id.sign_in_now:
                /*Intent intent = new Intent(SignUp.this, LoginActivity.class);
                startActivity(intent);*/
                finish();
                break;
        }
    }
}