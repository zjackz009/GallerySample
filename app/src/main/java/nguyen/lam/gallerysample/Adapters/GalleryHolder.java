package nguyen.lam.gallerysample.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nguyen.lam.gallerysample.R;

class GalleryHolder extends RecyclerView.ViewHolder {

    ImageView imageItem;
    TextView tvItem;

    GalleryHolder(View itemView) {
        super(itemView);

        imageItem = (ImageView) itemView.findViewById(R.id.img_item);
        tvItem = (TextView) itemView.findViewById(R.id.tv_item);
    }
}
