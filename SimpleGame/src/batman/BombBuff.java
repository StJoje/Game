package batman;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.simplegame.GameView;
import com.example.simplegame.HorizontalRunner;
import com.example.simplegame.R;

public class BombBuff extends HorizontalRunner{

	
	  static public Bitmap buffBmp=BitmapFactory.decodeResource(gameView.getResources(), R.drawable.buff); 


	    /**Конструктор класса*/
	    public BombBuff(){
	    	
	        Random rnd=new Random();      
	        this.x = gameView.getWidth();	        
	        int arg=gameView.getHeight()-100;	        
	        if(arg>0)
	        this.y = rnd.nextInt(arg);
	        else
	        this.y=gameView.getHeight()/2;
	        this.chance=20;
	        this.effectDesc=3;//bullet are changing to bombs
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
			gv.getPlayer().setBuf(2);
			Bitmap bmp=BitmapFactory.decodeResource(gameView.getResources(),R.drawable.bomb_anim);
	    	BulletBomb.setBullBmp(bmp);
			Timer t=new Timer();
			t.schedule(new BuffTask(gv), 5000);
			
		}
		
		 class BuffTask extends TimerTask{

		    	GameView a;
		    	BuffTask(GameView s){a=s;}
				@Override
				public void run() {					
			    	BulletBomb.setBullBmp(gameView.bullBmp);
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
