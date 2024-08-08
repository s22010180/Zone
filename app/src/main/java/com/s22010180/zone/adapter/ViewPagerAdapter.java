package com.s22010180.zone.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.s22010180.zone.frag.Add;
import com.s22010180.zone.frag.Home;
import com.s22010180.zone.frag.Info;
import com.s22010180.zone.frag.Notification;
import com.s22010180.zone.frag.Profile;
import com.s22010180.zone.frag.Search;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    int noOfTabs;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int noOfTabs) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.noOfTabs = noOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){

            default:
            case 0:
                return new Home();

            case 1:
                return new Search();

            case 2:
                return new Add();

            case 3:
                return new Notification();

            case 4:
                return new Info();

            case 5:
                return new Profile();


        }

    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}

