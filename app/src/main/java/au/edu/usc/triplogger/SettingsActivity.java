//author: p.schwake
//edited: 2016-10-26 psc
//file: SettingsActivity.java
//about: Calls SettingsFragment

package au.edu.usc.triplogger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.UUID;

public class SettingsActivity extends SingleFragmentActivity{//extends AppCompatActivity{//

    private static final String EXTRA_SETTINGS_ID = "au.edu.usc.triplogger.settings_id";


    @Override
    protected Fragment createFragment() {

        UUID settingsId = (UUID) getIntent().getSerializableExtra(EXTRA_SETTINGS_ID);
        return SettingsFragment.newInstance(settingsId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_trip);
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, SettingsActivity.class);
        return intent;
    }


    public static Intent newIntent(Context packageContext, UUID settingsID) {
        Intent intent = new Intent(packageContext, SettingsActivity.class);
        intent.putExtra(EXTRA_SETTINGS_ID, settingsID);
        return intent;
    }


}
