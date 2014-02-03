package com.example.simplegame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class StartActivity extends Activity {
	
	Button start;
	Button exit;
	Button instructions;
	ImageView revenge;
	ImageView logo1;
	ImageView logo2;
	
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);    
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Log.d("StartAct","starting2");

        setContentView(R.layout.start_activity);
        Log.d("StartAct","starting");       
        logo1=(ImageView)findViewById(R.id.logo_dobro1);
        logo2=(ImageView)findViewById(R.id.logo_dobro2);
        start=(Button)findViewById(R.id.btn_start);
        instructions=(Button)findViewById(R.id.btn_instr);
        exit=(Button)findViewById(R.id.btn_exit);
        Log.d("StartAct","starting2");

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.appearing);
        Animation logo_in=AnimationUtils.loadAnimation(this, R.anim.logo_app);
        Animation logo_out=AnimationUtils.loadAnimation(this, R.anim.logo_dis);
        final Animation slide=AnimationUtils.loadAnimation(this, R.anim.go_next_out);
        
        long startTime=System.currentTimeMillis();
        logo1.startAnimation(logo_in);
        while(System.currentTimeMillis()-startTime<1000){}
        logo2.startAnimation(logo_out);
 
          start.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				Intent startGame=new Intent(StartActivity.this,SceneManager.class);
				startActivity(startGame);
				start.startAnimation(slide);
				instructions.startAnimation(slide);
				exit.startAnimation(slide);
				
				finish();
				
			}});
        
        instructions.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				Toast toast = Toast.makeText(getApplicationContext(), 
						   "Be brave,warrior!", Toast.LENGTH_SHORT); 
						toast.show(); 
						start.startAnimation(slide);
						instructions.startAnimation(slide);
						exit.startAnimation(slide);
				
			}});
        
        exit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				finish();				
			}});
    }
}
