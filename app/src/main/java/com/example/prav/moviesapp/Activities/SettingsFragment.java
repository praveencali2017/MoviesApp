package com.example.prav.moviesapp.Activities;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.prav.moviesapp.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }
}
