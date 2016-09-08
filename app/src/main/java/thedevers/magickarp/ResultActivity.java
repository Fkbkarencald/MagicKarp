package thedevers.magickarp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;

public class ResultActivity extends Activity {
    TextView displayTime, displayType, displayPing, displayDownload, displayUpload,
                displayLatitude, displayLongitude;

    DataHelper dataHelper;
    SQLiteDatabase sqLiteDatabase;
    String id;
    private LatLng latLng;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        id = intent.getStringExtra("idd");
        Log.i("idd3", id);

        displayTime = (TextView) findViewById(R.id.resultDate);
        displayType = (TextView) findViewById(R.id.resultType);
        displayPing = (TextView) findViewById(R.id.resultPing);
        displayDownload = (TextView) findViewById(R.id.resultDownload);
        displayUpload = (TextView) findViewById(R.id.resultUpload);

        searchId();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ResultMap resultMap = new ResultMap();
        Bundle currentLocation = new Bundle();
        currentLocation.putDoubleArray("location", new double[]{latitude, longitude});
        resultMap.setArguments(currentLocation);
        fragmentTransaction.add(R.id.resultMap, resultMap);
        fragmentTransaction.commit();
    }

    public void startTest(View v){
        Log.i("startTest", "clicked");
        Intent i = new Intent(this, TestingActivity.class);
        startActivity(i);
    }

    public void viewHistory(View v){
        Log.i("viewHistory", "clicked");
        Intent i = new Intent(this, HistoryActivity.class);
        startActivity(i);
    }

    public void viewTop5(View v){
        Log.i("viewTop5", "clicked");
        Intent i = new Intent(this, Top5.class);
        startActivity(i);
    }

    private void searchId(){
        dataHelper = new DataHelper(getApplicationContext());
        sqLiteDatabase = dataHelper.getReadableDatabase();
        Cursor cursor = dataHelper.getSingleInformation(id, sqLiteDatabase);
        if(cursor.moveToFirst()){
            int id, ping;
            String type;
            Long time;
            double uploadSpeed, downloadSpeed;
            id = cursor.getInt(0);
            type = cursor.getString(1);
            time = cursor.getLong(2);
            downloadSpeed = cursor.getDouble(3);
            uploadSpeed = cursor.getDouble(4);
            ping = cursor.getInt(5);
            latitude = cursor.getDouble(6);
            longitude = cursor.getDouble(7);

            latLng = new LatLng(latitude, longitude);

            displayType.setText(type);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd\n HH:mm:ss");
            String formattedDate = dateFormat.format(time);
            displayTime.setText(formattedDate);
            displayPing.setText(Integer.toString(ping));
            displayDownload.setText(Double.toString(downloadSpeed));
            displayUpload.setText(Double.toString(uploadSpeed));
        }
    }
}
