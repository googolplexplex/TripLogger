//author: p.schwake
//edited: 2016-10-28 psc
//file: TripFragment.java
//about: does stuff and junk
//location source: http://blog.teamtreehouse.com/beginners-guide-location-android

package au.edu.usc.triplogger;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.util.Locale;
import java.util.UUID;

public class TripFragment extends Fragment {

    private static final String ARG_TRIP_ID = "trip_id";
    private static final int REQUEST_PHOTO = 1;
    private static final String TAG = "TripFragment";

    private Trip _Trip;
    private File _PhotoFile;
    private EditText _TitleField;
    private Button _DateButton;
    private Spinner _TripType;
    private EditText _DestinationField;
    private EditText _DurationField;
    private EditText _CommentField;
    private ImageButton _PhotoButton;
    private ImageView _PhotoView;
    private EditText _LocationField;
    private GoogleApiClient gClient;
    private Location gCurrentLocation;
    private Context _Context;


    //---------------------------------------------------------------------------

    public static TripFragment newInstance(UUID tripId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TRIP_ID, tripId);

        TripFragment fragment = new TripFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID tripId = (UUID) getArguments().getSerializable(ARG_TRIP_ID);
        _Trip = TripDatabase.get(getActivity()).getTrip(tripId);
        _PhotoFile = TripDatabase.get(getActivity()).getPhotoFile(_Trip);

        //add the location services api
        gClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        getActivity().invalidateOptionsMenu();
                        findLocation();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                    }
                })
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().invalidateOptionsMenu();
        gClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        TripDatabase.get(getActivity()).updateTrip(_Trip);
    }

    @Override
    public void onStop() {
        super.onStop();
        gClient.disconnect();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_trip, container, false);

        //title field

        _TitleField = (EditText) v.findViewById(R.id.trip_title);
        _TitleField.setText(_Trip.getTitle());
        _TitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _Trip.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //destination field

        _DestinationField = (EditText) v.findViewById(R.id.trip_destination);
        _DestinationField.setText(_Trip.getDestination());
        _DestinationField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _Trip.setDestination(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        //duration field

        _DurationField = (EditText) v.findViewById(R.id.trip_duration);
        _DurationField.setText(_Trip.getDuration());
        _DurationField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _Trip.setDuration(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        //comment field

        _CommentField = (EditText) v.findViewById(R.id.trip_comment);
        _CommentField.setText(_Trip.getComment());
        _CommentField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _Trip.setComment(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        //date button

        _DateButton = (Button) v.findViewById(R.id.trip_date);
        updateDate();

        //triptype Spinner

        _TripType = (Spinner) v.findViewById(R.id.trip_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.trip_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _TripType.setAdapter(adapter);

        //photo button

        PackageManager packageManager = getActivity().getPackageManager();
        _PhotoButton = (ImageButton) v.findViewById(R.id.trip_camera);

        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = _PhotoFile != null && captureImage.resolveActivity(packageManager) != null;
        _PhotoButton.setEnabled(canTakePhoto);
        if (canTakePhoto) {
            Uri uri = Uri.fromFile(_PhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        _PhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i(TAG, "photo() Latitude in DB: " + _Trip.getLatitude());
                Log.i(TAG, "photo() Longitude in DB: " + _Trip.getLongitude());

                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        _PhotoView = (ImageView) v.findViewById(R.id.trip_photo);
        updatePhotoView();


        //location field
        Log.i(TAG, "onCreateView() Latitude in DB: " + _Trip.getLatitude());
        Log.i(TAG, "onCreateView() Longitude in DB: " + _Trip.getLongitude());

        _LocationField = (EditText) v.findViewById(R.id.trip_location);
        //_LocationField.setText("Longitude: " + _Trip.getLongitude() + ", Latitude: " + _Trip.getLatitude());

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { //when clicky, touchy, doey...
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_PHOTO) {
            updatePhotoView();
        }
    }

    private void updateDate() {
        _DateButton.setText(_Trip.getDate().toString());
    }

    private void updateLocation() {
        _LocationField.setText("Latitude: " + _Trip.getLatitude() + ", Longitude: " + _Trip.getLongitude());
    }

    private void updatePhotoView() {
        if (_PhotoFile == null || !_PhotoFile.exists()) {
            _PhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    _PhotoFile.getPath(), getActivity());
            _PhotoView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) { //creates options menu
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.trip, menu);

        //button to get location manually
        //MenuItem searchItem = menu.findItem(R.id.menu_item_locate);
        //searchItem.setEnabled(gClient.isConnected());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //options menu events
        switch (item.getItemId()) {
            case R.id.menu_item_save:
                closeFragment();
                return true;
            case R.id.menu_item_cancel:
                TripDatabase.get(getActivity()).deleteTrip(_Trip);
                getActivity().onBackPressed();
                return true;
            /*case R.id.menu_item_locate: //Button to manually get current location
                findLocation();
                //updateLocation();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void closeFragment() {
        TripDatabase.get(getActivity()).updateTrip(_Trip);
        getActivity().onBackPressed();
    }


    private void findLocation() {
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);
        Log.i(TAG, "Request: " + request);

        _Context = getActivity().getBaseContext();
        if (ActivityCompat.checkSelfPermission(_Context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(_Context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //do nothing
                return;
        }

        LocationServices.FusedLocationApi
                .requestLocationUpdates(gClient, request, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Log.i(TAG, "Got a fix: " + location);

                        //save current location to database
                        _Trip.setLatitude(Double.toString(location.getLatitude()));
                        _Trip.setLongitude(Double.toString(location.getLongitude()));


                        new SearchTask().execute(location);
                        Log.i(TAG, "findLocation() Latitude in DB: " + _Trip.getLatitude());
                        Log.i(TAG, "findLocation() Latitude in DB: " + _Trip.getLongitude());
                        updateLocation();
                    }
                });
    }


    private class SearchTask extends AsyncTask<Location,Void,Void> {
        private Location mLocation;

        @Override
        protected Void doInBackground(Location... params) {
            mLocation = params[0];
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            gCurrentLocation = mLocation;
        }
    }
}
