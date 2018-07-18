package emribalazs.hu.etc;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, SolutionDialog.SolutionMapCommunicator {
    private static final String TAG = "myapp";
    private static final String RIDDLE_STATE = "riddle_state";
    //google map API
    private GoogleMap mMap;

    //Dialogs
    private SolutionDialog mSolutionDialogasd;

    //Riddles
    private RiddleManager mRiddleManager;

    //GUI
    private TextView mFailuresTextView;
    private TextView mProgressTextViewAll;
    private TextView mProgressTextViewDone;

    //current riddle and map state
    private LatLng mCoordinates;
    private float mZoom;
    private Marker mMarker;
    private boolean mRecoverState = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        initGUIElements();

        if(savedInstanceState!=null) {
            Log.d(TAG, "onCreate: NOT NULL, not first start");
            mRecoverState = true;
            mCoordinates = new LatLng(savedInstanceState.getDouble("lat"), savedInstanceState.getDouble("lon"));
            mZoom = savedInstanceState.getFloat("zoom");
        }
        else {
            Log.d(TAG, "onCreate: NULL, first start");
        }

        initRiddleManager(R.raw.riddles_hi);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("lat", mMap.getCameraPosition().target.latitude);
        outState.putDouble("lon", mMap.getCameraPosition().target.longitude);
        outState.putFloat("zoom", mMap.getCameraPosition().zoom);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState: ");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        initRiddle(mRecoverState);
    }

    public void initGUIElements(){
        mFailuresTextView = findViewById(R.id.failuresTextView);
        mProgressTextViewAll = findViewById(R.id.progressTextViewAll);
        mProgressTextViewDone = findViewById(R.id.progressTextViewDone);
    }

    public void initRiddleManager(int resourceId){
        RiddleXMLParser xpp = new RiddleXMLParser(getApplicationContext());
        try{
            ArrayList<Riddle> list = xpp.readRiddles(resourceId);
            mRiddleManager = new RiddleManager(list);
        }
        catch (IOException e){
            System.out.println("io");
        }
        catch (XmlPullParserException e){
            System.out.println("xml");
        }
    }

    public void initRiddle(boolean recoverState){

        mMarker = mMap.addMarker(new MarkerOptions().position(mRiddleManager.getCoordinatesOfActualRiddle()).title(mRiddleManager.getNameOfActualRiddle()));
        mMarker.setSnippet(mRiddleManager.getQestionOfActualRiddle());
        refreshTextViews();

        if(recoverState) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mCoordinates));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(mZoom));
        }

        else {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mRiddleManager.getCoordinatesOfActualRiddle()));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(mRiddleManager.getZoomOfActualRiddle()));
        }
    }


    public void refreshTextViews(){
        mFailuresTextView.setText(Integer.toString(mRiddleManager.getFailuresOfActualRiddle()));
        mProgressTextViewAll.setText(Integer.toString(mRiddleManager.getNumberOfRiddles()));
        mProgressTextViewDone.setText(Integer.toString(mRiddleManager.getNumberOfSolvedRiddles()));
    }

    public void solutionButtonClicked(View v) {
        FragmentManager fragmentManager = getFragmentManager();

        Intent intent = new Intent(this, SolutionDialog.class);
        Bundle bundle = new Bundle();
        bundle.putString("question", mRiddleManager.getQestionOfActualRiddle());
        bundle.putString("answer", mRiddleManager.getAnswerOfActualRiddle());
        intent.putExtras(bundle);

        mSolutionDialog = new SolutionDialog();
        mSolutionDialog.setArguments(bundle);
        mSolutionDialog.show(fragmentManager, "Solution Dialog");
    }

    @Override
    public void answerReceived(String answer) {
        if(mRiddleManager.evaluateAnswer(answer)) {
            if(!mRiddleManager.isSolvedAll()) {
                initRiddle(mRecoverState);
            }
            else {
                mFailuresTextView.setText("VICTORY");
                mMap.animateCamera(CameraUpdateFactory.zoomTo(5));
            }
        }
        else if (!mRiddleManager.isActualRiddleFailed())
            refreshTextViews();
        else
            mFailuresTextView.setText("FAIL!");
    }

}