package br.com.tramalho.googlelocationservices;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;

/**
 * Created by trama on 03/08/16.
 */
public class DetectedActivitiesIntentService extends IntentService {

    private static final String TAG = DetectedActivitiesIntentService.class.getSimpleName();

    public DetectedActivitiesIntentService() {
        super(TAG);
        Log.d(TAG, "constructor");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG, "onHandleIntent");


        ActivityRecognitionResult actrr = ActivityRecognitionResult.extractResult(intent);

        ArrayList<DetectedActivity> probableActivities =
                (ArrayList<DetectedActivity>) actrr.getProbableActivities();

        Log.d(TAG, probableActivities.toString());

        Intent resultIntent = new Intent(Const.BROADCAST_ACTION);
        resultIntent.putExtra(Const.PROBABLE_ACTIVIES_EXTRA, probableActivities);
        LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);
    }
}
