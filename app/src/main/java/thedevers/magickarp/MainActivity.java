package thedevers.magickarp;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        checkStatus();
        ckeckGpsStatus();


    }

   private void ckeckGpsStatus(){
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
           Log.i("inside", "build.Version");
           if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                   && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
               Log.i("inside", "Request Permissions");
               requestPermissions(new String[]{
                       android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION,
                       Manifest.permission.INTERNET
               }, 10);
               return;
           }
       }

       LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
       if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
           turnOnGPS();
       }

   }

    private void turnOnGPS(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    private void checkStatus(){
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            // fetch data
            Toast.makeText(this, "CONNECTED", Toast.LENGTH_LONG).show();
            Log.d("Connected?","YES");
        } else {
            // display error
            Log.d("Connected?","NO");
            Toast.makeText(this, "Enabling Wifi", Toast.LENGTH_LONG).show();
            enableWiFi();
        }
    }

    private void enableWiFi(){
        String service = Context.WIFI_SERVICE;
        Log.d("WIFI_SERVICE",service);
        WifiManager wifi = (WifiManager) getSystemService(service);
        if(!wifi.isWifiEnabled()){
            if(wifi.getWifiState() != WifiManager.WIFI_STATE_ENABLING){
                wifi.setWifiEnabled(true);
                Toast.makeText(this, "Wifi Enabled", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void startTest(View v){
        Log.i("startTest", "clicked");
        Intent i = new Intent(MainActivity.this, TestingActivity.class);
        startActivity(i);
    }

    public void viewHistory(View v){
        Log.i("startTest", "clicked");
        Intent i = new Intent(MainActivity.this, HistoryActivity.class);
        startActivity(i);
    }

    public void viewTop5(View v){
        Log.i("startTest", "clicked");
        Intent i = new Intent(MainActivity.this, Top5.class);
        startActivity(i);
    }
}
