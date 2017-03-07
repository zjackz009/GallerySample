package nguyen.lam.gallerysample.Activity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import nguyen.lam.gallerysample.R;
import nguyen.lam.gallerysample.Services.DownloadService;
import nguyen.lam.gallerysample.Utilities.CommonUtil;
import nguyen.lam.gallerysample.Utilities.Constant;
import nguyen.lam.gallerysample.Utilities.FileUtil;

public class GallerySampleActivity extends AppCompatActivity implements View.OnClickListener {

    private long downloadId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_gallery_sample);
        setupAppBar();
        FileUtil.listFiles(this,"json_file");
        if(CommonUtil.checkConnect(this)) {
            downloadId = DownloadService.getDownloadInstance().downloadFile(Constant.URL_JSON_FILE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(downloadBroadcastReceiver,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    protected void onPause() {
        unregisterReceiver(downloadBroadcastReceiver);
        super.onPause();
    }

    private void setupAppBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView titleView = (TextView) toolbar.findViewById(R.id.toolbar_title);
        titleView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.toolbar_title:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    BroadcastReceiver downloadBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(null != intent){
                long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if(downloadId == referenceId){
                    if(DownloadService.getDownloadInstance().getCurrentDownloadStatus(downloadId) == DownloadManager.STATUS_SUCCESSFUL){
                        Log.e("TAG","STATUS_SUCCESSFUL");
                    }
                }
            }else {

            }
        }
    };
}
