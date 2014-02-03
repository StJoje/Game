package com.example.simplegame;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import batman.BombBuff;
import batman.Joker;
import batman.Twofaced;

import com.example.simplegame.packman.Buff;
import com.example.simplegame.packman.Ghost;
import com.example.simplegame.packman.Medicine;


public class FullscreenActivity extends Activity {
	
	GameView game;
	private final int IDD_LIST_MENUS = 1;
	AlertDialog.Builder builder;
	Context ctx;
	String player;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);       
        player=getIntent().getStringExtra("PLAYER");
        Log.d("Me","Player:"+player);
        Log.d("Full","oncreate");        
    }
    
    @Override
    public void onStart(){
    	
    	super.onStart();
    	WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        Bitmap plBmp;
        Player pl = null;
        Bitmap background=null;
        Bitmap bull=null;
        ArrayList<Class<? extends HorizontalRunner>> hrs=new ArrayList<Class<? extends HorizontalRunner>>();
        MediaPlayer backsound = null;
        int bulletSound = 0;

        if(player.equals("Batman"))
        {
        	Log.d("Me", "Batman");
        	 plBmp=BitmapFactory.decodeResource(getResources(), R.drawable.bat_anim);
             pl=new Player(plBmp,metrics,100,4,4);
             
             hrs.add(Joker.class);
             hrs.add(Twofaced.class);
             hrs.add(BombBuff.class);             
          	Log.d("Me", "Batman3");

            backsound=MediaPlayer.create(this, R.raw.batman_backsound2);
            background=BitmapFactory.decodeResource(getResources(), R.drawable.bat_back);
            bull=BitmapFactory.decodeResource(getResources(), R.drawable.batbull_anim3);
            bulletSound=R.raw.batman_bull;
             
          	Log.d("Me", "Batman4");
        	
         	Log.d("Me", "Batman2");

        }
        
        else 
        	if(player.equals("Pacman"))
        	
        {
        		Log.d("Me","Pacman");
        	plBmp=BitmapFactory.decodeResource(getResources(), R.drawable.player);
            pl=new Player(plBmp,metrics,100,1,1);            
            hrs.add(Ghost.class);
            hrs.add(Buff.class);
            hrs.add(Medicine.class);         
            backsound=MediaPlayer.create(this, R.raw.eightbitpacman); 
            background=BitmapFactory.decodeResource(getResources(), R.drawable.city);
            bull=BitmapFactory.decodeResource(getResources(), R.drawable.bullet);
            bulletSound=R.raw.blaster;
        	
        }
        
        game=new GameView(this.getBaseContext(),pl,hrs, background, bull,backsound,bulletSound);
        setContentView(game);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }
    @Override
    public void onPause(){
    	super.onPause();
Log.d("Fullscr","Pause");
    	
    	
    }
    
   @Override
   protected void onStop(){
      super.onStop();
      Log.d("Fullscr","Stop");

     finish();
   }
   
   @Override
   protected void onRestart(){
      super.onRestart();

   }
  
   @Override
   public void onBackPressed(){
	   finish();

       
   }
   
 @SuppressWarnings("deprecation")
@Override
	// Метод вызывается при нажатии на кнопку Menu девайса
	public boolean onCreateOptionsMenu(Menu menu) {
	   Log.d("Me","Pause");
	 game.isPaused=true;
	   game.pause();
       showDialog(IDD_LIST_MENUS);
		return false;
		
	}
   
   @Override
   protected Dialog onCreateDialog(int id) {
       switch (id) {
       

       case IDD_LIST_MENUS:
           
           final String[] mPuncts ={"Resume","Restart", "Change mode", "Exit"};

           builder = new AlertDialog.Builder(this);
           builder.setTitle(""); // заголовок для диалога

           builder.setItems(mPuncts, new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int item) {
                   // TODO Auto-generated method stub
                   switch(item){
                   case 0:
                	   game.isPaused=false;
                	   game.resume();
                	   break;

                   case 1:
                       Log.d("Full","Item:"+item);
                       
                       Intent i = new Intent(ctx, FullscreenActivity.class);
                       i.putExtra("PLAYER", player);
            			startActivity(i);
                       finish();
                     
             			
             			
           			break;
                   case 2:
                       
              			
                   case 3: Log.d("Full","Item:"+item);

                   finish();break;
                   }
               }
           });
           builder.setCancelable(false);
           return builder.create();
           
       default:
           return null;
       }
}}
