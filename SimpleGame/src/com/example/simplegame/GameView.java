package com.example.simplegame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import batman.BulletBomb;
import batman.Joker;

public class GameView extends SurfaceView
{
        long beginTime ;
        long endTime ;
        long lastDrawingTime=0;
        int scores=0;
        int level=1;
        boolean isRunning;
        public  boolean isPaused=false;
        boolean isDrawn=false;//переменная для предотвращения задержки отрисовки при загрузке GameView
        public Bitmap bullBmp;
        
        
    private GameThread mThread;    
    public int shotX;
    public int shotY;     
    DisplayMetrics displaymetrics = getResources().getDisplayMetrics();//ïàðàìåòðû ýêðàíà 
    private boolean running = false;
    
    SoundPool shortSound;
    MediaPlayer backSound;

    private int sblop;
    private int sblast;
    float actualVolume;
    float maxVolume;
    float volume;
    
    
    private List<Bullet> balls = new ArrayList<Bullet>();   
    List<BulletBomb> bombs=new ArrayList<BulletBomb>();
    ArrayList<HorizontalRunner> runners=new ArrayList<HorizontalRunner>();
    private List<Class<? extends HorizontalRunner>> types=Collections.synchronizedList(new ArrayList<Class<? extends HorizontalRunner>>());
    private Player player;
    
    Bitmap background;
    Bitmap ghostRed,ghostBlue,ghostOrange;
    Bitmap playerBmp;
    Bitmap medBmp;
    Bitmap buffBmp;
    
    
    private CreatingRunnersThread runnersThread;
    private OnDisplayThread odt;
    
    
     
    
public class OnDisplayThread extends Thread{
            
        boolean isRun=false;
        boolean isPaused=false;
        GameView gv;
        OnDisplayThread(GameView gv){
                this.gv=gv;}
            
            public void setRunning(boolean run){isRun=run;}
            @Override
            public void run()
        {
                beginTime= System.currentTimeMillis();

                    while(isRun)
                        {
                    	
                    	while(isPaused){}
                         
                            if(gv.getPlayer().getLifes()<=0)
                            {game_over();}                           
                    testCollision();

                    endTime= System.currentTimeMillis();
                    scores=(int) (endTime-beginTime)/100;
                    if(scores<=50)
                    {level=1;}
                    else
                            if(scores<=100)
                            {level=2;}
                            else 
                                    if(scores<=200)
                                    {level=3;}
                                    else 
                                            if(scores<=400)
                                                    level=4;
                                            else
                                            	if(scores<=550)
                                                {level=5;}
                                                else 
                                                	if(scores<=700)
                                                    {level=6;}
                                                    else 
                                                    	if(scores<=800)
                                                        {level=7;}
                                                        else 
                                                        	if(scores<=950)
                                                            {level=8;}
                                                            else 
                                                            	if(scores<=1100)
                                                                {level=9;}
                                                                else 
                                                                	if(scores<=1200)
                                                                    {level=10;}
                                                                    
                   
                            for(int i=0;i<balls.size();i++)
                            	{Bullet a=balls.get(i);
                            if(!a.onScene(gv))
                            	balls.remove(i); }
               
                synchronized(runners){
          
                for(int i=0;i<runners.size();i++)
                    {HorizontalRunner a=runners.get(i);
                    if(!a.onScene(gv))        
                    	{
                            runners.remove(i);
                    if(a.effectDesc==1||a.effectDesc==2)
                            {try {
                                        a.getClass().newInstance().effect(gv);
                                        Log.d("Me","Geffect");
                            } catch (InstantiationException e) {
                                    
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                }}
                    }
                    
                    }
                         }}}}
        
           
public class CreatingRunnersThread extends Thread{
            
            boolean isRun;
            GameView gv;
            boolean isPaused=false;
 
            CreatingRunnersThread(GameView gv){this.gv=gv;}
            
            public void setRunning(boolean run){isRun=run;}
            @Override
            public void run()
        {
                                            
            while(isRun){
              while(isPaused){}
              Log.d("Me","CRT");
              Random rnd = new Random();
                    
                try {
                        
                    Log.d("enemy","new");
                    Thread.sleep(rnd.nextInt(2000)/level);  
                    
                    for(Class<? extends HorizontalRunner> cl:types)
                    {
                            Object obj=null;
                                                try {
                                                        
                                                        obj = cl.newInstance();
                                                } catch (InstantiationException e) {
                                                        // TODO Auto-generated catch block
                                                        e.printStackTrace();
                                                } catch (IllegalAccessException e) {
                                                        Log.d("Me","Proval");
                                                        // TODO Auto-generated catch block
                                                        e.printStackTrace();
                                                }
                            HorizontalRunner ex=(HorizontalRunner)obj;
                            
                            synchronized(runners){
                    if(rnd.nextInt(100)<=ex.chance)        
                    { runners.add(ex); break;}}
                            					
            }
                   
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                        
                        }         
    }
}

    
    public class GameThread extends Thread
    {
        private GameView view;         
        boolean isPaused=false;

        public GameThread(GameView view) 
        {
              this.view = view;
        }

