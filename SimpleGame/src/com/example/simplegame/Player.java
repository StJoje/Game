package com.example.simplegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;




public class Player extends SceneObject
{
        /**������ �������� ������*/
    private int lifes;
    private int isBuf=0;//1 for pacman buff,2 for bat buff
    Bitmap playerBmp;
    private Rect sourceRect;    // ������������� ������� � bitmap,  ������� ����� ����������
    private int frameNr;        // ����� ������ � ��������
    private int currentFrame;    // ������� ����
    private long frameTicker;    // ����� ���������� ���������� �����
    private int framePeriod;    // ������� ����������� ������ ������ ����� ������ ����� (1000/fps)
    private int prWidth=100;
    private int prHeight=150;
      

        //�����������	
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
        this.x = 0;                        //������ �� � ���
        this.y = b.heightPixels/2; //������ �� ������
                
    }
    
    public void update(){
    	
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
    }

    //������ ��� ������
    public void onDraw(Canvas canvas)
    {      
        Paint text=new Paint();
        text.setColor(Color.WHITE);
        text.setTextSize(50);
        canvas.drawText("Your lifes:"+getLifes(), 100, 100, text);
        
        update();
     // �������, ��� �������� ������
        Rect destRect = new Rect(x, y, x + width, y + height);
        //�������� ������ ������� �� �����.
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


