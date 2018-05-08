package com.example.lenovo.firebase_sample;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_email,et_pwd;
    Button btn_login;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()!=null){

            finish();
            Intent intent = new Intent(LoginActivity.this,DisplayActivity.class);
            startActivity(intent);

        }


        et_email = (EditText)findViewById(R.id.et_email_login);
        et_pwd=(EditText)findViewById(R.id.et_pwd_login);
        btn_login=(Button)findViewById(R.id.btn_login_login);

        btn_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btn_login){
            login();
        }
    }

    private void login() {
        String email=et_email.getText().toString().trim();
        String pwd = et_pwd.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "login success", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(LoginActivity.this,DisplayActivity.class);
                            startActivity(intent);

                            et_email.setText("");
                            et_pwd.setText("");
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "email and pwd not matching", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
