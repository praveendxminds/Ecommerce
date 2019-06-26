package com.app.ecommerce.Home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.ecommerce.R;

public class SwipeFragment extends Fragment {
    public static final String[] IMAGE_NAME = {"https://www.gstatic.com/webp/gallery3/1.png",
            "https://www.gstatic.com/webp/gallery3/2.png",
            "https://www.gstatic.com/webp/gallery3/4.png"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View swipeView = inflater.inflate(R.layout.slide_prd_details, container, false);
        ImageView imageView = (ImageView) swipeView.findViewById(R.id.imageView);
        Bundle bundle = getArguments();
        int position = bundle.getInt("position");
        String imageFileName = IMAGE_NAME[position];
        int imgResId = getResources().getIdentifier(imageFileName, "drawable", "com.javapapers.android.swipeimageslider");
        imageView.setImageResource(imgResId);
        return swipeView;
    }

    static SwipeFragment newInstance(int position) {
        SwipeFragment swipeFragment = new SwipeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        swipeFragment.setArguments(bundle);
        return swipeFragment;
    }
}