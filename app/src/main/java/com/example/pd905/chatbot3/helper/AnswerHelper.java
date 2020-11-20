package com.example.pd905.chatbot3.helper;

import android.util.Log;
import android.util.SparseBooleanArray;

/**
 * Created by pd905 on 2017/8/2.
 */

public class AnswerHelper {

    static final String SEPARATOR = System.getProperty("line.separator");
    private static final String TAG = "AnswerHelper";

    private AnswerHelper() {
        //no instance
    }

    /**
     * Converts an array of answers to a readable answer
     * @param answers
     * @return
     */
    public static String getAnswer(String[] answers) {
        StringBuilder readableAnswer = new StringBuilder();
        //iterate over all answers
        for (int i = 0; i < answers.length; i++) {
            String answer = answers[i];
            readableAnswer.append(answer);
            //don't add a separator for the last answer
            if(i < answers.length - 1){
                readableAnswer.append(SEPARATOR);
            }
        }
        return readableAnswer.toString();
    }

    /**
     * Converts an array of answers with options to a readable answer
     * @param answers
     * @param options
     * @return
     */
    public static String getAnswer(int[] answers, String[] options) {
        String[] readableAnswers = new String[answers.length];
        for (int i = 0; i < answers.length; i ++) {
            final String humanReadableAnswer = options[answers[i]];
            readableAnswers[i] = humanReadableAnswer;
        }
        return getAnswer(readableAnswers);
    }

    public static boolean isAnswerCorrect(SparseBooleanArray checkedItems, int[] answerIds) {
        if (null == checkedItems || null == answerIds) {
            Log.i(TAG, "isAnswerCorrect got a null parameter input");
            return false;
        }
        for (int answer:answerIds) {
            if (0 > checkedItems.indexOfKey(answer)) {
                return false;
            }
        }
        return checkedItems.size() == answerIds.length;
    }
}
