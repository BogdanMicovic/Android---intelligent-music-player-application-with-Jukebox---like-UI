package com.example.test;

public class SongRaw {

    private SongInfo song1;
    private SongInfo song2;

    public SongRaw(SongInfo song1, SongInfo song2) {
        this.song1 = song1;
        this.song2 = song2;
    }

    public SongInfo getSong1() {
        return song1;
    }

    public SongInfo getSong2() {
        return song2;
    }

    public void setSong1(SongInfo song1) {
        this.song1 = song1;
    }

    public void setSong2(SongInfo song2) {
        this.song2 = song2;
    }
}
