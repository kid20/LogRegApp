package com.example.ralph.logregapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LognSuccess extends AppCompatActivity {
    TextView name,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logn_success);

        name = (TextView)findViewById(R.id.main_name);
        email = (TextView)findViewById(R.id.main_email);
        Bundle bundle = new Bundle();
        name.setText("Welcome " + bundle.getString("name"));
        email.setText("Email " + bundle.getString("email"));

    }
}
