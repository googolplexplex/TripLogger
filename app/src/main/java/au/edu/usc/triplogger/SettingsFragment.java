//author: p.schwake
//edited: 2016-10-28 psc
//file: SettingsFragment.java
//about: does stuff and junk

package au.edu.usc.triplogger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.UUID;

public class SettingsFragment extends Fragment {

    private static final String TAG = "SettingsFragment";
    private static final String ARG_SETTINGS_ID = "settings_id";


    private EditText _NameField;
    private EditText _IdField;
    private EditText _EmailField;
    private EditText _GenderField;
    private EditText _CommentField;
    private Settings _Settings;

    //----------------------------------------------------------------------------------------------------------------

    public static SettingsFragment newInstance(UUID settingsID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_SETTINGS_ID, settingsID);

        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onPause() {
        super.onPause();
        SettingsDatabase.get(getActivity()).updateSettings(_Settings);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        _Settings = SettingsDatabase.get(getActivity()).getSettings();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        Log.i(TAG, "settingsFragment Name in DB: " + _Settings.getName());
        Log.i(TAG, "settingsFragment ID in DB: " + _Settings.getId());
        Log.i(TAG, "settingsFragment Email in DB: " + _Settings.getEmail());
        Log.i(TAG, "settingsFragment Gender in DB: " + _Settings.getGender());
        Log.i(TAG, "settingsFragment Comment in DB: " + _Settings.getComment());


        //name field

        _NameField = (EditText) v.findViewById(R.id.settings_name);
        _NameField.setText(_Settings.getName());
        _NameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _Settings.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //id field

        _IdField = (EditText) v.findViewById(R.id.settings_id);
        _IdField.setText(_Settings.getId());
        _IdField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _Settings.setId(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //email field

        _EmailField = (EditText) v.findViewById(R.id.settings_email);
        _EmailField.setText(_Settings.getEmail());
        _EmailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _Settings.setEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //gender field

        _GenderField = (EditText) v.findViewById(R.id.settings_gender);
        _GenderField.setText(_Settings.getGender());
        _GenderField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _Settings.setGender(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //comment field

        _CommentField = (EditText) v.findViewById(R.id.settings_comment);
        _CommentField.setText(_Settings.getComment());
        _CommentField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _Settings.setComment(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return v;
    }

    //options menu

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) { //creates options menu
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.settings, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //options menu events
        switch (item.getItemId()) {
            case R.id.menu_settings_save:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}