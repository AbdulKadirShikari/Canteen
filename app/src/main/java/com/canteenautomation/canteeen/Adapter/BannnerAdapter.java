package com.canteenautomation.canteeen.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.canteenautomation.canteeen.Model.BannnerModel;
import com.canteenautomation.canteeen.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BannnerAdapter extends PagerAdapter {

    private ArrayList<BannnerModel> images;
    private LayoutInflater inflater;
    private Context context;

    public BannnerAdapter(Context context, ArrayList<BannnerModel> images) {
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

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.slide, view, false);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.imageSlide);

        Picasso.with(context).load( images.get(position).getBannerImage()).into(myImage);


        //S.E("Aniruddha data"+Const.URL.Banner_Image + images.get(position).getBannerImage());
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}