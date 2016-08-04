package br.com.tramalho.googlelocationservices;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

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
        } else {

            int geofenceTransition = geofencingEvent.getGeofenceTransition();

            if(Geofence.GEOFENCE_TRANSITION_ENTER == geofenceTransition ||
                    Geofence.GEOFENCE_TRANSITION_EXIT == geofenceTransition){

                List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

                String ids = concatGeofenceIds(triggeringGeofences);

            }
        }
    }

    private String concatGeofenceIds(List<Geofence> triggeringGeofences) {

        StringBuilder builder = new StringBuilder();

        for (Geofence triggeringGeofence : triggeringGeofences) {
            builder.append(triggeringGeofence.getRequestId());
        }

        return builder.toString();
    }
}
