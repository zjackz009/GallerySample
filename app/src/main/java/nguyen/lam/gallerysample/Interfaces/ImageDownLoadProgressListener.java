package nguyen.lam.gallerysample.Interfaces;

/**
 * Created by JackSilver on 3/8/2017.
 */

public interface ImageDownLoadProgressListener {
    void onProgressUpdate(String percent);
    void onProgressDone();
}
