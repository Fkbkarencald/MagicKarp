package thedevers.magickarp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 1/09/2016.
 */
public class ListDataAdapter extends ArrayAdapter{
    List list = new ArrayList();
    int ID;

    public ListDataAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class LayoutHandler{
        TextView ID, TYPE, TIME, DOWNLOAD, UPLOAD, PING, LATITUDE, LONGITUDE;
    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        LayoutHandler layoutHandler;

        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout, parent, false);
            layoutHandler = new LayoutHandler();
            layoutHandler.ID = (TextView)row.findViewById(R.id.rowId);
            layoutHandler.TYPE = (TextView)row.findViewById(R.id.textType);
            layoutHandler.TIME = (TextView)row.findViewById(R.id.textTime);
            layoutHandler.DOWNLOAD = (TextView)row.findViewById(R.id.textDownload);
            layoutHandler.UPLOAD = (TextView)row.findViewById(R.id.textUpload);
            layoutHandler.PING = (TextView)row.findViewById(R.id.textPing);
            row.setTag(layoutHandler);
        }
        else {
            layoutHandler = (LayoutHandler)row.getTag();
        }
        final DataProvider dataProvider = (DataProvider) this.getItem(position);
        layoutHandler.ID.setText(Integer.toString(dataProvider.getId()));
        layoutHandler.TYPE.setText(dataProvider.getType());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd\n HH:mm:ss");
        String formattedDate = dateFormat.format(dataProvider.getTime());
        layoutHandler.TIME.setText(formattedDate);
        layoutHandler.DOWNLOAD.setText(Double.toString(dataProvider.getDownloadSpeed()));
        layoutHandler.UPLOAD.setText(Double.toString(dataProvider.getUploadSpeed()));
        layoutHandler.PING.setText(Integer.toString(dataProvider.getPing()));
        ID = dataProvider.getId();
        Log.i("DataProvider", "ID is set to: " + ID);
        Log.i(""+dataProvider.getLatitude(), ""+dataProvider.getLongitude());
        return row;
    }

    public int getID() {
        return ID;
    }
}
