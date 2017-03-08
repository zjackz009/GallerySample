package nguyen.lam.gallerysample.Services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import nguyen.lam.gallerysample.Interfaces.ImageDownLoadProgressListener;
import nguyen.lam.gallerysample.Utilities.CommonUtil;

/**
 * Created by JackSilver on 3/8/2017.
 */

public class ImageAsyncTaskDownloadService extends AsyncTask<String, String, Bitmap> {

    private final WeakReference<ImageDownLoadProgressListener> progressListenerWeakReference;
    private File file;

    public ImageAsyncTaskDownloadService(ImageDownLoadProgressListener listener, File file) {
        progressListenerWeakReference = new WeakReference<ImageDownLoadProgressListener>(listener);
        this.file = file;
    }

    @Override
    protected Bitmap doInBackground(String... params) {


        Bitmap bitmap = null;
        try {
            int increment;

            int response;
            InputStream in = null;
            URL url = new URL(params[0]);
            URLConnection conn = url.openConnection();
            if (!(conn instanceof HttpURLConnection))
                throw new IOException("Not an HTTP connection");
            try {
                HttpURLConnection httpConn = (HttpURLConnection) conn;
                httpConn.setInstanceFollowRedirects(true);
                httpConn.setRequestMethod("GET");
                httpConn.connect();

                response = httpConn.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK) {
                    in = httpConn.getInputStream();
                }
                int length = httpConn.getContentLength();

                byte[] data = new byte[length];
                increment = length / 100;
                OutputStream outStream = new FileOutputStream(file);
                int count = -1;
                int progress = 0;

                while ((count = in.read(data, 0, increment)) != -1) {
                    progress += count;
                    publishProgress("" + (int) ((progress * 100) / length));
                    outStream.write(data, 0, count);
                }
                in.close();
                outStream.close();
                bitmap = CommonUtil.decodeFile(file);
            } catch (Exception ex) {
                Log.d("Networking", ex.getLocalizedMessage());
                throw new IOException("Error connecting");
            }

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
        return bitmap;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        if (!TextUtils.isEmpty(values[0])) {
            final ImageDownLoadProgressListener listener = progressListenerWeakReference.get();
            if (listener != null) {
                listener.onProgressUpdate(values[0]);
            }
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        final ImageDownLoadProgressListener listener = progressListenerWeakReference.get();
        if (listener != null) {
            listener.onProgressDone();
        }
    }
}
