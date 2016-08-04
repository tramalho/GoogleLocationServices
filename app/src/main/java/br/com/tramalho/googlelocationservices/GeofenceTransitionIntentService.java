package br.com.tramalho.googlelocationservices;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.GeofencingEvent;

/**
 * Created by tramalho on 04/08/16.
 */
public class GeofenceTransitionIntentService extends IntentService {

    private static final String TAG = GeofenceTransitionIntentService.class.getSimpleName();

    public GeofenceTransitionIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if(geofencingEvent.hasError()){
            String errorString = GeofenceErrorMessages
                    .getErrorString(getApplicationContext(), geofencingEvent.getErrorCode());

            Log.e(TAG, "onHandleIntent: "+errorString);
        }
    }
}
