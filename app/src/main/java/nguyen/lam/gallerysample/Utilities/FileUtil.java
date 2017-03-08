package nguyen.lam.gallerysample.Utilities;

import android.content.Context;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtil {

    private static final String TAG = FileUtil.class.getSimpleName();


    public static String readFileFromAsset(Context context,String fileName){
        StringBuilder buf =new StringBuilder();
        try {
            InputStream json=context.getAssets().open(fileName);
            BufferedReader in=
                    new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String str;

            while ((str=in.readLine()) != null) {
                buf.append(str);
            }
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.e(TAG,buf.toString());
        return buf.toString();
    }
}
