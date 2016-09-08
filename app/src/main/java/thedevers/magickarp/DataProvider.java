package thedevers.magickarp;

/**
 * Created by Frank on 1/09/2016.
 */
public class DataProvider {

    private int id;
    private String type;
    private long time;
    private double downloadSpeed;
    private double uploadSpeed;
    private int ping;
    private double latitude;
    private double longitude;

    public DataProvider(int id, String type, long time, double downloadSpeed, double uploadSpeed, int ping, double latitude, double longitude) {
        this.id = id;
        this.type = type;
        this.time = time;
        this.downloadSpeed = downloadSpeed;
        this.uploadSpeed = uploadSpeed;
        this.ping = ping;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void setPing(int ping) {
        this.ping = ping;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Long getTime() {
        return time;
    }

    public double getDownloadSpeed() {
        return downloadSpeed;
    }

    public double getUploadSpeed() {
        return uploadSpeed;
    }

    public int getPing() {
        return ping;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

