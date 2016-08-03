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

import com.google.android.gms.location.LocationServices;

import java.util.Arrays;

public class UniqueLocationActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private final String TAG = this.getClass().getSimpleName();

    private TextView txtLatitude;
    private TextView txtLongitude;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unique_location);
        loadUI();
        createGoogleAPIClient();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mGoogleApiClient != null && mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }

    private void loadUI() {
        setTitle(R.string.unique_location);
        txtLatitude  = (TextView) findViewById(R.id.txt_output_unique_location_latitude);
        txtLongitude = (TextView) findViewById(R.id.txt_output_unique_location_longitude);

        updateLatLong(0, 0);
    }

    private synchronized void createGoogleAPIClient() {
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);

        mGoogleApiClient = builder
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    private void updateLatLong(double lat, double lon){
        txtLatitude.setText(getString(R.string.label_latitude,  lat));
        txtLongitude.setText(getString(R.string.label_longitude, lon));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if(mLastLocation != null){
            updateLatLong(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        showLog("onConnectionSuspended", "status: "+i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        showLog("onConnectionFailed", connectionResult.getErrorMessage());
    }

    private void showLog(String message, String... addictional){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Log.d(TAG, message +" - "+ Arrays.toString(addictional));
    }
}
