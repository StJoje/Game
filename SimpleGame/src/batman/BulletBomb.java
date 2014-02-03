package batman;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.example.simplegame.Bullet;
import com.example.simplegame.R;

public class BulletBomb extends Bullet{

	
	 static Bitmap bmpBlowing;
	 private Rect sourceRectBl;    // ������������� ������� � bitmap,  ������� ����� ����������
	 protected int frameNrBl;        // ����� ������ � ��������
	 private int currentFrameBl;    // ������� ����
	 private long frameTickerBl;    // ����� ���������� ���������� �����
	 private int framePeriodBl;    // ������� ����������� ������ ������ ����� ������ ����� (1000/fps)
	 boolean isBlowed=false;
	 
	 
	 static{
		 
		 bmpBlowing=BitmapFactory.decodeResource(gameView.getResources(), R.drawable.blow_anim);
	 }
	 
	 
	 
	public BulletBomb(int fps, int frameCount) {
		super(fps, frameCount);
		// TODO Auto-generated constructor stub
        sourceRectBl = new Rect(0, 0, prWidth, prHeight);
        framePeriodBl = 5000 / 2;//16 per second
        frameTickerBl = 0l;        
        width=50;
        height=50;

        bmpBlowing=Bitmap.createScaledBitmap(bmpBlowing, prWidth*frameNr, prHeight, false);

	}
	
	private void update_blowing() {         
 	   long gameTime=System.currentTimeMillis();
 	   if (gameTime > frameTickerBl + framePeriodBl) {
 	        frameTickerBl = gameTime;
 	        // ����������� ����� �������� �����
 	        currentFrameBl++;
 	        //���� ������� ���� ��������� ����� ���������� ����� � 
 	        // ������������ ������������������, �� ��������� �� ������� ����
 	        if (currentFrameBl >= frameNrBl) {
 	            currentFrameBl = 0; 
 	            Log.d("BulletBomb","anim is ended");
 	            isBlowed=true;
 	        }
 	    }
 	    // ���������� ������� �� ������� � ������������, ��������������� �������� �����
 	    this.sourceRectBl.left = currentFrameBl * prWidth;
 	    this.sourceRectBl.right = this.sourceRectBl.left + prWidth;
 	    
 	     
    }
	
	public void onDrawBlowing(Canvas canvas) {
		
        update_blowing();                    
        
     // �������, ��� �������� ������
        Rect destRect = new Rect(x, y, x + 100, y + 100);
        //�������� ������ ������� �� �����.                
        canvas.drawBitmap(bmpBlowing, sourceRect, destRect, null);

   }
	


}
