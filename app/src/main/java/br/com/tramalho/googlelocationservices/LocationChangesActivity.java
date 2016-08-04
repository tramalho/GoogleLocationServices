package br.com.tramalho.googlelocationservices;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Arrays;

public class LocationChangesActivity extends AbstractLocationActivity implements LocationListener {

    private TextView txtOutput;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_changes);
        loadUI();
        createGoogleAPIClient();
    }

    protected void loadUI() {
        setTitle(R.string.location_changes);
        txtOutput = (TextView) findViewById(R.id.txt_output_location_changes);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        showLog("onConnected");

            locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(1000);

        LocationServices.FusedLocationApi
                .requestLocationUpdates(mGoogleApiClient, locationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        showLog("onLocationChanged", location.toString());
        txtOutput.append("\n "+location.toString());
    }

    private void showLog(String message, String... addictional){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Log.d(TAG, message +" - "+Arrays.toString(addictional));
    }
}
