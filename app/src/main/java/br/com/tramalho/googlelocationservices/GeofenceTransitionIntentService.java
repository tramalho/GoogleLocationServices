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

            Log.e(TAG, "onHandleIntent [error]: "+errorString);
        } else {

            int geofenceTransition = geofencingEvent.getGeofenceTransition();

            if(Geofence.GEOFENCE_TRANSITION_ENTER == geofenceTransition ||
                    Geofence.GEOFENCE_TRANSITION_EXIT == geofenceTransition){

                List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

                String ids = concatGeofenceIds(triggeringGeofences, geofenceTransition);

                Log.d(TAG, "onHandleIntent [ids]: "+ids);
            }
        }
    }

    private String concatGeofenceIds(List<Geofence> triggeringGeofences, int geofenceTransition) {

        StringBuilder builder = new StringBuilder(""+geofenceTransition);
        builder.append(":");

        int size = triggeringGeofences.size();

        for (int i = 0; i < size; i++) {
            builder.append(triggeringGeofences.get(i).getRequestId());

            if(i <  (size - 1)){
                builder.append(",");
            }
        }

        return builder.toString();
    }
}
