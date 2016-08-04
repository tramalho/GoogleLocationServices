package br.com.tramalho.googlelocationservices;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.google.android.gms.location.LocationServices;

public class UniqueLocationActivity extends AbstractLocationActivity {

    private TextView txtLatitude;
    private TextView txtLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unique_location);
        loadUI();
        createGoogleAPIClient();
    }

    protected void loadUI() {
        setTitle(R.string.unique_location);
        txtLatitude  = (TextView) findViewById(R.id.txt_output_unique_location_latitude);
        txtLongitude = (TextView) findViewById(R.id.txt_output_unique_location_longitude);

        updateLatLong(0, 0);
    }

    private void updateLatLong(double lat, double lon){
        txtLatitude.setText(getString(R.string.label_latitude,  lat));
        txtLongitude.setText(getString(R.string.label_longitude, lon));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        showLog("onConnected");
        Location mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if(mLastLocation != null){
            updateLatLong(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }

    }

}
