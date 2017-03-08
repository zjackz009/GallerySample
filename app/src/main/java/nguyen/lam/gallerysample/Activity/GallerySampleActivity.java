package nguyen.lam.gallerysample.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import nguyen.lam.gallerysample.Adapters.GalleryPagerAdapter;
import nguyen.lam.gallerysample.Interfaces.GalleryListener;
import nguyen.lam.gallerysample.R;
import nguyen.lam.gallerysample.Utilities.Constant;

public class GallerySampleActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int TAB_POSITION_0 = 0;
    public static final int TAB_POSITION_1 = 1;
    public static final int TAB_POSITION_2 = 2;

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x01;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_gallery_sample);
        setupAppBar();
        if (checkStoragePermission()) {
            setupTabBar();
            setupViewPager();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void setupAppBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView titleView = (TextView) toolbar.findViewById(R.id.toolbar_title);
        titleView.setOnClickListener(this);
        setSupportActionBar(toolbar);
    }

    private void setupTabBar() {
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_name_1)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_name_2)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_name_3)));

        tabLayout.addOnTabSelectedListener(tabSelectedListener);
    }

    private void setupViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        GalleryPagerAdapter pagerAdapter = new GalleryPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), this,galleryListener);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.toolbar_title:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    setupTabBar();
                    setupViewPager();
                } else {
                    onBackPressed();
                }
            }
            break;
            default:
                break;
        }
    }

    private TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    GalleryListener galleryListener = new GalleryListener() {
        @Override
        public void onImageClick(String url) {
            Intent intent = new Intent(GallerySampleActivity.this,GalleryDetailActivity.class);
            intent.putExtra(Constant.INTENT_URL_KEY,url);
            startActivity(intent);

        }
    };

    private boolean checkStoragePermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            int storage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (storage != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_ID_MULTIPLE_PERMISSIONS);
                return false;
            }
        }
        return true;
    }
}
