//author: p.schwake
//edited: 2016-10-21 psc
//file: TripListActivity.java
//about: calls TripListFragment()

package au.edu.usc.triplogger;

import android.support.v4.app.Fragment;

public class TripListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment(){
        return new TripListFragment();
    }
}
