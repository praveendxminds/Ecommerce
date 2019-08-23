package com.nisarga.nisargaveggiez.Home;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.nisarga.nisargaveggiez.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProductDetailImageSliderAdapter extends PagerAdapter {

    private ArrayList<String> images;
    private LayoutInflater inflater;
    private Context context;

    public ProductDetailImageSliderAdapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup view, int position) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View myImageLayout = inflater.inflate(R.layout.slide_home_2, view, false);
        ImageView myImage = (ImageView) myImageLayout.findViewById(R.id.image);



        final String fileUrl = images.get(position);
        Glide.with(context).load(fileUrl).error(R.drawable.deep).into(myImage);


        final Dialog settingsDialog = new Dialog(context);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.setContentView(inflater.inflate(R.layout.image_layout, view, false));

        PhotoView photoView = (PhotoView) settingsDialog.findViewById(R.id.photo_view);
        ImageView ivClose = (ImageView) settingsDialog.findViewById(R.id.ivClose);

        Glide.with(context).load(fileUrl).error(R.drawable.deep).into(photoView);


        myImage.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view)
            {
                settingsDialog.show();
            }
        });


        ivClose.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view)
            {
                settingsDialog.hide();
            }
        });



        view.addView(myImageLayout, 0);

        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}