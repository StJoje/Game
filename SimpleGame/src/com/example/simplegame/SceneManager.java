package com.example.simplegame;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class SceneManager extends Activity{
	private ViewFlipper gallery;
	private TextView txtPages;
	private CheckBox checkBoxLoop;
	private LayoutInflater inflater = null;
	private float fromPosition;
	private int count;
	private List<Bitmap> items;
	Button prev;
	Button next;
	Button start;
	Context ctx;
	
	private boolean isStarted=false;//метод onTouch для btnStart вызывается несколько раз


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.scene_manager);
		
		initList();
		   
		  inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		   
		  
		  this.gallery = (ViewFlipper)findViewById(R.id.gallery);  
		  this.next = (Button)findViewById(R.id.next);
		  this.prev = (Button)findViewById(R.id.prev);
		  this.start = (Button)findViewById(R.id.startBtn);


		  ctx=this;
		  
		  next.setOnTouchListener(new OnTouchListener() {
		    
		   @Override
		   public boolean onTouch(View v, MotionEvent event) {
			   if(event.getAction()==MotionEvent.ACTION_DOWN)
				   {next();
				   Log.d("MeNext","Count:"+count);}
		   return true;}
		   
		  });
		   
		  prev.setOnTouchListener(new OnTouchListener() {
			    
			   @Override
			   public boolean onTouch(View v, MotionEvent event) {
				   if(event.getAction()==MotionEvent.ACTION_DOWN)
					   {previous();
					   Log.d("MePrev","Count:"+count);}
			   return true;}
			   
			  });
		  
		  start.setOnTouchListener(new OnTouchListener() {
			    
			   @Override
			   public boolean onTouch(View v, MotionEvent event) {
				 if(isStarted==false){
				
				isStarted=true;
				  Intent startIntent=new Intent(ctx,FullscreenActivity.class);
				  if(count==0)
				  {startIntent.putExtra("PLAYER", "Batman");}
				  else					  
				  {startIntent.putExtra("PLAYER", "Pacman");}
				  ctx.startActivity(startIntent);
				  Log.d("SM","ontouch");
				  finish();
				  }

			   return true;}
			   
			  });
			   
	Log.d("SM","oncreate");
		 
		   
		  gallery.addView(addImage(items.get(0)));
		 }
	

	

private View addImage(Bitmap bitmap)
 {
  ImageView view = (ImageView)inflater.inflate(R.layout.gallery_item, null);
  view.setImageBitmap(bitmap);
   
  return view;
 }


private void removeImages()
 {
  if (gallery.getChildCount() > 2)
  {
   gallery.removeViewAt(0);
   System.gc();
  }
 }

private void addNextImage(int position, boolean isLeft)
{  
 if (isLeft)
 {
  if (position >= 0)
  {
   gallery.addView(addImage(items.get(position)));
  }
 } else
 {
  if (position < items.size())
   gallery.addView(addImage(items.get(position)));
 }
}


private void updateTextView()
{
 String pages = String.format("Страница", (count + 1), items.size());
 txtPages.setText(pages);
}


public void next()
{
if (count+1 >= items.size())
{count = -1;}

count++;
addNextImage(count, false);
 gallery.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.go_next_in));
    gallery.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.go_next_out));
    gallery.showNext();   
    removeImages(); 
    
}

public void previous()
{
if (count <= 0)
count = items.size();

count--;
addNextImage(count, true);
 gallery.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.go_prev_in));
    gallery.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.go_prev_out));
    gallery.showNext();
    removeImages(); 
    
}

private void initList()
{
 items = new ArrayList<Bitmap>();
 items.add(BitmapFactory.decodeResource(getResources(), R.drawable.batman_player_rage));
 items.add(BitmapFactory.decodeResource(getResources(), R.drawable.player));

}

}
