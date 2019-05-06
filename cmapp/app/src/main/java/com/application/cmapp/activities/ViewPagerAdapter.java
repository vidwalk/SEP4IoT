package com.application.cmapp.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        TestFragment testFrag = new TestFragment();
        position = position + 1;
        Bundle bundle = new Bundle();
        bundle.putString("Message", "Fragment: " + position);
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
        return "Fragment" + position;
    }
}
