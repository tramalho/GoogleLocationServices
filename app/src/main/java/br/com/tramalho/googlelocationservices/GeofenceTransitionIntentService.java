package br.com.tramalho.googlelocationservices;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
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

                sendNotification(ids);
            }
        }
    }

    private void sendNotification(String ids) {

        // Create an explicit content Intent that starts the main Activity.
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);

        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(MainActivity.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        // Define the notification settings.
        builder.setSmallIcon(R.mipmap.ic_launcher)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher))
                .setColor(Color.RED)
                .setContentTitle(ids)
                .setContentText(getString(R.string.geofence_transition_notification_text))
                .setContentIntent(notificationPendingIntent);

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);

        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Issue the notification
        mNotificationManager.notify(0, builder.build());
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
