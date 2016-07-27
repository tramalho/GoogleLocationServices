package br.com.tramalho.googlelocationservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UniqueLocationActivity extends AppCompatActivity {

    private TextView txtLatitude;
    private TextView txtLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unique_location);
        loadUI();
    }

    private void loadUI() {
        setTitle(R.string.unique_location);
        txtLatitude  = (TextView) findViewById(R.id.txt_output_unique_location_latitude);
        txtLongitude = (TextView) findViewById(R.id.txt_output_unique_location_longitude);
        updateLatLong("0", "0");
    }

    private void updateLatLong(String lat, String lon){
        txtLatitude.setText(getString(R.string.label_latitude,  lat));
        txtLongitude.setText(getString(R.string.label_longitude, lon));
    }
}
