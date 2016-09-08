package thedevers.magickarp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

/**
 * Created by Frank on 25/08/2016.
 */
public class Top5 extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_top5);
    }

    public void startTest(View v){
        Log.i("startTest", "clicked");
        Intent i = new Intent(Top5.this, TestingActivity.class);
        startActivity(i);
    }

    public void viewHistory(View v){
        Log.i("startTest", "clicked");
        Intent i = new Intent(Top5.this, HistoryActivity.class);
        startActivity(i);
    }

    public void viewTop5(View v){
        Log.i("startTest", "clicked");
        Intent i = new Intent(Top5.this, Top5.class);
        startActivity(i);
    }
}
