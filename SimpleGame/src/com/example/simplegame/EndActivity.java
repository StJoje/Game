package com.example.simplegame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EndActivity extends Activity {
	
	AlertDialog.Builder ad;
	TextView result;
	Context context;
	Button b;
	ImageView ghost;
	AnimationDrawable animd;
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);    
        Log.d("End","OnCreate1");

        setContentView(R.layout.end_activity);
        
        Log.d("End","OnCreate");
        result=(TextView)findViewById(R.id.textView2);
        context=EndActivity.this;
        b=(Button)findViewById(R.id.startBtn);
        ghost=(ImageView)findViewById(R.id.ghost2);
      
       

        
        b.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				Intent startGame=new Intent(context,StartActivity.class);
				startActivity(startGame);
				finish();
			}});
        int scores=getIntent().getIntExtra("SCORES", 0);
        result.setTextSize(20);
        result.setText("Ваш результат:"+scores);    
    }
    
    public void onDestroy() {
        super.onDestroy();


    }
    
}
