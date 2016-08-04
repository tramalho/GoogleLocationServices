package br.com.tramalho.googlelocationservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GeofenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geofence);
        setTitle(R.string.geofence);
    }

    public void addGeofencesButtonHandler(View view) {

    }
}
