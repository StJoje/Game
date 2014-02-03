package batman;

import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.example.simplegame.Enemy;
import com.example.simplegame.GameView;
import com.example.simplegame.HorizontalRunner;
import com.example.simplegame.R;

public class Twofaced extends Enemy {
	
	static Bitmap enemyBmp;
	static Bitmap death;
	static int[] sounds=new int[2];
	static int sound;
	static SoundPool soundPl;
	Bitmap current;
	static Class<? extends HorizontalRunner> effect=Joker.class;
	static boolean isLoaded=false;
	
	
	
	
	static{
		enemyBmp=BitmapFactory.decodeResource(gameView.getResources(), R.drawable.twofaced);

		WindowManager wm = (WindowManager) gameView.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
		int scaleWidth = (  enemyBmp.getWidth()/metrics.widthPixels);
	    int scaleHeight = ( enemyBmp.getHeight()/metrics.heightPixels);
	    Log.d("Me","w: "+metrics.widthPixels+" h:"+metrics.heightPixels+" sw:"+scaleWidth+" sh:"+scaleHeight+
	    		"bw:"+enemyBmp.getWidth()+"bh:"+enemyBmp.getHeight());
	    
	    enemyBmp=Bitmap.createScaledBitmap(enemyBmp, 65, 65, false);
	    
	    soundPl=new SoundPool(10, AudioManager.STREAM_MUSIC, 0);      
	     soundPl.setOnLoadCompleteListener(new OnLoadCompleteListener() {
	            @Override
	            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
	                Log.e("Me", "sampleId="+sampleId+" status="+status);
	            }
	        });
		    sound = soundPl.load(gameView.getContext(), R.raw.twofaced_shouting, 1);
		    isLoaded=true;
		}

	public Twofaced(){
	
		super();
		Random rnd=new Random();
		this.effectDesc=2;        
        damage=200;
        chance=50;    	
    	this.width=(int) enemyBmp.getWidth();
        this.height=(int) enemyBmp.getHeight();        
	}
	
	
	@Override
	public void effect(GameView gv) {
		// TODO Auto-generated method stub
		sound();
		gv.getPlayer().setLifes(gv.getPlayer().getLifes() - damage);
		
	}

	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas cnv) {
		// TODO Auto-generated method stub
		/*Paint paint = new Paint();
    	paint.setFilterBitmap(true);*/
		update();
		
    	cnv.drawBitmap(enemyBmp, x,y, null);
    	
		
	}

	@Override
	public void sound() {
		// TODO Auto-generated method stub
		soundPl.play(sound, volume*1.5f, volume*1.5f, 1, 0, 1f);
		
	}


	@Override
	public boolean isLoaded() {
		// TODO Auto-generated method stub
		return isLoaded;
	}

}
