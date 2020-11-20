package com.example.pd905.chatbot3.model;


import java.util.List;

/**
 * Created by pd905 on 2017/8/3.
 */

public class SingleSelectQuiz extends Quiz {

    private String[] mAnswerList;

    public SingleSelectQuiz(int quizId, String quizDetail, String[] answerList) {
        super(quizId, quizDetail);
        mAnswerList = answerList;
    }

    public String[] getAnswerList() {
        return mAnswerList;
    }

    public int getType() {
        //The type code for single selection quiz is 0
        return 0;
    }
}
