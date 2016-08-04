package br.com.tramalho.googlelocationservices;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.Arrays;

/**
 * Created by trama on 03/08/16.
 */
public abstract class AbstractLocationActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    protected final String TAG = this.getClass().getSimpleName();

    protected GoogleApiClient mGoogleApiClient;

    @Override
    protected void onStart() {
        super.onStart();
        connect();
    }

    private void connect() {
        if(mGoogleApiClient != null && !mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mGoogleApiClient != null && mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }

    protected synchronized void createGoogleAPIClient() {
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);

        mGoogleApiClient = builder
                .addApi(getAPI())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void onConnectionSuspended(int i) {
        showLog("onConnectionSuspended", "status: "+i);
        connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        showLog("onConnectionFailed", connectionResult.getErrorMessage());
    }

    protected void showLog(String message, String... addictional){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        debugLog(message, addictional);
    }

    protected void debugLog(String message, String[] addictional) {
        Log.d(TAG, message +" - "+ Arrays.toString(addictional));
    }

    protected Api<? extends Api.ApiOptions.NotRequiredOptions> getAPI() {
        return LocationServices.API;
    }
}