        public void setRunning(boolean run) 
        {
              running = run;
        }
        
                @SuppressLint("WrongCall")
                public void run()
        {
            backSound.setLooping(true); // Set looping 
            backSound.setVolume(70,70); 
            backSound.start();
            
            
            while (running)
            {
            	while(isPaused){}
                    
                Canvas canvas = null;
                try
                {
                        
                         // ïîäãîòîâêà Canvas-à
                    canvas = view.getHolder().lockCanvas();
                    if(canvas==null)
                    {Log.d("Me","canvas!=null");}
                    Log.d("Me","flag");
                    synchronized (view.getHolder())
                    {
                        // ñîáñòâåííî ðèñîâàíèå
                    	Log.d("Me","drawning");
                        onDraw(canvas);
                        Log.d("Me","drawning2");
                        isDrawn=true;
                    }
                    
                }
                catch (Exception e) { Log.d("Me","Canvas is not locked");}
                finally
                {
                    if (canvas != null)
                    {
                            view.getHolder().unlockCanvasAndPost(canvas);
                    }
                }
            }//end of while
            
            if(!running)
                        {Log.d("Me","Sound is cancelled");
                        backSound.stop();
                        backSound.release();
                        
                        
                        return;}
        }
}

    
    protected GameView(Context context,Player pl, final List<Class<? extends HorizontalRunner>> runners,Bitmap background,
                    Bitmap bullBmp,MediaPlayer backsound,int bullSound) 
    {
        super(context);
        Log.d("Gameview","Constructor");       
        mThread = new GameThread(this);
        runnersThread=new CreatingRunnersThread(this);
        odt=new OnDisplayThread(this);
        
        getHolder().addCallback(new SurfaceHolder.Callback() 
        {
               public void surfaceDestroyed(SurfaceHolder holder) 
               {
                       Log.d("Me","Surfacedestroyed");
                       boolean retry = true;
                        mThread.setRunning(false);
                        runnersThread.setRunning(false);
                        odt.setRunning(false);
                        backSound.stop();
                        Log.d("Me","threads have been stopped");
                        while (retry)
                        {
                            try
                            {
                                mThread.join();
                                runnersThread.join();
                                odt.join();
                                retry = false;
                            }
                            catch (InterruptedException e) { }
                        }
               }

               public void surfaceCreated(SurfaceHolder holder) 
               {
            	   boolean isLoaded=false;
            	   while(!isLoaded){
            		   Log.d("GameView","Loading");
            		   int loads=0;
            	   for(int i=0;i<runners.size();i++)
            	   {Class<? extends HorizontalRunner> cl=runners.get(i);
            		   try {
					if(cl.newInstance().isLoaded())
						loads++;
					if(loads==runners.size())
						isLoaded=true;
						
					Log.d("Me","Created");
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}}
            	   
            	   	
                       mThread.setPriority(Thread.MAX_PRIORITY);
                       mThread.setRunning(true);
                       mThread.start();
                       Log.d("Me","thread "+mThread.getId());
                       
                       runnersThread.setRunning(true);
                       runnersThread.start();
                       
                       odt.setRunning(true);
                       odt.start();
                       Log.d("Me","odthread "+odt.getId());

                       
                      
                    
               }

               public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
               {
               }
        });
        
 
        
        AudioManager audioManager = (AudioManager) this.getContext().getSystemService(Activity.AUDIO_SERVICE);
        actualVolume = (float) audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC);
        maxVolume = (float) audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume = actualVolume / maxVolume;
        shortSound=new SoundPool(10, AudioManager.STREAM_MUSIC, 0);   
        
        shortSound.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
               
                Log.e("Test", "sampleId="+sampleId+" status="+status);
            }
        });
 
        sblop = shortSound.load(this.getContext(), R.raw.chpok, 1);
        int bulletSound=bullSound;
        sblast = shortSound.load(this.getContext(), bulletSound, 1);        
        this.setPlayer(pl);        
        SceneObject.setGameView(this);
        Bullet.setBullBmp(bullBmp);
        this.bullBmp=bullBmp;    
        this.background=background;
        this.types=runners;
        this.backSound=backsound;
    	Log.d("Me","creating");

               
    }
    
  @SuppressLint("WrongCall")
          protected void onDraw(Canvas canvas) {  
          
      Log.d("Me","Magic");

        draw_background(canvas);
        Log.d("Me","afterMagic");

        
        for(int i=0;i<balls.size();i++)
        {Bullet a=balls.get(i);
        
        if(a.isTargeted)
        {/*
        	if(a.getClass().equals(BulletBomb.class))
        	{
        	
        		BulletBomb bb=(BulletBomb) a;
        		bb.onDrawBlowing(canvas);
        		
        	}*/
        	balls.remove(a);
        }
                a.onDraw(canvas); }        
                
        synchronized(runners){
     
                
       for(HorizontalRunner hr:runners)
        {
               hr.onDraw(canvas);
               }
       runners.notifyAll();
       }
        
        for(BulletBomb bb:bombs)
        {
        	Log.d("Me","blowing");
        	 bb.onDrawBlowing(canvas);
        	 bombs.remove(bb);
        }
                    
        Paint text=new Paint();
        text.setColor(Color.WHITE);
        text.setTextSize(50);
        canvas.drawText("Scores:"+scores, this.getWidth()-500, 100, text);
        canvas.drawText("Level:"+level, 100,this.getHeight()-100 , text);
        getPlayer().onDraw(canvas);
       
        
       
  }
    
    public Bullet createBullet() {

    	return new Bullet(8,4);
   }
    
    public BulletBomb createBulletBomb() {

    	return new BulletBomb(8,4);
   }
    
        public boolean onTouchEvent(MotionEvent e) 
    {
            shotX = (int) e.getX();
            shotY = (int) e.getY();
            Log.d("Me","Touching");
            
            shortSound.play(sblast, volume, volume, 1, 0, 1f);

            if(this.getPlayer().isBuf()==2)
            	{if(e.getAction() == MotionEvent.ACTION_DOWN)
            		{balls.add(createBulletBomb());}}
            else
            if(this.getPlayer().isBuf()==1)
            {balls.add(createBullet());}
            else
            if(e.getAction() == MotionEvent.ACTION_DOWN)
            balls.add(createBullet());
            
        return true;
    }

        
        
        public void setRunning(boolean run){isRunning=run;}
    

        /*Ïðîâåðêà íà ñòîëêíîâåíèÿ*/
    private void testCollision() {
    
          bullets:
            for(int q=0;q<balls.size();q++)
            {
                    Bullet a=balls.get(q);
            
                    synchronized(runners)
                    {
                    for(int i=0;i<runners.size();i++)
                    {
                            HorizontalRunner hr=runners.get(i);
                            if(hr==null)
                            {break;}
                            if(a==null)
                            {break bullets;}
                            if ((Math.abs(a.x - hr.x) <= (a.width + hr.width) / 2f)
                                      && (Math.abs(a.y - hr.y) <= (a.height + hr.height) / 2f))
                            {
                            	
                            	if(a.getClass().equals(BulletBomb.class))
                            	{
                            		for(int r=0;r<runners.size();r++)
                            		{
                            			HorizontalRunner h=runners.get(r);
                            			if ((Math.abs(a.x - hr.x) <= this.getWidth()/10)
                                                && (Math.abs(a.y - hr.y) <= this.getHeight()/10))
                            				runners.remove(h);
                            			
                            		}
                            		
                            		 BulletBomb bb=(BulletBomb) a;
                                     bombs.add(bb);
                            		
                            	}
                            	
                            if(hr.effectDesc==0)
                            {hr.effect(this);}
                            
                            if(hr.effectDesc==3)
                            {hr.effect(this);}
                            
                            if(hr.effectDesc==2)
                            {
                            Random rand=new Random();
                            
                            for(int r=0;r<5;r++)
                            {
                            	Joker jok=new Joker();
            					jok.x=hr.x;
            					jok.y=rand.nextInt(this.getHeight());
            					runners.add(jok);
                            }	
                            }
                            
                           
                            balls.remove(a);
                            
                            runners.remove(hr);}
                            
                    }}}
            
    }
     
    
    private void game_over(){
            
            Intent finish=new Intent(this.getContext(),EndActivity.class);
            finish.putExtra("SCORES", scores);
            finish.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.getContext().startActivity(finish);
            
            
            
    }
    
    
    public void draw_background(Canvas canvas){
    	
    	Log.d("Me","draw_backgr0");
    	Log.d("Me","value ");
    	float var=this.getWidth();
    	Log.d("Me","value1 ");

    	float var2=background.getWidth();
    	Log.d("Me","value2 ");

            float scaleWidth = var/var2;
        	Log.d("Me","draw_backgr01");

            float scaleHeight = (float) this.getHeight() / background.getHeight();
        	Log.d("Me","draw_backgr02");

            Matrix matrix = new Matrix();
        	Log.d("Me","draw_backgr03");

            matrix.postScale(scaleWidth, scaleHeight);

            Log.d("Me","draw_backgr");
            Paint paint = new Paint();
            paint.setFilterBitmap(true);
            Log.d("Me","draw_backgr_1");

            canvas.drawBitmap(background, matrix, paint);
            Log.d("Me","draw_backgr_2");

    }
    
    public void draw_bmp(Canvas canvas,Bitmap bmp){
            float scaleWidth = (float) this.getWidth() / bmp.getWidth();
            float scaleHeight = (float) this.getHeight() / bmp.getHeight();
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);

            
            Paint paint = new Paint();
            paint.setFilterBitmap(true);
            canvas.drawBitmap(background, matrix, paint);
            
            
    }

        public Player getPlayer() {
                return player;
        }

        public void setPlayer(Player player) {
                this.player = player;
        }
    
        public void pause(){
        	
       mThread.isPaused=true;
       odt.isPaused=true;
       runnersThread.isPaused=true;
        }
        
        public void resume(){
        	
        	mThread.isPaused=false;
            odt.isPaused=false;
            runnersThread.isPaused=false;
            
        	
        }
    
}