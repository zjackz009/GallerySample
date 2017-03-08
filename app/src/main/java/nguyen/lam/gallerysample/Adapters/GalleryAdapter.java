package nguyen.lam.gallerysample.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import nguyen.lam.gallerysample.Interfaces.GalleryListener;
import nguyen.lam.gallerysample.R;
import nguyen.lam.gallerysample.Services.ImageDownloadService;

/**
 * Copyright © 2016 TMA Solutions. All rights reserved
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryHolder> {

    private ArrayList<String> listUrl = new ArrayList<>();
    private ImageDownloadService imageDownloadService;
    private Context context;
    private GalleryListener galleryListener;

    public GalleryAdapter(ArrayList<String> listUrl, Context context, GalleryListener listener){
        this.listUrl = listUrl;
        this.context = context;
        imageDownloadService = new ImageDownloadService(context);
        this.galleryListener = listener;
    }

    @Override
    public GalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layot_gallery_item, parent, false);
        return new GalleryHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryHolder holder, int position) {
        if(null !=listUrl && listUrl.size()>0){
            imageDownloadService.DisplayImage(listUrl.get(position),holder.imageItem);
            final int lindId = position;
            holder.imageItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    galleryListener.onImageClick(listUrl.get(lindId));;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(null != listUrl && listUrl.size()>0){
            return listUrl.size();
        }
        return 0;
    }
}
