package com.example.wasif.seaiceapp;

/**
 * Created by wasif on 4/23/16.
 */
public class CustomMarker {

    @Override
    public String toString() {
        return "CustomMarker{" +
                "markerIdInSqlite=" + markerIdInSqlite +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", markerType='" + markerType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomMarker)) return false;

        CustomMarker that = (CustomMarker) o;

        if (getMarkerIdInSqlite() != that.getMarkerIdInSqlite()) return false;
        if (Double.compare(that.getLatitude(), getLatitude()) != 0) return false;
        if (Double.compare(that.getLongitude(), getLongitude()) != 0) return false;
        return getMarkerType().equals(that.getMarkerType());

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getMarkerIdInSqlite();
        temp = Double.doubleToLongBits(getLatitude());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getLongitude());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getMarkerType().hashCode();
        return result;
    }

    public int getMarkerIdInSqlite() {

        return markerIdInSqlite;
    }

    public void setMarkerIdInSqlite(int markerIdInSqlite) {
        this.markerIdInSqlite = markerIdInSqlite;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getMarkerType() {
        return markerType;
    }

    public void setMarkerType(String markerType) {
        this.markerType = markerType;
    }

    public CustomMarker(double latitude, double longitude, String markerType) {


        this.latitude = latitude;
        this.longitude = longitude;
        this.markerType = markerType;
    }

    private int markerIdInSqlite;
    private double latitude;
    private double longitude;
    private String markerType;

}
