package com.example.simplegame;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;

public class BackgroundSound extends AsyncTask<Void, Void, Void> {

	Context context;
	MediaPlayer player;
	BackgroundSound(GameView a){
		context=a.getContext();	
	}
    @Override
    protected Void doInBackground(Void... params) {
        player = MediaPlayer.create(context, R.raw.eightbitpacman);
        player.setLooping(true); // Set looping 
        player.setVolume(100,100); 
        player.start();
        Log.d("CUSTOMTAG","background");      
		return null;      
    }
    
    protected void stop(){
    	player.stop();
    	player.release();    	
    }

}