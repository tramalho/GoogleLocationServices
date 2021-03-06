package br.com.tramalho.googlelocationservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickButton(View view) {

        Class<?> targetActivity = LocationChangesActivity.class;

        if(view.getId() == R.id.button_unique_location){
            targetActivity = UniqueLocationActivity.class;
        }
        else if(view.getId() == R.id.button_recognition){
            targetActivity = RecognitionActivity.class;
        }
        else if(view.getId() == R.id.button_geofence){
            targetActivity = GeofenceActivity.class;
        }

        startActivity(new Intent(this, targetActivity));
    }
}
