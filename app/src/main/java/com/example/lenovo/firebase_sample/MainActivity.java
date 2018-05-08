package com.example.lenovo.firebase_sample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    EditText et_email,et_pwd,et_uname;
    Button btn_signup;
    TextView tv_login;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        progressDialog=new ProgressDialog(getApplicationContext());

        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null){

            finish();
            Intent intent = new Intent(this,DisplayActivity.class);
            startActivity(intent);

        }
        et_uname=(EditText)findViewById(R.id.et_uname_main);
        et_email=(EditText)findViewById(R.id.et_email_main);
        et_pwd=(EditText)findViewById(R.id.et_pwd_main);
        btn_signup=(Button)findViewById(R.id.btn_signUp_main);
        tv_login=(TextView)findViewById(R.id.tv_login_main);



        btn_signup.setOnClickListener(this);

        tv_login.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    public void createAccount(){


       String email = et_email.getText().toString().trim();
       String pwd = et_pwd.getText().toString().trim();


//       progressDialog=new ProgressDialog(getApplicationContext());
//       progressDialog.setMessage("Loading.....");

        firebaseAuth.createUserWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            finish();
                            UserProfile();
                            Intent intent = new Intent(MainActivity.this,DisplayActivity.class);
                            startActivity(intent);
                            et_email.setText("");
                            et_pwd.setText("");
                            et_uname.setText("");
                                                    }
                        else {
                            Log.d("MainActivity", "task:" + task);
                            Toast.makeText(MainActivity.this, "Having Problem", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    private void UserProfile(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setDisplayName(et_uname.getText().toString().trim())
                    .build();
            user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    }
            );

        }
    }

    @Override
    public void onClick(View v) {
        if (v == btn_signup)
        {
            try {
                createAccount();
            } catch (RuntimeException ex) {
                ex.printStackTrace();
                Toast.makeText(this, "ex:" + ex, Toast.LENGTH_SHORT).show();
                // do something with the runtime exception
            }

        }
        if (v == tv_login){

            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);

        }
    }
}
