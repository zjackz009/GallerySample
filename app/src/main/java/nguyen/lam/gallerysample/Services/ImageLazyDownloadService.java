package nguyen.lam.gallerysample.Services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.util.Log;

import nguyen.lam.gallerysample.Interfaces.ImageDownLoadProgressListener;
import nguyen.lam.gallerysample.R;
import nguyen.lam.gallerysample.Utilities.CommonUtil;

public class ImageLazyDownloadService {

    private static final String TAG = ImageLazyDownloadService.class.getSimpleName();

    private static final int TIME_OUT = 30*1000;

    private ImageMemoryCache memoryCache=new ImageMemoryCache();
    private ImageFileCache fileCache;
    private Map<ImageView, String> imageViews=Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    private ExecutorService executorService;
    private Handler handler =new Handler();//handler to display images in UI thread

    public ImageLazyDownloadService(Context context){
        fileCache=new ImageFileCache(context);
        executorService=Executors.newFixedThreadPool(5);
    }

    private final int stub_id= R.mipmap.ic_launcher;
    public void DisplayImage(String url, ImageView imageView,ImageDownLoadProgressListener listener)
    {
        imageViews.put(imageView, url);
        Bitmap bitmap=memoryCache.get(url);
        if(bitmap!=null)
            imageView.setImageBitmap(bitmap);
        else
        {
            queuePhoto(url, imageView,listener);
            imageView.setImageResource(stub_id);
        }
    }

    private void queuePhoto(String url, ImageView imageView,ImageDownLoadProgressListener listener)
    {
        PhotoToLoad p=new PhotoToLoad(url, imageView,listener);
        executorService.submit(new PhotosLoader(p));
    }

    private Bitmap getBitmap(String url, ImageDownLoadProgressListener listener)
    {
        File f=fileCache.getFile(url);

        //from SD cache
        Bitmap b = CommonUtil.decodeFile(f);
        if(b!=null)
            return b;

        //from web
        try {
            Bitmap bitmap;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            conn.setConnectTimeout(TIME_OUT);
            conn.setReadTimeout(TIME_OUT);
            conn.setInstanceFollowRedirects(true);
            InputStream is=conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            CommonUtil.CopyStream(is, os);
            os.close();
            conn.disconnect();
            bitmap = CommonUtil.decodeFile(f);
//            bitmap = (new ImageAsyncTaskDownloadService(listener,f).execute(url)).get();
            return bitmap;
        } catch (Throwable ex){
            ex.printStackTrace();
            if(ex instanceof OutOfMemoryError)
                memoryCache.clear();
            return null;
        }
    }



    //Task for the queue
    private class PhotoToLoad
    {
        public String url;
        public ImageView imageView;
        public ImageDownLoadProgressListener progressListener;
        public PhotoToLoad(String u, ImageView i, ImageDownLoadProgressListener listener){
            url=u;
            imageView=i;
            progressListener = listener;
        }
    }

    private class PhotosLoader implements Runnable {
        PhotoToLoad photoToLoad;
        PhotosLoader(PhotoToLoad photoToLoad){
            this.photoToLoad=photoToLoad;
        }

        @Override
        public void run() {
            try{
                if(imageViewReused(photoToLoad))
                    return;
                Bitmap bmp=getBitmap(photoToLoad.url,photoToLoad.progressListener);
                memoryCache.put(photoToLoad.url, bmp);
                if(imageViewReused(photoToLoad))
                    return;
                BitmapDisplayer bd=new BitmapDisplayer(bmp, photoToLoad);
                handler.post(bd);
            }catch(Throwable th){
                th.printStackTrace();
            }
        }
    }

    private boolean imageViewReused(PhotoToLoad photoToLoad){
        String tag=imageViews.get(photoToLoad.imageView);
        if(tag==null || !tag.equals(photoToLoad.url))
            return true;
        return false;
    }

    //Used to display bitmap in the UI thread
    private class BitmapDisplayer implements Runnable
    {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;
        public BitmapDisplayer(Bitmap b, PhotoToLoad p){bitmap=b;photoToLoad=p;}
        public void run()
        {
            if(imageViewReused(photoToLoad))
                return;
            if(bitmap!=null)
                photoToLoad.imageView.setImageBitmap(bitmap);
            else
                photoToLoad.imageView.setImageResource(stub_id);
        }
    }

    public void clearCache() {
        memoryCache.clear();
        fileCache.clear();
    }
}
