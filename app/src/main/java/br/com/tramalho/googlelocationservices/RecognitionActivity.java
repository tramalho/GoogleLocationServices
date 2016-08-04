package br.com.tramalho.googlelocationservices;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;


public class RecognitionActivity extends AbstractLocationActivity implements ResultCallback<Status> {

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
        configReceiver(false);
        super.onStop();
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

            mTxtDetectedActivities.append("\n");
            mTxtDetectedActivities.append(friendlyType + "-" + confidence +" %");
        }
    }


    public void requestActivityUpdatesButtonHandler(View view) {
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mGoogleApiClient, 1000,
                getActivityDetectionPendingIntent()).setResultCallback(this);
    }

    public void removeActivityUpdatesButtonHandler(View view) {
        ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(mGoogleApiClient,
                getActivityDetectionPendingIntent()).setResultCallback(this);
        mTxtDetectedActivities.setText(getString(R.string.detected_activities_title));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        showLog("onConnected");
    }

    private PendingIntent getActivityDetectionPendingIntent() {
        Intent intent = new Intent(this, DetectedActivitiesIntentService.class);

        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // requestActivityUpdates() and removeActivityUpdates().
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    protected Api<? extends Api.ApiOptions.NotRequiredOptions> getAPI() {
        return ActivityRecognition.API;
    }

    @Override
    public void onResult(@NonNull Status status) {
        showLog(status.toString(), "message: "+status.getStatusMessage());
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
