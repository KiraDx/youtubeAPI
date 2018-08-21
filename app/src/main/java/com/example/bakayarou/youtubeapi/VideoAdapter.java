package com.example.bakayarou.youtubeapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Video> videoList;

    public VideoAdapter(Context context, int layout, List<Video> videoList) {
        this.context = context;
        this.layout = layout;
        this.videoList = videoList;
    }

    @Override
    public int getCount() {
        return videoList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        ImageView imgThumbnail;
        TextView txtTen;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);

            holder.txtTen = (TextView) convertView.findViewById(R.id.textviewTitle);
            holder.imgThumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Video video = videoList.get(position);
        holder.txtTen.setText(video.getTitle());
        Picasso.get().load(video.getThumbnail()).into(holder.imgThumbnail);
        return convertView;
    }
}
