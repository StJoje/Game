package com.example.simplegame.packman;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.simplegame.GameView;
import com.example.simplegame.HorizontalRunner;
import com.example.simplegame.R;

public class Medicine extends HorizontalRunner {
 
    static public Bitmap medBmp= BitmapFactory.decodeResource(gameView.getResources(), R.drawable.med);

    /**Конструктор класса*/
    public Medicine(){
          	
        Random rnd=new Random();           
        this.x = gameView.getWidth();
        this.y = rnd.nextInt(gameView.getHeight()-100);
 
        this.chance=20;
        this.effectDesc=0;
        this.width = 25;
        this.height = 25;
    }
    
    static public void setMedBmp(Bitmap bmp){medBmp=bmp;}
    
    @Override
    public void onDraw(Canvas c){
    	
        update();
        c.drawBitmap(medBmp, x, y, null);
    }


	@Override
	public void effect(GameView gv) {
		gv.getPlayer().setLifes(gv.getPlayer().getLifes() + 100);
		
	}

	@Override
	public void sound() {}

	@Override
	public boolean isLoaded() {
		// TODO Auto-generated method stub
		return true;
	}

}
