package br.com.tramalho.googlelocationservices;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

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
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        ActivityRecognitionResult actrr = ActivityRecognitionResult.extractResult(intent);
        ArrayList<DetectedActivity> probableActivities =
                (ArrayList<DetectedActivity>) actrr.getProbableActivities();

        Intent resultIntent = new Intent(Const.BROADCAST_ACTION);
        resultIntent.putExtra(Const.PROBABLE_ACTIVIES_EXTRA, probableActivities);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
