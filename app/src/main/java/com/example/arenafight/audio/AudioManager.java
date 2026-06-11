package com.example.arenafight.audio;

import android.content.Context;
import android.media.MediaPlayer;
import com.example.arenafight.R;

import java.util.HashMap;

public class AudioManager {

    private static AudioManager instance;
    private MediaPlayer musicPlayer;

    private AudioManager() {}

    public static AudioManager getInstance() {
        if(instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    private HashMap<MusicType, Integer> musics = new HashMap<>();
    private HashMap<SoundType, Integer> sounds = new HashMap<>();
    public void init(Context context) {

        musics.put(MusicType.COMBAT, R.raw.combat);
    }

    public void playMusic(Context context, MusicType musicType) {

        Integer resId = musics.get(musicType);

        if(resId == null) {
            return;
        }

        if(musicPlayer != null) {
            musicPlayer.stop();
            musicPlayer.release();
        }

        musicPlayer = MediaPlayer.create(context, resId);
        musicPlayer.setLooping(true);
        musicPlayer.start();
    }

    public void stopMusic() {
        if(musicPlayer != null) {
            musicPlayer.stop();
            musicPlayer.release();
            musicPlayer = null;
        }
    }


}