package com.mobile.lab_23_muhammad_adil_mehmood_contextmenu2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class FriendsListAdapter extends ArrayAdapter<String> {
    private String[] friendNames;
    private Integer[] friendImages;
    private Activity context;

    public FriendsListAdapter(Activity context, String[] friendNames, Integer[] friendImages) {
        super(context, R.layout.layout_item, friendNames);
        this.context = context;
        this.friendNames = friendNames;
        this.friendImages = friendImages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView==null)
            row = inflater.inflate(R.layout.layout_item, null, true);
        TextView tvFriendName = (TextView) row.findViewById(R.id.tvFriendName);
        ImageView ivFriend = (ImageView) row.findViewById(R.id.ivFriendImage);
        CardView cvFriend = (CardView) row.findViewById(R.id.cvFriend);
        tvFriendName.setText(friendNames[position]);
        ivFriend.setImageResource(friendImages[position]);

        return  row;
    }

}
