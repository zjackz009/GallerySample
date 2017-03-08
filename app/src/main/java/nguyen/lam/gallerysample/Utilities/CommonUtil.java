package nguyen.lam.gallerysample.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import android.util.Log;


/**
 * Copyright © 2016 TMA Solutions. All rights reserved
 */

public class CommonUtil {

    private static final String TAG = CommonUtil.class.getSimpleName();

    public static boolean checkConnect(Context context) {

        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public static  String md5Hash(String s){
        try {
            final MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(s.getBytes());
            final byte[] bytes = digest.digest();
            final StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(String.format("%02X", aByte));
            }
            return sb.toString().toLowerCase();
        } catch (Exception exc) {
            return "";
        }
    }

    public static ArrayList<String> parseJson(String jsonString){
        ArrayList<String> list = new ArrayList<>();
        try {
            // jsonString is a string variable that holds the JSON
            JSONArray itemArray=new JSONArray(jsonString);
            if(itemArray.length()>0) {
                int length = itemArray.length();
                for (int i = 0; i < length; i++) {
                    list.add(itemArray.getString(i));
                }
            }
        } catch (JSONException e) {
            return null;
        }

        return list;
    }

    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    //decodes image and scales it to reduce memory consumption
    public static Bitmap decodeFile(File f){
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1=new FileInputStream(f);
            BitmapFactory.decodeStream(stream1,null,o);
            stream1.close();

            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE=512;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            FileInputStream stream2=new FileInputStream(f);
            Bitmap bitmap=BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();
            return bitmap;
        } catch (FileNotFoundException e) {
            Log.e(TAG,e.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
