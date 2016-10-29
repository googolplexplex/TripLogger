//author: p.schwake
//edited: 2016-10-21 psc
//file: TripActivity.java
//about: Calls TripFragment

package au.edu.usc.triplogger;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.util.UUID;

public class TripActivity extends SingleFragmentActivity {

    private static final int REQUEST_ERROR = 0;

    private static final String EXTRA_TRIP_ID = "au.edu.usc.triplogger.trip_id";
    public static Intent newIntent(Context packageContext, UUID tripId) {
        Intent intent = new Intent(packageContext, TripActivity.class);
        intent.putExtra(EXTRA_TRIP_ID, tripId);
        return intent;
    }

    @Override
    protected Fragment createFragment(){
        UUID tripId = (UUID) getIntent().getSerializableExtra(EXTRA_TRIP_ID);
        return TripFragment.newInstance(tripId);
    }


    @Override
    protected void onResume() { //google maps
        super.onResume();
        int errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (errorCode != ConnectionResult.SUCCESS) {Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(errorCode, this, REQUEST_ERROR, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {// Leave if services are unavailable.
                finish();
            }
        });
            errorDialog.show();
        }
    }



}
