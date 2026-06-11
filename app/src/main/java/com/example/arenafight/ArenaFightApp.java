package com.example.arenafight;

import android.app.Application;
import com.example.arenafight.audio.AudioManager;

public class ArenaFightApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 🔥 ICI ON INITIALISE L'AUDIO UNE SEULE FOIS
        AudioManager.getInstance().init(this);
    }
}
