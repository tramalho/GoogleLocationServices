package br.com.tramalho.googlelocationservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;

public class RecognitionActivity extends AbstractLocationActivity {

    private TextView mTxtDetectedActivities;
    private ActivityDetectionBroadcast mBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognition);
        loadUI();
        mBroadcastReceiver = new ActivityDetectionBroadcast();
        createGoogleAPIClient();
    }

    @Override
    protected void onResume() {
        super.onResume();
        configReceiver(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        configReceiver(false);
    }

    private void configReceiver(boolean isRegister) {
        IntentFilter intentFilter = new IntentFilter(Const.BROADCAST_ACTION);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);

        broadcastManager.unregisterReceiver(mBroadcastReceiver);

        if(isRegister){
            broadcastManager.registerReceiver(mBroadcastReceiver, intentFilter);
        }
    }

    private void loadUI() {
        mTxtDetectedActivities = (TextView) findViewById(R.id.detectedActivities);
    }

    private void updateUI(ArrayList<DetectedActivity> parcelableArrayListExtra) {
        for (DetectedActivity detectedActivity : parcelableArrayListExtra) {

            int confidence = detectedActivity.getConfidence();
            String friendlyType = DetectedActivity.zzsu(detectedActivity.getType());

            mTxtDetectedActivities.append(friendlyType + "-" + confidence +"%");
        }
    }


    public void requestActivityUpdatesButtonHandler(View view) {
    }

    public void removeActivityUpdatesButtonHandler(View view) {
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        showLog("onConnected");
    }

    @Override
    protected Api<? extends Api.ApiOptions.NotRequiredOptions> getAPI() {
        return ActivityRecognition.API;
    }

    class ActivityDetectionBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            ArrayList<DetectedActivity> parcelableArrayListExtra =
                    intent.getParcelableArrayListExtra(Const.PROBABLE_ACTIVIES_EXTRA);

            if(parcelableArrayListExtra != null) {
                updateUI(parcelableArrayListExtra);
            }
        }
    }
}
