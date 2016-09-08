package thedevers.magickarp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Frank on 1/09/2016.
 */
public class DataHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATA_TABLE_NAME = "DATA";
    private static final String DATA_TABLE_CREATE =
            "CREATE TABLE " + DATA_TABLE_NAME + " ("
                    + DataMc.DataMagicCarp.Id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + DataMc.DataMagicCarp.Type + " TEXT NOT NULL, "
                    + DataMc.DataMagicCarp.Time + " REAL NOT NULL, "
                    + DataMc.DataMagicCarp.DownloadSpeed + " REAL NOT NULL, "
                    + DataMc.DataMagicCarp.UploadSpeed + " REAL NOT NULL, "
                    + DataMc.DataMagicCarp.Ping + " INTEGER NOT NULL, "
                    + DataMc.DataMagicCarp.Latitude + " REAL NOT NULL, "
                    + DataMc.DataMagicCarp.Longitude + " REAL NOT NULL"
                    + ")";

    DataHelper(Context context){
        super(context, DATA_TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("Creating Table...", "yeah");
        db.execSQL(DATA_TABLE_CREATE);
        Log.e("DATABASE OPERATIONS", "Table Created...");
    }

    public void addInformation(String type, long time, double downloadSpeed, double uploadSpeed,
                               int ping, double latitude, double longitude, SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataMc.DataMagicCarp.Type, type);
        contentValues.put(DataMc.DataMagicCarp.Time, time);
        contentValues.put(DataMc.DataMagicCarp.DownloadSpeed, downloadSpeed);
        contentValues.put(DataMc.DataMagicCarp.UploadSpeed, uploadSpeed);
        contentValues.put(DataMc.DataMagicCarp.Ping, ping);
        contentValues.put(DataMc.DataMagicCarp.Latitude, latitude);
        contentValues.put(DataMc.DataMagicCarp.Longitude, longitude);
        db.insert(DataMc.DataMagicCarp.TABLE_NAME, null, contentValues);
        Log.e("DATABASE OPERATIONS", "Row is inserted...");
    }

    public Cursor getInformation(SQLiteDatabase db){
        Cursor cursor;
        String[] projections = {DataMc.DataMagicCarp.Id, DataMc.DataMagicCarp.Type, DataMc.DataMagicCarp.Time,
                DataMc.DataMagicCarp.DownloadSpeed, DataMc.DataMagicCarp.UploadSpeed, DataMc.DataMagicCarp.Ping,
                DataMc.DataMagicCarp.Latitude, DataMc.DataMagicCarp.Longitude};
        cursor = db.query(DataMc.DataMagicCarp.TABLE_NAME, projections, null, null, null, null, null);
        return cursor;
    }

    public Cursor getSingleInformation(String id, SQLiteDatabase sqLiteDatabase){

        String[] projections = {DataMc.DataMagicCarp.Id, DataMc.DataMagicCarp.Type, DataMc.DataMagicCarp.Time,
                DataMc.DataMagicCarp.DownloadSpeed, DataMc.DataMagicCarp.UploadSpeed, DataMc.DataMagicCarp.Ping,
                DataMc.DataMagicCarp.Latitude, DataMc.DataMagicCarp.Longitude};
        String selection = DataMc.DataMagicCarp.Id + " LIKE ?";
        String[] selection_args = {(id)};
        return sqLiteDatabase.query(DataMc.DataMagicCarp.TABLE_NAME, projections, selection, selection_args, null, null, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
