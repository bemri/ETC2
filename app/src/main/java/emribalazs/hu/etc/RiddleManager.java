package emribalazs.hu.etc;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class RiddleManager {


    private ArrayList<Riddle> mRiddles;
    private short mActualRiddle;
    private boolean mSolvedAll = false;

    public RiddleManager (ArrayList<Riddle> riddleList){
        mRiddles = riddleList;
        mActualRiddle = 0;
    }


    public boolean evaluateAnswer(String answer){
        if(getActualRiddle().solve(answer)) {
            nextOne();
            return true;
        }
        return false;
    }

    public void nextOne(){
        if(mActualRiddle < mRiddles.size()-1)
            mActualRiddle++;

        else
            mSolvedAll = true;
    }

    public void solveActualRiddle(){
        mRiddles.get(mActualRiddle).setSolved(true);
    }

    public int getNumberOfRiddles(){
        return mRiddles.size();
    }

    public int getNumberOfSolvedRiddles(){
        int solved = 0;
        for (int i = 0; i < mRiddles.size(); i++) {
            if(mRiddles.get(i).isSolved())
                solved++;
        }
        return solved;
    }
    public Riddle getActualRiddle(){
        return mRiddles.get(mActualRiddle);
    }

    public LatLng getCoordinatesOfActualRiddle() {
        return mRiddles.get(mActualRiddle).getCoordinates();
    }

    public String getNameOfActualRiddle() {
        return mRiddles.get(mActualRiddle).getName();
    }

    public String getQestionOfActualRiddle() {
        return mRiddles.get(mActualRiddle).getQuestion();
    }

    public String getAnswerOfActualRiddle() {
        return mRiddles.get(mActualRiddle).getAnswer();
    }

    public short getTypeOfActualRiddle() {
        return mRiddles.get(mActualRiddle).getType();
    }

    public float getZoomOfActualRiddle() {
        return mRiddles.get(mActualRiddle).getZoom();
    }

    public int getFailuresOfActualRiddle() {
        return mRiddles.get(mActualRiddle).getFailures();
    }

    public int getMaxFailuresOfActualRiddle() {
        return mRiddles.get(mActualRiddle).getMaxFailures();
    }

    public ArrayList<String> getChoicesOfActualRiddle() {
        return mRiddles.get(mActualRiddle).getChoices();
    }

    public boolean isActualRiddleSolved() {
        return mRiddles.get(mActualRiddle).isSolved();
    }

    public boolean isActualRiddleFailed(){
        return mRiddles.get(mActualRiddle).isFailed();
    }

    public boolean isSolvedAll() {
        return mSolvedAll;
    }

    public void setSolvedAll(boolean solvedAll) {
        mSolvedAll = solvedAll;
    }
}
