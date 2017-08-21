package com.example.pd905.chatbot3.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewAnimator;
import android.widget.AdapterViewFlipper;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pd905.chatbot3.activities.ChatbotActivity;
import com.example.pd905.chatbot3.adapter.QuizAdapter;
import com.example.pd905.chatbot3.R;


public class QuizFragment extends Fragment{

    // public property, to indicate the process of evaluation.
    public boolean isDone = false;

    //private properties
    private TextView mProgressText;
    private ProgressBar mQuizProgress;
    private int mQuizSize;
    private QuizAdapter mQuizAdapter;
    private AdapterViewAnimator mQuizView;
    private  int mQuizId; // determine which quiz is currently on screen
    private String username;




    public QuizFragment() {
        // Required empty public constructor
    }

    public static QuizFragment newInstance(String username) {

        Bundle args = new Bundle();
        args.putString("USER_NAME", username);

        QuizFragment fragment = new QuizFragment();
        fragment.setArguments(args); //getArguments later
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getArguments().getString("USER_NAME");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set UI components
        mQuizView = (AdapterViewFlipper) view.findViewById(R.id.quiz_content);
        mQuizAdapter = new QuizAdapter(getContext());
        mQuizView.setAdapter(mQuizAdapter);

        // Here to start replace views
        mQuizSize = mQuizAdapter.getCount();
        mQuizId = 0;
        mQuizView.setSelection(mQuizId);

        FloatingActionButton nextButton = (FloatingActionButton) view
                .findViewById(R.id.next_button);
        nextButton.setClickable(true);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNext();
            }
        });

        //init progress bar here
        initProgressToolbar(view);



    }

    private void goToNext() {
        if (mQuizId < mQuizSize - 1) {
            mQuizId++;
            mQuizView.showNext();

            //set progress
            mQuizProgress.setProgress(mQuizId + 1);
            mProgressText.setText("Progress: " + Integer.toString(mQuizId + 1) + "/" + Integer.toString(mQuizSize));
        } else {
            goToChatbot();
        }

    }

    private void goToChatbot() {
        Intent intent = new Intent(getContext(), ChatbotActivity.class);
        startActivity(intent);
    }



    //Set progress bar UI components
    private void initProgressToolbar(View view) {
        mProgressText = (TextView) view.findViewById(R.id.progress_text);
        mProgressText.setText("Progress " + Integer.toString(mQuizId + 1) + "/" + Integer.toString(mQuizSize));

        mQuizProgress = (ProgressBar) view.findViewById(R.id.quiz_progress);
        mQuizProgress.setMax(mQuizSize);
        mQuizProgress.setProgress(mQuizId + 1);
    }



}
