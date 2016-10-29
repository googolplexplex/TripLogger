//author: p.schwake
//edited: 2016-10-23 psc
//file: TripViewActivity.java
//about: Calls TripViewFragment

package au.edu.usc.triplogger;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;


import java.util.UUID;

public class TripViewActivity extends SingleFragmentActivity {

    private static final String EXTRA_TRIP_ID = "au.edu.usc.triplogger.trip_id";
    public static Intent newIntent(Context packageContext, UUID tripId) {
        Intent intent = new Intent(packageContext, TripViewActivity.class);
        intent.putExtra(EXTRA_TRIP_ID, tripId);
        return intent;
    }

    @Override
    protected Fragment createFragment(){
        UUID tripId = (UUID) getIntent().getSerializableExtra(EXTRA_TRIP_ID);
        return TripViewFragment.newInstance(tripId);

    }
}
