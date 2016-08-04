package br.com.tramalho.googlelocationservices;

import android.app.IntentService;
import android.content.Intent;

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

    }
}
