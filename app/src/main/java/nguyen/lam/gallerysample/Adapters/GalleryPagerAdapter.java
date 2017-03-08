package nguyen.lam.gallerysample.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import nguyen.lam.gallerysample.Activity.GallerySampleActivity;
import nguyen.lam.gallerysample.Fragments.GallerySampleFragment;
import nguyen.lam.gallerysample.Interfaces.GalleryListener;
public class GalleryPagerAdapter extends FragmentPagerAdapter {

    private int maxTab;
    private Context context;
    private GalleryListener galleryListener;

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
