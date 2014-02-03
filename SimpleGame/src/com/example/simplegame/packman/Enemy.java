package com.example.simplegame.packman;

import java.util.ArrayList;
import java.util.Random;

import com.example.simplegame.GameView;
import com.example.simplegame.HorizontalRunner;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class Enemy  extends HorizontalRunner{   
     
    int damage=50;
    static SoundPool bubble;
   public  static ArrayList<Bitmap> enemiesBmp;
    public Bitmap enemyBmp;
    /**Конструктор класса*/
    public Enemy(GameView gameView,Bitmap bmp){
        super(gameView);   
        
        Random rnd=new Random();
        this.x = gameView.getWidth();
        this.y = rnd.nextInt(gameView.getHeight()-100);
        bubble= new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        
      this.enemyBmp=bmp;
        	
       
       
        this.width=25;
        this.height=25;
        
    }
    
    
    @Override
    public void onDraw(Canvas c){
    	
        update();
        c.drawBitmap(enemyBmp, x, y, null);
    }


	@Override
	public void effect(GameView gv) {
	
	gv.getPlayer().setLifes(gv.getPlayer().getLifes() - damage);	
	}
	
	static public void sound(){
	 
	
	}
}
