package emribalazs.hu.etc;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class Riddle {

    public static short RIDDLETYPE_ONLY_NUMBER = 1;
    public static short RIDDLETYPE_ONLY_TEXT = 2;
    public static short RIDDLETYPE_TEXT_CHOICE = 3;
    public static short RIDDLETYPE_PICTURE_CHOICE = 4;
    public static short RIDDLETYPE_AUDIO_CHOICE = 5;
    public static short RIDDLETYPE_TEXT_CHOICE_TIMED = 6;
    public static short RIDDLETYPE_PICTURE_CHOICE_TIMED = 7;


    private LatLng mCoordinates;
    private String mName;
    private String mQuestion;
    private String mAnswer;
    private short mType;
    private float mZoom = 17.0f;
    private int mMaxFailures = 3;
    private ArrayList<String> mChoices = null;

    private int mFailures;
    private boolean mSolved = false;

    public Riddle(){

    }

    public boolean solve(String answer){
        if(answer.equals(mAnswer)) {
            mSolved = true;
            return true;
        }
        increaseFailures();
        return false;
    }

    public void increaseFailures(){
        mFailures++;
    }

    public boolean isFailed() {
        return mFailures >= mMaxFailures;
    }

    public LatLng getCoordinates() {
        return mCoordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        mCoordinates = coordinates;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        mAnswer = answer;
    }

    public short getType() {
        return mType;
    }

    public void setType(short type) {
        mType = type;
    }

    public float getZoom() {
        return mZoom;
    }

    public void setZoom(float zoom) {
        mZoom = zoom;
    }

    public int getFailures() {
        return mFailures;
    }

    public void setFailures(int failures) {
        mFailures = failures;
    }

    public int getMaxFailures() {
        return mMaxFailures;
    }

    public void setMaxFailures(int maxFailures) {
        mMaxFailures = maxFailures;
    }

    public ArrayList<String> getChoices() {
        return mChoices;
    }

    public void setChoices(ArrayList<String> choices) {
        mChoices = choices;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
