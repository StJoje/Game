package com.example.simplegame.packman;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.simplegame.Enemy;
import com.example.simplegame.GameView;
import com.example.simplegame.HorizontalRunner;
import com.example.simplegame.R;

public class Ghost  extends Enemy{   
     
	   static public ArrayList<Bitmap> gBmps=new ArrayList<Bitmap>();

	static{		
		gBmps.add(BitmapFactory.decodeResource(gameView.getResources(), R.drawable.ghost_blue));
		gBmps.add(BitmapFactory.decodeResource(gameView.getResources(), R.drawable.ghost_red));
		gBmps.add(BitmapFactory.decodeResource(gameView.getResources(), R.drawable.ghost_orange));	}
   

    /**Конструктор класса*/
    public Ghost(){
        
        Random rnd=new Random();
        this.x = gameView.getWidth();
        this.y = rnd.nextInt(gameView.getHeight()-100);
        bubble= new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        this.chance=80;
        this.effectDesc=1;
        this.width=25;
        this.height=25;
        this.damage=50;
		enemyBmp=gBmps.get(rnd.nextInt(3));
        
    }
    
    
    
    @Override
    public void onDraw(Canvas c){
        update();
        c.drawBitmap(enemyBmp, x, y, null);}


	@Override
	public void effect(GameView gv) {
	gv.getPlayer().setLifes(gv.getPlayer().getLifes() - damage);}
	
	public void sound(){}

	@Override
	public boolean isLoaded() {
		// TODO Auto-generated method stub
		return true;
	}
}
