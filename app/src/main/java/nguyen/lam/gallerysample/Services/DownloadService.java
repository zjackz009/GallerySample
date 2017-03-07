package nguyen.lam.gallerysample.Services;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import nguyen.lam.gallerysample.Utilities.CommonUtil;

/**
 * Copyright © 2016 TMA Solutions. All rights reserved
 */

public class DownloadService {

    private static DownloadService DOWNLOAD_INSTANCE = null;
    private DownloadManager downloadManager;

    public static DownloadService getDownloadInstance() {
        if(null == DOWNLOAD_INSTANCE){
            DOWNLOAD_INSTANCE = new DownloadService();
        }
        return DOWNLOAD_INSTANCE;
    }

    public void initDownloadManager(Context context){
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public long downloadFile(String url) {

        if (null != downloadManager &&!TextUtils.isEmpty(url)) {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);

            return downloadManager.enqueue(request);
        }

        return -1;
    }

    public int getCurrentDownloadStatus(long lastDownloadId) {
        Cursor c = downloadManager.query(new DownloadManager.Query().setFilterById(lastDownloadId));
        if (c != null) {
            c.moveToFirst();
        } else {
            return DownloadManager.STATUS_FAILED;
        }
        int statusDownload = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
        c.close();
        return statusDownload;
    }

    public String getFileName(long lastDownloadId) {
        Cursor c = downloadManager.query(new DownloadManager.Query().setFilterById(lastDownloadId));
        if (c != null) {
            c.moveToFirst();
        } else {
            return null;
        }
        String fileName = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
        c.close();
        return fileName;
    }

}
