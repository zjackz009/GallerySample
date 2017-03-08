package nguyen.lam.gallerysample.Services;

import android.content.Context;

import java.io.File;

import nguyen.lam.gallerysample.Utilities.CommonUtil;

/**
 * Copyright © 2016 TMA Solutions. All rights reserved
 */

class ImageFileCache {
    private File cacheDir;

    ImageFileCache(Context context){
        //Find the dir to save cached images
        String sdState = android.os.Environment.getExternalStorageState();
        if (sdState.equals(android.os.Environment.MEDIA_MOUNTED)) {
            File sdDir = android.os.Environment.getExternalStorageDirectory();
            cacheDir = new File(sdDir,"data/cache");
        } else {
            cacheDir = context.getCacheDir();
        }

        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
    }

    File getFile(String url){
        String filename= CommonUtil.md5Hash(url);
        File f = new File(cacheDir, filename);
        return f;

    }

    void clear(){
        File[] files=cacheDir.listFiles();
        if(files==null)
            return;
        for(File f:files)
            f.delete();
    }
}
