package com.example.shophere;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_REPLY ="com.example.android.shophere.REPLY";

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
        bSI.setOnClickListener(this);
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.create_account:
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(un.getText())||TextUtils.isEmpty(em.getText())||TextUtils.isEmpty(ps.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String username = un.getText().toString();
                    String email = em.getText().toString();
                    String password = ps.getText().toString();
                    //User registerUser = new User(username,email,password);
                    replyIntent.putExtra(EXTRA_REPLY, username);
                    replyIntent.putExtra(EXTRA_REPLY, email);
                    replyIntent.putExtra(EXTRA_REPLY, password);
                    // Set the result status to indicate success.
                    setResult(RESULT_OK, replyIntent);
                }
                finish();

                //User registerUser = new User(username,email,password);
                break;
            case R.id.sign_in_now:
                /*Intent intent = new Intent(SignUp.this, LoginActivity.class);
                startActivity(intent);*/
                finish();
                break;
        }
    }
}