package com.example.pd905.chatbot3.model;

/**
 * Created by pd905 on 2017/8/2.
 */

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.pd905.chatbot3.helper.ParcelableHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * This abstract class provides general structure for quizzes.
 *

 */
public abstract class Quiz<A> implements Parcelable {

    private static final String TAG = "Quiz";

    /**
     * Implement non-null static field called CREATOR of a type that
     * implements the Parcelable.Creator interface
     */
    public static final Creator<Quiz> CREATOR = new Creator<Quiz>() {
        @Override
        public Quiz createFromParcel(Parcel source) {
            int ordinal = source.readInt();
            QuizType type = QuizType.values()[ordinal];

            try {
                Constructor<? extends Quiz> constructor = type.getType()
                        .getConstructor(Parcel.class);
                return constructor.newInstance(source);

            } catch (InstantiationException e) {
                performLegacyCatch(e);
            } catch (NoSuchMethodException e) {
                performLegacyCatch(e);
            } catch (InvocationTargetException e) {
                performLegacyCatch(e);
            } catch (IllegalAccessException e) {
                performLegacyCatch(e);
            }
            throw new UnsupportedOperationException("Could not create Quiz");
        }

        @Override
        public Quiz[] newArray(int size) {
            return new Quiz[size];
        }
    };

    private static void performLegacyCatch(Exception e) {
        Log.e(TAG, "createFromParcel ", e);
    }


    /**
     * Need comments
     */
    private final String mQuestion;
    private final String mQuizType;
    //Only answers are different.
    private A mAnswer;
    //Flag indicating whether this quiz has already been solved.
    private boolean mSolved;

    /**
     * @return The {@link QuizType} that represents this quiz.
     * this is an abstract method!!! not class or instance
     */
    public abstract QuizType getType();

    /**
     * Implementations need to return a human readable version of the given answer.
     */
    public abstract String getStringAnswer();

    protected Quiz(String question, A answer, boolean solved) {
        mQuestion = question;
        mAnswer = answer;
        mQuizType = getType().getJsonName();
        mSolved = solved;
    }

    protected Quiz(Parcel in) {
        mQuestion = in.readString();
        mQuizType = getType().getJsonName();
        mSolved = ParcelableHelper.readBoolean(in);
    }


    public String getQuestion() {
        return mQuestion;
    }

    public A getAnswer() {
        return mAnswer;
    }

    protected void setAnswer(A answer) {
        mAnswer = answer;
    }

    public boolean isAnswerCorrect(A answer) {
        return mAnswer.equals(answer);
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    /**
     * return the id of this quiz
     */
    public int getId() {
        return getQuestion().hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        ParcelableHelper.writeEnumValue(dest, getType());
        dest.writeString(mQuestion);
        ParcelableHelper.writeBoolean(dest, mSolved);
    }

    /**
     * Override equals method
     * @param o The object to be compared with
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof  Quiz)) return false;

        Quiz quiz = (Quiz) o;

        if (mSolved != quiz.mSolved) {
            return false;
        }
        if (!mAnswer.equals(quiz.mAnswer)) {
            return false;
        }
        if (!mQuestion.equals(quiz.mQuestion)) {
            return false;
        }
        if (!mQuizType.equals(quiz.mQuizType)) {
            return false;
        }

        return true;
    }

    /**
     * Just to ensure the hash code id is unique
     * @return
     */
    @Override
    public int hashCode() {
        int result = mQuestion.hashCode();
        result = 31 * result + mAnswer.hashCode();
        result = 31 * result + mQuizType.hashCode();
        result = 31 * result + (mSolved ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return getType() + ": \"" + getQuestion() + "\"";
    }

}