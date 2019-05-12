package com.application.cmapp.activities.fragments;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.application.cmapp.R;
import com.application.cmapp.activities.MainActivity;
import com.application.cmapp.model.Reading;
import com.application.cmapp.viewmodel.ReadingViewModel;

public class SettingsFragment extends Fragment {

    Button setSettings;
    CheckBox imperial;

    public SettingsFragment(){
        //No-args constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstance){

        return inflater.inflate(R.layout.settings_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance)
    {
        setSettings = view.findViewById(R.id.setButton);
        imperial = view.findViewById(R.id.imperial);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        imperial.setChecked(prefs.getBoolean("imperial", false));

        setSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("imperial", imperial.isChecked()).apply();
                editor.commit();

                Toast toast = Toast.makeText(getActivity(), "Settings updated!", Toast.LENGTH_SHORT);
                toast.show();

                AppFragment.getInstance().refreshTemp();

                ((MainActivity) getActivity()).unFadeBackground();
                getFragmentManager().beginTransaction().remove(SettingsFragment.this).commit();


            }
        });
    }
}
