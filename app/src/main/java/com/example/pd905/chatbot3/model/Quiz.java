package com.example.pd905.chatbot3.model;

import java.util.ArrayList;

/**
 * Created by pd905 on 2017/8/2.
 */


public abstract class Quiz {
    private final int mQuizId;
    private final String mQuizDetail;

    public Quiz(int quizId, String quizDetail) {
        mQuizId = quizId;
        mQuizDetail = quizDetail;
    }

    public int getQuizId() {
        return mQuizId;
    }

    public String getQuizDetail() {
        return mQuizDetail;
    }

    public abstract int getType();


    public abstract String[] getAnswerList();
}