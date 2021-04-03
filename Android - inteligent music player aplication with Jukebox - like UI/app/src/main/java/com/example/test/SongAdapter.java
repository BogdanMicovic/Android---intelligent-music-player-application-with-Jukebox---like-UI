package com.example.test;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


 

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {

    private ArrayList<SongRaw> songs = new ArrayList<SongRaw>();
    private Context context;
    private OnItemClickListener myOnItemClickListener1;
    private OnItemClickListener myOnItemClickListener2;

    public SongAdapter(Context context, ArrayList<SongRaw> songs) {
        this.context = context;
        this.songs = songs;
    }

    public interface OnItemClickListener {
        void onItemClick(ImageButton b , View view, SongInfo obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener,final OnItemClickListener m2) {
        this.myOnItemClickListener1 = mItemClickListener;
        this.myOnItemClickListener2 = m2;
    }


    @Override
    public SongHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View myView = LayoutInflater.from(context).inflate(R.layout.song_layout,viewGroup,false);
        return new SongHolder(myView);
    }

    @Override
    public void onBindViewHolder(final SongHolder songHolder, final int i) {
        final SongInfo s1 = songs.get(i).getSong1();
        final SongInfo s2 = songs.get(i).getSong2();
        songHolder.song1.setText(songs.get(i).getSong1().getSongname());
        songHolder.song2.setText(songs.get(i).getSong2().getSongname());
        songHolder.imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myOnItemClickListener1 != null) {
                    myOnItemClickListener1.onItemClick(songHolder.imageButton1,v, s1, i);
                }
            }
        });
        songHolder.imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myOnItemClickListener2 != null) {
                    myOnItemClickListener2.onItemClick(songHolder.imageButton2,v, s2, i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class SongHolder extends RecyclerView.ViewHolder {
        TextView song1,song2;
        ImageButton imageButton1,imageButton2;
        public SongHolder(View itemView) {
            super(itemView);
            song1 = (TextView) itemView.findViewById(R.id.artist1);
            song2 = (TextView) itemView.findViewById(R.id.artist2);
            imageButton1 = (ImageButton) itemView.findViewById(R.id.imageButton5);
            imageButton2 = (ImageButton) itemView.findViewById(R.id.imageButton6);
        }
    }
}
