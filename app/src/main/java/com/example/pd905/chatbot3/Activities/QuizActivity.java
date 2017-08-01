package com.example.pd905.chatbot3.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.pd905.chatbot3.Fragments.QuizFragment;
import com.example.pd905.chatbot3.R;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState == null) {
            String username = getIntent().getStringExtra("USER_NAME");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.quiz_container, QuizFragment.newInstance(username)).commit();
        }
    }
}
