package thedevers.magickarp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by Frank on 25/08/2016.
 */
public class HistoryActivity extends Activity{

    ListView listView;
    SQLiteDatabase sqLiteDatabase;
    DataHelper dataHelper;
    Cursor cursor;
    ListDataAdapter listDataAdapter;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_history);

        //create a list view for the listing
        listView = (ListView) findViewById(R.id.list_view);
        listDataAdapter = new ListDataAdapter(getApplicationContext(), R.layout.row_layout);
        listView.setAdapter(listDataAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);

                DataProvider dataProvider = (DataProvider) listView.getAdapter().getItem(position);
                int value = dataProvider.getId();
                Log.i("GetID", "ID: "+value+" ,thisID: " + id);

                intent.putExtra("idd", Integer.toString(value));
                startActivity(intent);

            }
        });

        //get data from database
        dataHelper = new DataHelper(getApplicationContext());
        sqLiteDatabase = dataHelper.getReadableDatabase();
        cursor = dataHelper.getInformation(sqLiteDatabase);

        Log.i("Oncreate", "Cursor if" + cursor.moveToFirst());
        if(cursor.moveToFirst()){
            do{
                int id, ping;
                String type;
                Long time;
                double uploadSpeed, downloadSpeed, latitude, longitude;
                id = cursor.getInt(0);
                type = cursor.getString(1);
                time = cursor.getLong(2);
                downloadSpeed = cursor.getDouble(3);
                uploadSpeed = cursor.getDouble(4);
                ping = cursor.getInt(5);
                latitude = cursor.getDouble(6);
                longitude = cursor.getDouble(7);

                Log.i("Do", id + type + latitude + longitude);

                DataProvider dataProvider = new DataProvider(id, type, time, downloadSpeed, uploadSpeed, ping, latitude, longitude);
                listDataAdapter.add(dataProvider);
            }
            while (cursor.moveToNext());
        }
        Log.i("Oncreate", "Cursor end if");
    }


    //Navigation Links
    public void startTest(View v){
        Log.i("startTest", "clicked");
        Intent i = new Intent(HistoryActivity.this, TestingActivity.class);
        startActivity(i);
    }

    public void viewHistory(View v){
        Log.i("viewHistory", "clicked");
        Intent i = new Intent(HistoryActivity.this, HistoryActivity.class);
        startActivity(i);
    }

    public void viewTop5(View v){
        Log.i("viewTop5", "clicked");
        Intent i = new Intent(HistoryActivity.this, Top5.class);
        startActivity(i);
    }

}
