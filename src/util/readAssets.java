package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import utilInter.readAssetsInter;
import android.content.Context;
import android.util.Log;

public class readAssets implements readAssetsInter{

	public String getFromAssets(String fileName,Context context) {
        try {
        	//Log.v("log:get asset","step 1");
        	InputStreamReader inputReader = new InputStreamReader( context.getAssets().open(fileName) );
        	//Log.v("log:get asset","step 2");
        	BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line+"\n";
            return Result;
        } catch (Exception e) {
            Log.v("log:exception of get asset",e.getMessage());
        }
        return null;
	}
}
