//author: p.schwake
//edited: 2016-10-22 psc
//file: TripListFragment.java
//about: lists the trips in the main view

package au.edu.usc.triplogger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class TripListFragment extends Fragment {

    private RecyclerView _TripRecyclerView;
    private TripAdapter _Adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_list, container, false);

        _TripRecyclerView = (RecyclerView) view
                .findViewById(R.id.trip_recycler_view);
        _TripRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) { //create options menu
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_trip_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //options menu events
        switch (item.getItemId()) {
            case R.id.menu_item_new_trip:
                Trip trip = new Trip();
                TripDatabase.get(getActivity()).addTrip(trip);
                Intent intent = TripActivity
                        .newIntent(getActivity(), trip.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_settings:

                /////////

                Settings settings = new Settings();
                SettingsDatabase.get(getActivity()).addSettings(settings);
                Intent intent1 = SettingsActivity
                        .newIntent(getActivity(), settings.getUUID());
                startActivity(intent1);

                /////////

                //openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //---------------------------------------------------------------------------------

    private void updateUI() {
        TripDatabase tripDatabase = TripDatabase.get(getActivity());
        List<Trip> trips = tripDatabase.getTrips();

        if (_Adapter == null) {
            _Adapter = new TripAdapter(trips);
            _TripRecyclerView.setAdapter(_Adapter);
        } else {
            _Adapter.setTrips(trips);
            _Adapter.notifyDataSetChanged();
        }

    }

    //---------------------------------------------------------------------------------

    //public void openSettings(){
        //Intent intent = SettingsActivity.newIntent(getActivity(), _Settings.getUUID());
        //startActivity(intent);
    //}



//###################################################################################private classes


    private class TripHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView _TitleTextView;
        private TextView _DateTextView;
        private TextView _DestinationTextView;


        private Trip _Trip;

        public TripHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            _TitleTextView = (TextView) itemView.findViewById(R.id.list_item_trip_title_text_view);
            _DateTextView = (TextView) itemView.findViewById(R.id.list_item_trip_date_text_view);
            _DestinationTextView = (TextView) itemView.findViewById(R.id.list_item_trip_destination_text_view);

        }

        public void bindTrip(Trip trip) {
            _Trip = trip;
            if(_Trip.getTitle() != null) {
                _TitleTextView.setText(_Trip.getTitle());
            }
            else {
                _TitleTextView.setText("N/A");
            }
            if(_Trip.getDate() != null) {
                _DateTextView.setText(_Trip.getDate().toString());
            }
            if(_Trip.getDestination() != null) {
                _DestinationTextView.setText(_Trip.getDestination().toString());
            }
            else{
                _DestinationTextView.setText("N/A");
            }

        }

        @Override
        public void onClick(View v) {
            Intent intent = TripViewActivity.newIntent(getActivity(), _Trip.getId());
            startActivity(intent);
        }
    }


//-----------------------------------------------------------------------------------

    private class TripAdapter extends RecyclerView.Adapter<TripHolder> {

        private List<Trip> _Trips;

        public TripAdapter(List<Trip> trips) {
            _Trips = trips;
        }

        @Override
        public TripHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_trip, parent, false);
            return new TripHolder(view);
        }

        @Override
        public void onBindViewHolder(TripHolder holder, int position) {
            Trip trip = _Trips.get(position);
            holder.bindTrip(trip);
        }

        @Override
        public int getItemCount() {
            return _Trips.size();
        }

        public void setTrips(List<Trip> trips) {
            _Trips = trips;
        }
    }

}