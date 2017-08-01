package com.example.pd905.chatbot3.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewAnimator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pd905.chatbot3.Activities.QuizActivity;
import com.example.pd905.chatbot3.Adapter.QuizAdapter;
import com.example.pd905.chatbot3.R;

import org.w3c.dom.Text;

import java.util.List;


public class QuizFragment extends Fragment {

    private TextView mProgressText;
    private ProgressBar mQuizProgress;
    private int mQuizSize;
    private QuizAdapter mQuizAdapter;
    private AdapterViewAnimator mQuizView;
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
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set UI components


    }

    //Set progress bar UI components
    private void initProgressToolbar(View view) {
        Quiz quiz1 = new Quiz("1", getString(R.string.quiz1));
        Quiz quiz2 = new Quiz("2", getString(R.string.quiz2));
        Quiz quiz3 = new Quiz("3", getString(R.string.quiz3));
        final Quiz[] quizzes = new Quiz[]{quiz1, quiz2, quiz3 };

        mQuizSize = quizzes.length;
        mProgressText = (TextView) view.findViewById(R.id.progress_text);
        mQuizProgress = (ProgressBar) view.findViewById(R.id.quiz_progress);
        //mQuizProgress.setMax(mQuizSize);
    }

    private class Quiz {
        private String quizId;
        private String quizContent;

        public Quiz(String quizId, String quizContent) {
            this.quizId = quizId;
            this.quizContent = quizContent;
        }

        public String getQuizId() {
            return quizId;
        }

        public void setQuizId(String quizId) {
            this.quizId = quizId;
        }

        public String getQuizContent() {
            return quizContent;
        }

        public void setQuizContent(String quizContent) {
            this.quizContent = quizContent;
        }
    }
}
