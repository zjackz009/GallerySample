package nguyen.lam.gallerysample.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import nguyen.lam.gallerysample.Activity.GallerySampleActivity;
import nguyen.lam.gallerysample.Adapters.GalleryAdapter;
import nguyen.lam.gallerysample.Interfaces.GalleryListener;
import nguyen.lam.gallerysample.R;
import nguyen.lam.gallerysample.Utilities.CommonUtil;
import nguyen.lam.gallerysample.Utilities.Constant;
import nguyen.lam.gallerysample.Utilities.FileUtil;
import nguyen.lam.gallerysample.Utilities.GridSpacingItemDecoration;

/**
 * Copyright © 2016 TMA Solutions. All rights reserved
 */

public class GallerySampleFragment extends Fragment {

    private ArrayList<String> listUrl = new ArrayList<>();
    private GalleryListener galleryListener;

    private int spanCount = 2; // 3 columns
    private int spacing = 10; // 50px
    private boolean includeEdge = false;

    public GallerySampleFragment() {
    }

    public GallerySampleFragment(int position, Context context, GalleryListener listener) {
        String fileName = "";
        switch (position) {
            case GallerySampleActivity.TAB_POSITION_0:
                fileName = Constant.JSON_FILE_IMAGES_0;
                break;
            case GallerySampleActivity.TAB_POSITION_1:
                fileName = Constant.JSON_FILE_IMAGES_1;
                break;
            case GallerySampleActivity.TAB_POSITION_2:
                fileName = Constant.JSON_FILE_IMAGES_2;
                break;
            default:
                break;
        }
        if(!TextUtils.isEmpty(fileName)){
            listUrl = CommonUtil.parseJson(FileUtil.readFileFromAsset(context,fileName));
        }
        this.galleryListener = listener;
    }

    private static final int COLUMN = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery_sample, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_images);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), COLUMN);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        GalleryAdapter adapter = new GalleryAdapter(listUrl,getActivity(),galleryListener);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
    }
}
