package com.example.simplegame;

import java.util.Random;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

abstract public class Enemy  extends HorizontalRunner{   
     
    protected int damage;
   protected static SoundPool bubble;
   public Bitmap enemyBmp;
   protected static float volume;
   static{
	   
	   AudioManager audioManager = (AudioManager) gameView.getContext().getSystemService(Activity.AUDIO_SERVICE);
        float actualVolume = (float) audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume = actualVolume ;
   }
   

    /**Конструктор класса*/
   public Enemy(){
        Random rnd=new Random();
        this.x = gameView.getWidth();
        int arg=gameView.getHeight()-100;
        Log.d("Me","Arg:"+arg);
        if(arg>0)
        this.y = rnd.nextInt(arg);
        else
        this.y=gameView.getHeight()-100;
        bubble= new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        this.width=25;
        this.height=25;
        
    }
    
    
    
   
}
