package nguyen.lam.gallerysample.Utilities;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by JackSilver on 3/6/2017.
 */

public class FileUtil {

    private static final String TAG = FileUtil.class.getSimpleName();

    public static void listFiles(Context context, String dirFrom) {
        Resources res = context.getResources(); //if you are in an activity
        AssetManager am = res.getAssets();
        List<String> fileList = new ArrayList();
        try {
            fileList = Arrays.asList(am.list(dirFrom));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (null != fileList && fileList.size()>0)
        {
            for ( int i = 0;i<fileList.size();i++)
            {
                Log.e(TAG,fileList.get(i));
            }
        }
    }
}
