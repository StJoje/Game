package com.example.simplegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;




public class Player extends SceneObject
{
        /**Объект главного класса*/
    private int lifes;
    private int isBuf=0;//1 for pacman buff,2 for bat buff
    Bitmap playerBmp;
    private Rect sourceRect;    // Прямоугольная область в bitmap,  которую нужно нарисовать
    private int frameNr;        // Число кадров в анимации
    private int currentFrame;    // Текущий кадр
    private long frameTicker;    // время обновления последнего кадра
    private int framePeriod;    // сколько миллисекунд должно пройти перед сменой кадра (1000/fps)
    private int prWidth=100;
    private int prHeight=150;
      

        //конструктор	
    public Player(Bitmap bmp,DisplayMetrics b,int lifes,int fps,int frameCount)
    {
        super();           
        
        currentFrame = 0;
        frameNr = frameCount;        
        playerBmp=Bitmap.createScaledBitmap(bmp, prWidth*frameNr,prHeight, false);
        this.width = playerBmp.getWidth()/frameNr;       
        this.height = playerBmp.getHeight(); 
        sourceRect = new Rect(0, 0, prWidth*frameNr, prHeight);
        framePeriod = 1000 / fps;
        frameTicker = 0l;        
        this.setLifes(lifes);
        this.x = 0;                        //отступ по х нет
        this.y = b.heightPixels/2; //делаем по центру
                
    }
    
    public void update(){
    	
    	long gameTime=System.currentTimeMillis();
 	   if (gameTime > frameTicker + framePeriod) {
 	        frameTicker = gameTime;
 	        // увеличиваем номер текущего кадра
 	        currentFrame++;
 	        //если текущий кадр превышает номер последнего кадра в 
 	        // анимационной последовательности, то переходим на нулевой кадр
 	        if (currentFrame >= frameNr) {
 	            currentFrame = 0; 
 	        }
 	    }
 	    // Определяем область на рисунке с раскадровкой, соответствующую текущему кадру
 	    this.sourceRect.left = currentFrame * width;
 	    this.sourceRect.right = this.sourceRect.left + width;
    }

    //рисуем наш спрайт
    public void onDraw(Canvas canvas)
    {      
        Paint text=new Paint();
        text.setColor(Color.WHITE);
        text.setTextSize(50);
        canvas.drawText("Your lifes:"+getLifes(), 100, 100, text);
        
        update();
     // область, где рисуется спрайт
        Rect destRect = new Rect(x, y, x + width, y + height);
        //комманда вывода рисунка на экран.
        canvas.drawBitmap(playerBmp, sourceRect, destRect, null);
    }

	public int isBuf() {
		
		return isBuf;
	}

	public void setBuf(int isBuf) {
		this.isBuf = isBuf;
	}

	public int getLifes() {
		return lifes;
	}

	public void setLifes(int lifes) {
		this.lifes = lifes;
	}
}


