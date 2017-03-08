package nguyen.lam.gallerysample.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import nguyen.lam.gallerysample.Activity.GallerySampleActivity;
import nguyen.lam.gallerysample.Fragments.GallerySampleFragment;
import nguyen.lam.gallerysample.Interfaces.GalleryListener;

/**
 * Copyright © 2016 TMA Solutions. All rights reserved
 */

public class GalleryPagerAdapter extends FragmentPagerAdapter {

    int maxTab;
    Context context;
    GalleryListener galleryListener;

    public GalleryPagerAdapter(FragmentManager fm, int numTab, Context context, GalleryListener listener) {
        super(fm);
        this.maxTab = numTab;
        this.context = context;
        this.galleryListener = listener;
    }

    @Override
    public Fragment getItem(int position) {
        return new GallerySampleFragment(position,context,galleryListener);
    }

    @Override
    public int getCount() {
        return maxTab;
    }
}
