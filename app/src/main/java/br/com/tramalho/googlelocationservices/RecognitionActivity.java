package br.com.tramalho.googlelocationservices;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class RecognitionActivity extends AbstractLocationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognition);
    }

    public void requestActivityUpdatesButtonHandler(View view) {
    }

    public void removeActivityUpdatesButtonHandler(View view) {
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }
}
