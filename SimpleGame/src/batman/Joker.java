package batman;

import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.example.simplegame.Enemy;
import com.example.simplegame.GameView;
import com.example.simplegame.R;

public class Joker extends Enemy {
	
	static Bitmap enemyBmp;
	static Bitmap death;
	static int[] sounds=new int[2];
	static int sound;
	static SoundPool soundPl;
	static boolean isLoaded=false;
	Bitmap current;
	
	
	
	static{
		enemyBmp=BitmapFactory.decodeResource(gameView.getResources(), R.drawable.joker);

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
	     
	    
	    sounds[0] = soundPl.load(gameView.getContext(), R.raw.jokers_laughing, 1);
	    sounds[1] = soundPl.load(gameView.getContext(), R.raw.jokers_laughing2, 1);
	    isLoaded=true;

	    
	}

	public Joker(){
	
		super();
		Random rnd=new Random();
        this.effectDesc=1;
        damage=50;
        chance=70;
    	this.width=(int) enemyBmp.getWidth();
        this.height=(int) enemyBmp.getHeight();
	     sound=sounds[rnd.nextInt(2)];
        
        
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
		soundPl.play(sound, volume, volume, 1, 0, 1f);		
	}


	@Override
	public boolean isLoaded() {
		// TODO Auto-generated method stub
		return isLoaded;
	}

}
