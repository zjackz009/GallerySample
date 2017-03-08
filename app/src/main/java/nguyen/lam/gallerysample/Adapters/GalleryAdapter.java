package nguyen.lam.gallerysample.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import nguyen.lam.gallerysample.Interfaces.GalleryListener;
import nguyen.lam.gallerysample.Interfaces.ImageDownLoadProgressListener;
import nguyen.lam.gallerysample.R;
import nguyen.lam.gallerysample.Services.ImageAsyncTaskDownloadService;
import nguyen.lam.gallerysample.Services.ImageLazyDownloadService;

/**
 * Copyright © 2016 TMA Solutions. All rights reserved
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryHolder> {

    private ArrayList<String> listUrl = new ArrayList<>();
    private ImageLazyDownloadService imageLazyDownloadService;
    private Context context;
    private GalleryListener galleryListener;

    public GalleryAdapter(ArrayList<String> listUrl, Context context, GalleryListener listener){
        this.listUrl = listUrl;
        this.context = context;
        imageLazyDownloadService = new ImageLazyDownloadService(context);
        this.galleryListener = listener;
    }

    @Override
    public GalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layot_gallery_item, parent, false);
        return new GalleryHolder(view);
    }

    @Override
    public void onBindViewHolder(final GalleryHolder holder, int position) {
        if(null !=listUrl && listUrl.size()>0){
            imageLazyDownloadService.DisplayImage(listUrl.get(position), holder.imageItem, new ImageDownLoadProgressListener() {
                @Override
                public void onProgressUpdate(String percent) {
                    holder.tvItem.setVisibility(View.VISIBLE);
                    holder.tvItem.setText(percent);
                }

                @Override
                public void onProgressDone() {
                    holder.tvItem.setVisibility(View.GONE);
                }
            });
            /*try {
                Bitmap bitmap =  (new ImageAsyncTaskDownloadService(holder.imageItem,holder.tvItem).execute(listUrl.get(position))).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }*/

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
