package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private ArrayList<SongRaw> songs = new ArrayList<SongRaw>();
    SongAdapter songAdapter;
    MediaPlayer mediaPlayer;
    Handler myHandler = new Handler();
    Handler myHandler1 = new Handler();
    Button stop;
    Button pause;
    Button resume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.d("Prog","POCELOOOOOOOO");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        SongAdapter songAdapter = new SongAdapter(this,songs);
        recyclerView.setAdapter(songAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        songAdapter.setOnItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final ImageButton b, View view, final SongInfo obj, int position) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.d("Prog","Startovana Nit");
                            Log.d("Prog","Song info:");
                            Log.d("Prog",obj.getArtistname());
                            if(mediaPlayer != null){
                                mediaPlayer.stop();
                                mediaPlayer.reset();
                                mediaPlayer.release();
                                mediaPlayer = null;
                            }
                            mediaPlayer = new MediaPlayer();
                            mediaPlayer.setDataSource(obj.getSongUrl());
                            mediaPlayer.prepareAsync();
                            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    Log.d("Prog","Startovan plejer");

                                    mp.start();
                                    //seekBar.setProgress(0);
                                    //seekBar.setMax(mediaPlayer.getDuration());
                                   // Log.d("Prog", "run: " + mediaPlayer.getDuration());
                                }
                            });
                            //b.setText("Stop");



                        }catch (Exception e){}
                    }

                };
                myHandler.postDelayed(runnable,100);
            }
        },new SongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final ImageButton b, View view, final SongInfo obj, int position) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.d("Prog","Startovana Nit");
                            Log.d("Prog","Song info:");
                            Log.d("Prog",obj.getArtistname());
                            if(mediaPlayer != null){
                                mediaPlayer.stop();
                                mediaPlayer.reset();
                                mediaPlayer.release();
                                mediaPlayer = null;
                            }
                            mediaPlayer = new MediaPlayer();
                            mediaPlayer.setDataSource(obj.getSongUrl());
                            mediaPlayer.prepareAsync();
                            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    Log.d("Prog","Startovan plejer");

                                    mp.start();
                                    //seekBar.setProgress(0);
                                    //seekBar.setMax(mediaPlayer.getDuration());
                                    // Log.d("Prog", "run: " + mediaPlayer.getDuration());
                                }
                            });
                            //b.setText("Stop");



                        }catch (Exception e){}
                    }

                };
                myHandler1.postDelayed(runnable,100);
            }
        } );
        checkUserPermission();
        stop = (Button) findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        });
        resume = (Button) findViewById(R.id.resume);
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
            }
        });
        pause = (Button) findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });

    }

    private void checkUserPermission(){
        if(Build.VERSION.SDK_INT>=23){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123);
                return;
            }
        }
        loadSongs();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       // Log.d("Prog","POCELOOOOOOOO11111");

        switch (requestCode){
            case 123:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    loadSongs();
                }else{
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    checkUserPermission();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        }

    }

    private void loadSongs(){
    Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    String selection = MediaStore.Audio.Media.IS_MUSIC+"!=0";
    Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if(cursor != null){
        if(cursor.moveToFirst()){
            Log.d("Prog","Radi loadSongs");
            do{
                String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                Log.d("Prog","Song Name: "+name);
                Log.d("Prog","Song Artist: "+artist);
                Log.d("Prog","Song url: "+url);
                SongInfo s = new SongInfo(name,artist,url);
                SongInfo s1 = null;
                //songs.add(s);
                if(cursor.moveToNext()){
                    String name1 = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    String artist1 = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                    String url1 = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    s1 = new SongInfo(name1,artist1,url1);
                }
                songs.add(new SongRaw(s,s1));

            }while (cursor.moveToNext());
        }
        else{
            Log.i(null,"Ne Radi");
        }

        cursor.close();
        songAdapter = new SongAdapter(MainActivity.this,songs);

    }
}



}