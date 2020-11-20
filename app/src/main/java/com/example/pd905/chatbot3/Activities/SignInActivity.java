package com.example.pd905.chatbot3.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.pd905.chatbot3.fragments.SignInFragment;
import com.example.pd905.chatbot3.R;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        //final boolean edit = isInEditMode();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.sign_in_container, SignInFragment.newInstance(true)).commit();
        }
    }
}
