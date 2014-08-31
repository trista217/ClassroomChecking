package com.example.action;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import domain.classroom;
import service.dealwithClassroom;

public class MainActivity extends ActionBarActivity {
 
	private TextView date;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Btn_click(View view)
    {
    	String startdate="2014-09-24";
    	classroom clazzroom=new classroom(startdate);
   
    	dealwithClassroom dealwithclassroom=new dealwithClassroom();
    	boolean result=dealwithclassroom.DealwithClassroom(clazzroom);
    }
}
