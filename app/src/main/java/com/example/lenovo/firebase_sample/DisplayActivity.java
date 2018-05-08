package com.example.lenovo.firebase_sample;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class DisplayActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    TextView tv_display,tv_pic;
    Button btn_logout;
    ImageView iv_profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        firebaseAuth=FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }



        iv_profile=(ImageView)findViewById(R.id.iv_pic_display);
        tv_pic=(TextView)findViewById(R.id.tv_t_display);
        tv_display=(TextView)findViewById(R.id.tv_wel_display);
        btn_logout=(Button) findViewById(R.id.btn_logout_display);

        FirebaseUser user = firebaseAuth.getCurrentUser();


        tv_display.setText(user.getDisplayName());

//        tv_pic.setText(Objects.requireNonNull(user.getDisplayName()).substring(0,1));




        btn_logout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btn_logout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }
}
