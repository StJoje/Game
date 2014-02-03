package com.example.simplegame;
import java.util.Random;

import android.graphics.Canvas;



abstract public class HorizontalRunner extends SceneObject{
	
	/**Скорость*/
    public int speed;  
    public int effectDesc;//0-effect after popping 1-effect after passing the player
    protected int chance;
	public HorizontalRunner() {
		Random rnd=new Random();
		this.speed = rnd.nextInt(10);
	}

	public void update(){
        x -= speed;
    }
		
	abstract public boolean isLoaded();	
	abstract public void effect(GameView gv);	
	abstract public void onDraw(Canvas cnv);
	abstract public void sound();
}