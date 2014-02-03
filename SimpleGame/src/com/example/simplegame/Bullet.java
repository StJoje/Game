package com.example.simplegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;

public class Bullet extends SceneObject
{
	static MediaPlayer blaster;
    
    /**Скорость по Х=15*/
    private int mSpeed=40;
    
    public double angle;
    public boolean isTargeted=false;    
    final public int prWidth=50;
    final public int prHeight=50;
    public int width;    
    public  int height;
    protected static Bitmap bulletBmp;
    protected Rect sourceRect;    // Прямоугольная область в bitmap,  которую нужно нарисовать
    protected int frameNr;        // Число кадров в анимации
    protected int currentFrame;    // Текущий кадр
    protected long frameTicker;    // время обновления последнего кадра
    protected int framePeriod;    // сколько миллисекунд должно пройти перед сменой кадра (1000/fps)

      
       /**Конструктор*/
       public Bullet(  int fps, int frameCount) {
            
    	   	 this.x = 0;            
             this.y = gameView.getHeight()/2;                            
             currentFrame = 0;
             frameNr = frameCount;
             this.width = bulletBmp.getWidth()/frameNr;       
             this.height = bulletBmp.getHeight();    
             bulletBmp=Bitmap.createScaledBitmap(bulletBmp, prWidth*frameNr, prHeight, false);
             sourceRect = new Rect(0, 0, prWidth, prHeight);
             framePeriod = 1000 / fps;
             frameTicker = 0l;
             width=prWidth;
             height=prHeight;
             //угол полета пули в зависиmости от координаты кaсания к экрану
             angle = Math.atan((double)(y - gameView.shotY) / (x - gameView.shotX)); 
       }
 
       /**Перемещение объекта, его направление*/
       private void update() {         
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
    	    
    	   x += mSpeed * Math.cos(angle);         
    	   y += mSpeed * Math.sin(angle);         
       }

       static public void sound(){
    	   
    	   blaster.start();
       }
      /**Рисуем наши спрайты*/
       @Override
       public void onDraw(Canvas canvas) {
            update();                    
         // область, где рисуется спрайт
            Rect destRect = new Rect(x, y, x + width, y + height);
            //комманда вывода рисунка на экран.
            Bitmap bmp=Bitmap.createScaledBitmap(bulletBmp, prWidth*frameNr, prHeight, false);
            canvas.drawBitmap(bulletBmp, sourceRect, destRect, null);
   
       }
       
       static public void setBullBmp(Bitmap bull){bulletBmp=bull;}
}