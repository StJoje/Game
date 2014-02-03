package com.example.simplegame.packman;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.simplegame.GameView;
import com.example.simplegame.HorizontalRunner;
import com.example.simplegame.R;

public class Buff extends HorizontalRunner{

   

    static public Bitmap buffBmp=BitmapFactory.decodeResource(gameView.getResources(), R.drawable.buff); 


    /**Конструктор класса*/
    public Buff(){
    	
        Random rnd=new Random();
        
      
        this.x = gameView.getWidth();
        int arg=gameView.getHeight()-100;
        if(arg>0)
        this.y = rnd.nextInt(arg);
        else
        this.y=gameView.getHeight()/2;
        this.chance=20;
        this.effectDesc=0;
        this.width = 25;
        this.height = 25;
    }
    
    public static void setBuffBmp(Bitmap buff)
    {
    	buffBmp=buff;
    	
    }
    
    @Override
    public void onDraw(Canvas c){
    	
        update();
        c.drawBitmap(buffBmp, x, y, null);
    }

	@Override
	public void effect(GameView gv) {
		gv.getPlayer().setBuf(1);
		
		Timer t=new Timer();
		t.schedule(new BufTask(gv), 3000);
		
	}
	
	 class BufTask extends TimerTask{

	    	GameView a;
	    	BufTask(GameView s){a=s;}
			@Override
			public void run() {
				a.getPlayer().setBuf(0);
				
			}}

	@Override
	public void sound() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLoaded() {
		// TODO Auto-generated method stub
		return true;
	}

}