package com.example.pd905.chatbot3.model;


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

    public QuizType getType() {
        //The QuizType is defined in QuizType enum file
        return QuizType.SINGLE_SELECTION_QUIZ;
    }
}
