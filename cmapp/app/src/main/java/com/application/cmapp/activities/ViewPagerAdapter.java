package com.application.cmapp.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.application.cmapp.activities.fragments.AppFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        AppFragment testFrag = new AppFragment();
        position = position + 1;
        Bundle bundle = new Bundle();
        bundle.putInt("Message", position);
        testFrag.setArguments(bundle);
        return testFrag;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int  position){
        position = position + 1;

        switch(position){
            case 1:
                return "Degrees";
            case 2:
                return "Humidity";
            case 3:
                return "CO2";
            case 4:
                return "Light";
            default:
                break;
        }
        return null;
    }
}
