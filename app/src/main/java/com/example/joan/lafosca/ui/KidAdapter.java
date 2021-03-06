package com.example.joan.lafosca.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.joan.lafosca.R;
import com.example.joan.lafosca.model.ModelKid;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Joan on 18/3/15.
 */
public class KidAdapter extends ArrayAdapter<ModelKid>{

        Context mContext;
        int layoutResourceId;
        ArrayList<ModelKid> data = null;

        public KidAdapter(Context mContext, int layoutResourceId, ArrayList<ModelKid> data) {

            super(mContext, layoutResourceId, data);

            this.layoutResourceId = layoutResourceId;
            this.mContext = mContext;
            this.data = data;
        }

        public ModelKid getData( int position ) { return data.get(position); }
        public void setData( ArrayList<ModelKid> kidsList ) { this.data = kidsList; }

    @Override
    public ModelKid getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView==null){

            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, null);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.kidNameId);
            holder.age = (TextView) convertView.findViewById(R.id.kidAgeId);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.pb = (ProgressBar) convertView.findViewById(R.id.imgLoader);
            holder.pb.setVisibility(View.GONE);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ModelKid kid = data.get(position);

        holder.name.setText(kid.getName());

        holder.age.setText(kid.getAge().toString());

        Picasso.with(mContext).load(kid.getUrl()).resize(50, 50).placeholder(R.anim.progress_animation).into(holder.img);
//                new ImageLoadedCallback(holder.pb) {
//
//            @Override
//            public void onSuccess() {
//                if (this.progressBar != null) {
//                    this.progressBar.setVisibility(View.GONE);
//                }
//            }
//        });

        return convertView;
    }
    private class ImageLoadedCallback implements Callback {
        ProgressBar progressBar;

        public ImageLoadedCallback(ProgressBar progBar){
            progressBar = progBar;
        }
        @Override
        public void onSuccess() {
        }
        @Override
        public void onError() {

        }
    }
    private static class ViewHolder {
        public TextView name;
        public TextView age;
        public ImageView img;
        public ProgressBar pb;
    }

}
