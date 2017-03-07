package nguyen.lam.gallerysample;

import android.app.Application;
import android.app.DownloadManager;

import nguyen.lam.gallerysample.Services.DownloadService;

/**
 * Created by JackSilver on 3/6/2017.
 */

public class GallerySampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DownloadService.getDownloadInstance().initDownloadManager(getApplicationContext());
    }
}
