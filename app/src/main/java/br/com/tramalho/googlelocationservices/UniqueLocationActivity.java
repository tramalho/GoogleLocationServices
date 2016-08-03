package br.com.tramalho.googlelocationservices;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class UniqueLocationActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private final String TAG = this.getClass().getSimpleName();

    private TextView txtLatitude;
    private TextView txtLongitude;
    private LocationRequest mLocationRequest;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unique_location);
        loadUI();
        createGoogleAPIClient();
    }

    private void loadUI() {
        setTitle(R.string.unique_location);
        txtLatitude  = (TextView) findViewById(R.id.txt_output_unique_location_latitude);
        txtLongitude = (TextView) findViewById(R.id.txt_output_unique_location_longitude);

        updateLatLong("0", "0");
    }

    private synchronized void createGoogleAPIClient() {
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);

        googleApiClient = builder
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    private void updateLatLong(String lat, String lon){
        txtLatitude.setText(getString(R.string.label_latitude,  lat));
        txtLongitude.setText(getString(R.string.label_longitude, lon));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
