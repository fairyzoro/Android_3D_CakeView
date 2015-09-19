package com.ra.cakepercent;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		float screenHeight= getWindowManager().getDefaultDisplay().getHeight();
		float screenWidth = getWindowManager().getDefaultDisplay().getWidth();
		float scr_density = MainActivity.this.getResources().getDisplayMetrics().density;
		CakeView cakeview=(CakeView)findViewById(R.id.cakeview);
		String[] names = new String[]{"apple","banana","orange","grape","pear"}; 
		int[] nums = new int[]{2,3,1,5,20};
		cakeview.init( screenWidth, (screenHeight-266*scr_density),names,nums);
	}
}
