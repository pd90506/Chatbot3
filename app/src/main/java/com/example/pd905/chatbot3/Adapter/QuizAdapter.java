package com.example.pd905.chatbot3.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.pd905.chatbot3.R;
import com.example.pd905.chatbot3.fragments.QuizFragment;
import com.example.pd905.chatbot3.model.Quiz;
import com.example.pd905.chatbot3.model.SingleSelectQuiz;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by pd905 on 2017/7/30.
 */

public class QuizAdapter extends BaseAdapter {

    private final Context mContext;
    //private final String[] mQuizzes;
    private LayoutInflater mInflater;
    private  Quiz[] mQuizzes;
    //private List<String> mQuizTypes;

    public QuizAdapter(Context context) {
        mContext = context;
        mQuizzes = new Quiz[] {
                new SingleSelectQuiz(0, "Is it your first time having a baby?", new String[] {"Yes","No"}),
                new SingleSelectQuiz(1, "What is your highest degree earned?", new String[] {"High school or lower", "Bachelor","Master", "PhD"}),
                new SingleSelectQuiz(2, "Who is going to take care of your child?", new String[] {"Self","Spouse", "Siblings", "Grandparents", "Nanny"}),
        };
        mInflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return mQuizzes.length;
    }

    @Override
    public Object getItem(int position) {
        return mQuizzes[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            //inflate quiz_view to parent view
            //Context context = parent.getContext();
            convertView = mInflater.inflate(R.layout.quiz_view, parent, false);

        }
        //set the content of view once selected at position
        TextView questionText = (TextView) convertView.findViewById(R.id.question_text);
        questionText.setText(mQuizzes[position].getQuizDetail());

        FrameLayout answerContainer = (FrameLayout) convertView.findViewById(R.id.answer_container);
        setAnswerView(answerContainer, position);

        //button click
        //Button submitButton = (Button) convertView.findViewById(R.id.submit_button);
        //submitButton.setOnClickListener(parent);
        return convertView;
    }

    private void setAnswerView(FrameLayout container, int position) {
        View view = mInflater.inflate(R.layout.answer_view,container,true);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.choices);
        String[] answerList = mQuizzes[position].getAnswerList();

        for (int i = 0; i < answerList.length; i++) {
            RadioButton answerChoice = new RadioButton(mContext);
            answerChoice.setText(answerList[i]);
            answerChoice.setTextSize(18);
            radioGroup.addView(answerChoice);
        }



    }
}
