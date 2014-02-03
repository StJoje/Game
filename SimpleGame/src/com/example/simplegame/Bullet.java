package com.example.simplegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;

public class Bullet extends SceneObject
{
	static MediaPlayer blaster;
    
    /**�������� �� �=15*/
    private int mSpeed=40;
    
    public double angle;
    public boolean isTargeted=false;    
    final public int prWidth=50;
    final public int prHeight=50;
    public int width;    
    public  int height;
    protected static Bitmap bulletBmp;
    protected Rect sourceRect;    // ������������� ������� � bitmap,  ������� ����� ����������
    protected int frameNr;        // ����� ������ � ��������
    protected int currentFrame;    // ������� ����
    protected long frameTicker;    // ����� ���������� ���������� �����
    protected int framePeriod;    // ������� ����������� ������ ������ ����� ������ ����� (1000/fps)

      
       /**�����������*/
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
             //���� ������ ���� � ������m���� �� ���������� �a����� � ������
             angle = Math.atan((double)(y - gameView.shotY) / (x - gameView.shotX)); 
       }
 
       /**����������� �������, ��� �����������*/
       private void update() {         
    	   long gameTime=System.currentTimeMillis();
    	   if (gameTime > frameTicker + framePeriod) {
    	        frameTicker = gameTime;
    	        // ����������� ����� �������� �����
    	        currentFrame++;
    	        //���� ������� ���� ��������� ����� ���������� ����� � 
    	        // ������������ ������������������, �� ��������� �� ������� ����
    	        if (currentFrame >= frameNr) {
    	            currentFrame = 0; 
    	        }
    	    }
    	    // ���������� ������� �� ������� � ������������, ��������������� �������� �����
    	    this.sourceRect.left = currentFrame * width;
    	    this.sourceRect.right = this.sourceRect.left + width;
    	    
    	   x += mSpeed * Math.cos(angle);         
    	   y += mSpeed * Math.sin(angle);         
       }

       static public void sound(){
    	   
    	   blaster.start();
       }
      /**������ ���� �������*/
       @Override
       public void onDraw(Canvas canvas) {
            update();                    
         // �������, ��� �������� ������
            Rect destRect = new Rect(x, y, x + width, y + height);
            //�������� ������ ������� �� �����.
            Bitmap bmp=Bitmap.createScaledBitmap(bulletBmp, prWidth*frameNr, prHeight, false);
            canvas.drawBitmap(bulletBmp, sourceRect, destRect, null);
   
       }
       
       static public void setBullBmp(Bitmap bull){bulletBmp=bull;}
}