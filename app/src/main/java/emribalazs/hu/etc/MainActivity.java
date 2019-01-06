package emribalazs.hu.etc;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final String TAG = "MainMenuLOG";
    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
    }

    public void choseButtonClicked(View v) {
        mIntent = new Intent(this, MapsActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        Log.d(TAG, "choseButtonClicked: ");
        startActivity(mIntent);

    }

    public void gambleButtonClicked(View v){
        finish();
    }

    public void profileButtonClicked(View v){

    }
}
