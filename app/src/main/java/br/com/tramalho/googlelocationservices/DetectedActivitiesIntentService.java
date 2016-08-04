package br.com.tramalho.googlelocationservices;

import android.app.IntentService;
import android.content.Intent;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;

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
        List<DetectedActivity> probableActivities = actrr.getProbableActivities();
        DetectedActivity[] array = new DetectedActivity[probableActivities.size()];

        Intent resultIntent = new Intent(Const.INTENT_ACTIVITIES_ID);
        resultIntent.putExtra(Const.PROBABLE_ACTIVIES_ID, probableActivities.toArray(array));
        sendBroadcast(intent);
    }
}
