package com.example.pd905.chatbot3.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.pd905.chatbot3.R;
import com.example.pd905.chatbot3.model.Quiz;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by pd905 on 2017/7/30.
 */

public class QuizAdapter extends BaseAdapter {

    private final Context mContext;
    private final String[] mQuizzes;
    private LayoutInflater mInflater;
    //private List<String> mQuizTypes;

    public QuizAdapter(Context context) {
        mContext = context;
        mQuizzes = new String[] {mContext.getString(R.string.quiz1), mContext.getString(R.string.quiz2),
        mContext.getString(R.string.quiz3)};
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
        questionText.setText(mQuizzes[position]);

        FrameLayout answerContainer = (FrameLayout) convertView.findViewById(R.id.answer_container);
        setAnswerView(answerContainer, position);
        return convertView;
    }

    private void setAnswerView(FrameLayout container, int position) {
        View view = mInflater.inflate(R.layout.answer_view,container,true);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.choices);
        RadioButton answerChoice = new RadioButton(mContext);
        answerChoice.setText("Choice 1");
        answerChoice.setTextSize(18);
        radioGroup.addView(answerChoice);


    }
}
