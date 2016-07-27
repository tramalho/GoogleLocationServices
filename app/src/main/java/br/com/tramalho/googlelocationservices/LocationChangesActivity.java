package br.com.tramalho.googlelocationservices;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Arrays;

public class LocationChangesActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private final String TAG = this.getClass().getSimpleName();


    private GoogleApiClient googleApiClient;
    private TextView txtOutput;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createGoogleAPIClient();
        loadUI();

    }

    private void loadUI() {
        txtOutput = (TextView) findViewById(R.id.txt_output);
    }

    private void createGoogleAPIClient() {
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);

        googleApiClient = builder
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        showLog("onConnected");

            locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(1000);

        LocationServices.FusedLocationApi
                .requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        showLog("onConnectionSuspended", "status: "+i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        showLog("onConnectionFailed", connectionResult.getErrorMessage());
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
