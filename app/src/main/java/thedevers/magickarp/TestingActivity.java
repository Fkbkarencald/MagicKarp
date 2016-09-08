package thedevers.magickarp;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.GpsSatellite;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by Frank on 24/08/2016.
 */
public class TestingActivity extends Activity {

    private TextView textView, downloadView, uploadView, pingView;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private Location locate;
    private LocationService locationService = null;
    private boolean locationServiceBound = false;

    private String type;
    private double download;
    private double upload;
    private int ping;
    private double latitude;
    private double longitude;
    private long timeDate;

    private Context context;
    private DataHelper dataHelper;
    private SQLiteDatabase sqLiteDatabase;

    private boolean added;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to MySimpleService, cast the IBinder and get MySimpleService
            //instance
            LocationService.MyBinder binder = (LocationService.MyBinder) service;
            locationService = binder.getService();
            locationServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            locationServiceBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_testing);

        type = "Wifi";
        textView = (TextView) findViewById(R.id.locationText);
        downloadView = (TextView) findViewById(R.id.downloadVar);
        uploadView = (TextView) findViewById(R.id.uploadVar);
        pingView = (TextView) findViewById(R.id.pingVar);

        added = false;

        setTxtLat();

        //generate random download speed 0-100Mbps
        Random random = new Random();
        int whole = random.nextInt(100);
        int fract = random.nextInt(100);
        download = Double.parseDouble(whole + "." + fract);

        //generate random upload speed 0-10Mbps
        int whole1 = random.nextInt(10);
        int fract1 = random.nextInt(100);
        upload = Double.parseDouble(whole1 + "." + fract1);

        //generate random ping 0-120ms
        ping = random.nextInt(120);

        //set texts
        downloadView.setText(download + "");
        uploadView.setText(upload + "");
        pingView.setText(ping + "");

        /** Defines callbacks for service binding, passed to bindService() */


/*
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locate = location;
                String locationText = "\n " + location.getLatitude() + "\n " + location.getLongitude();
                textView.setText(locationText);
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                if(!added){
                    Log.i("Called From", "onLocationChanged");
                    AddData();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
                requestLocationUpdate();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.i("GPS", "Disabled");
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
*/
        getLocation();
    }

/*
    private void requestLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getLocation();
        }
        locationManager.requestLocationUpdates("gps", 0, 0, locationListener);
        Log.i("Location", "Requested");
        getLocation();
    }
    */

    public void startTest(View v) {
        Log.i("startTest", "clicked");
        Intent i = new Intent(TestingActivity.this, TestingActivity.class);
        startActivity(i);
    }

    public void viewHistory(View v) {
        Log.i("viewHistory", "clicked");
        Intent i = new Intent(TestingActivity.this, HistoryActivity.class);
        startActivity(i);
    }

    public void viewTop5(View v) {
        Log.i("viewTop5", "clicked");
        Intent i = new Intent(TestingActivity.this, Top5.class);
        startActivity(i);
    }

    public void setTxtLat() {
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        ((TextView) findViewById(R.id.location)).setText(currentDateTimeString);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.i("inside", "onRequestPermissionsResult");
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("Permission", "Granted");
                    //getLocation();
                    startLocationService();
                }
        }
    }

    private void startLocationService() {
        startService(new Intent(getBaseContext(), LocationService.class));
        // Bind to MySimpleService
        Intent intent = new Intent(this, LocationService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private void stopLocationService() {
        if (locationServiceBound) {
            unbindService(mConnection);
            locationServiceBound = false;
        }
        stopService(new Intent(getBaseContext(), LocationService.class));
    }

    public void getLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.i("inside", "build.Version");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.i("inside", "Request Permissions");
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
                return;
            } else {
                startLocationService();
            }
/*
            locate = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(locate != null){
                latitude = locate.getLatitude();
                longitude = locate.getLongitude();
                Log.i("Called From", "Inside");
                AddData();
                String locationText = "\n " + latitude + "\n " + longitude;
                textView.setText(locationText);
            }
            else {
                requestLocationUpdate();
            }
            */
        }
     /*   else {
            locate = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(locate != null){
                latitude = locate.getLatitude();
                longitude = locate.getLongitude();
                Log.i("Called From", "Outside");
                AddData();
                String locationText = "\n " + latitude + "\n " + longitude;
                textView.setText(locationText);
            }
            else {
                requestLocationUpdate();
            }

        } */
    }

    public void setLocation(Location locate) {
        latitude = locate.getLatitude();
        longitude = locate.getLongitude();
        String loc = latitude + ", " + longitude;
        Log.i("Location", loc);
    }

    public void AddData() {
        Calendar c = Calendar.getInstance();
        timeDate = c.getTimeInMillis();
        Log.i("time", timeDate + "");
        dataHelper = new DataHelper(this);
        sqLiteDatabase = dataHelper.getWritableDatabase();

        dataHelper.addInformation(type, timeDate, download, upload, ping, latitude, longitude, sqLiteDatabase);
        Toast.makeText(getBaseContext(), "data saved", Toast.LENGTH_LONG).show();
        dataHelper.close();
        added = true;
        stopLocationService();
    }
}
