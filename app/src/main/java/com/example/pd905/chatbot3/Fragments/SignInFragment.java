package com.example.pd905.chatbot3.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.example.pd905.chatbot3.Activities.ChatbotActivity;
import com.example.pd905.chatbot3.Activities.QuizActivity;
import com.example.pd905.chatbot3.R;

public class SignInFragment extends Fragment {
    private static final String ARG_EDIT = "EDIT";

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //private UserLoginTask mAuthTask = null;

    //UI references
    private boolean mEdit;
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;

    //new instance and set arguments
    public static SignInFragment newInstance(boolean edit) {
        Bundle args = new Bundle();
        args.putBoolean(ARG_EDIT, edit);
        SignInFragment fragment = new SignInFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEdit = getArguments().getBoolean(ARG_EDIT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View contentView =  inflater.inflate(R.layout.fragment_sign_in, container, false);

        //set up the layout listener (this one doesn't do any thing)
        contentView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
            }
        });


        return contentView;
    }

    //Initialize all view components
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //set up the login form
        mEmailView = (EditText) view.findViewById(R.id.user_name);

        mPasswordView = (EditText) view.findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == R.id.login || actionId == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        // sign in button, and click listener
        Button mSignInButton = (Button) view.findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        mProgressView = view.findViewById(R.id.progressBar);
    }

    // implements attemptLogin, attempts to sign in or register the account
    private void attemptLogin() {

        // Reset errors
        mEmailView.setError(null);
        mPasswordView.setError(null);

        //Store values at the time of the login attempt
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError("This password is too short");
            focusView = mPasswordView;
            cancel = true;
        }

        //Check for a valid email address
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError("This field is required");
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError("This email address is invalid");
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            //showProgress(true);
            //mAnthTask = new UserLoginTask(email, password);
            //mAuthTask.execute((Void) null);
            goToQuiz(email, password);
        }
    }

    // Check if email valid
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    //Check if password valid
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private void goToQuiz(String email, String password) {
        Intent intent = new Intent(getActivity(), ChatbotActivity.class);
        intent.putExtra("USER_NAME", email);
        startActivity(intent);
    }






}
