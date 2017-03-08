package nguyen.lam.gallerysample.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import nguyen.lam.gallerysample.R;
import nguyen.lam.gallerysample.Services.ImageDownloadService;
import nguyen.lam.gallerysample.Utilities.Constant;

public class GalleryDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_gallery_detail);
        if (getIntent().hasExtra(Constant.INTENT_URL_KEY)) {
            String url = getIntent().getStringExtra(Constant.INTENT_URL_KEY);
            if (!TextUtils.isEmpty(url)) {
                initView(url);
            } else {
                onBackPressed();
            }
        } else {
            onBackPressed();
        }

    }

    private void initView(String url) {
        ImageView btnBack = (ImageView) findViewById(R.id.btn_detail_back);
        btnBack.setOnClickListener(this);

        ImageView imageDetail = (ImageView) findViewById(R.id.img_detail);
        ImageDownloadService imageDownloadService = new ImageDownloadService(this);
        imageDownloadService.DisplayImage(url, imageDetail);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_detail_back:
                onBackPressed();
                break;
            default:
                break;
        }
    }
}
