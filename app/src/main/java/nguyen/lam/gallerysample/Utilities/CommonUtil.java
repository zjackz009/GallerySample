package nguyen.lam.gallerysample.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import android.util.Log;

/**
 * Copyright © 2016 TMA Solutions. All rights reserved
 */

public class CommonUtil {

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
            for (int i = 0; i < bytes.length; i++) {
                sb.append(String.format("%02X", bytes[i]));
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
        catch(Exception ex){}
    }

}
