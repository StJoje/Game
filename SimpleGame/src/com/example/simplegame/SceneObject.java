package com.example.simplegame;

import android.graphics.Canvas;
import android.graphics.Matrix;

abstract public class SceneObject {

	/**Х и У коорданаты*/
    public int x; 
    public int y; 
    
    
    /**Выосота и ширина спрайта*/
    public int width;
    public int height;
    
    static public GameView gameView;

   protected Matrix matrix;//матрица для отрисовки объекта
	  
    
abstract public void onDraw(Canvas c);

public boolean onScene(GameView gv){
	if(x >= 0  && x <= gv.getWidth())
	return true;
	else return false;	}

public static void setGameView(GameView gv){gameView=gv;}
}
