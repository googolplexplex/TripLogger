//author: p.schwake
//edited: 2016-10-25 psc
//file: TripViewFragment.java
//about: View a single trip object

package au.edu.usc.triplogger;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.File;
import java.util.Locale;
import java.util.UUID;

public class TripViewFragment extends Fragment{

    private static final String ARG_TRIP_ID = "trip_id";
    private static final int REQUEST_PHOTO = 1;

    private static final String TAG = "TripViewFragment";

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
    private Button _LocationButton;

    //---------------------------------------------------------------------------

    public static TripViewFragment newInstance(UUID tripId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TRIP_ID, tripId);

        TripViewFragment fragment = new TripViewFragment();
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
    }

    @Override
    public void onPause() {
        super.onPause();
        TripDatabase.get(getActivity()).updateTrip(_Trip);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_trip_view, container, false);

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
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        _PhotoView = (ImageView) v.findViewById(R.id.trip_photo);
        updatePhotoView();


        //Location Button

        Log.i(TAG, "Latitude: " + _Trip.getLatitude());
        Log.i(TAG, "Longitude: " + _Trip.getLongitude());

        _LocationButton = (Button) v.findViewById(R.id.trip_location);
        _LocationButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){



        //source: https://developers.google.com/maps/documentation/android-api/intents
        // Create a Uri from an intent string. Use the result to create an Intent.
        //Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194"); //SanFrancisco
        //Uri gmmIntentUri = Uri.parse("geo:" + _Trip.getLatitude() + "," + _Trip.getLongitude() + "?=q" + _Trip.getLatitude() + "," + _Trip.getLongitude() + "(Label+Name)");

        Uri gmmIntentUri = Uri.parse("geo:" + _Trip.getLatitude() + "," + _Trip.getLongitude());

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

        // Attempt to start an activity that can handle the Intent
        startActivity(mapIntent);

            }
        });

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
        inflater.inflate(R.menu.trip_view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //options menu events
        switch (item.getItemId()) {
            //case R.id.menu_item_save:
              //  closeFragment();
               // return true;
            case R.id.menu_item_delete:
                TripDatabase.get(getActivity()).deleteTrip(_Trip);
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void closeFragment(){
        TripDatabase.get(getActivity()).updateTrip(_Trip);
        getActivity().onBackPressed();
    }
}
