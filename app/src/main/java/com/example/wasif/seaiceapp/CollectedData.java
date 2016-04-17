package com.example.wasif.seaiceapp;

import java.util.Arrays;

/**
 * Created by wasif on 4/17/16.
 */
public class CollectedData {
    private byte[] image;
    private byte[] audio;
    private String latitude;
    private String longitude;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CollectedData)) return false;

        CollectedData that = (CollectedData) o;

        if (!Arrays.equals(getImage(), that.getImage())) return false;
        if (!Arrays.equals(getAudio(), that.getAudio())) return false;
        if (!getLatitude().equals(that.getLatitude())) return false;
        return getLongitude().equals(that.getLongitude());

    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(getImage());
        result = 31 * result + Arrays.hashCode(getAudio());
        result = 31 * result + getLatitude().hashCode();
        result = 31 * result + getLongitude().hashCode();
        return result;
    }

    public byte[] getImage() {

        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getAudio() {
        return audio;
    }

    public void setAudio(byte[] audio) {
        this.audio = audio;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "CollectedData{" +
                "image=" + Arrays.toString(image) +
                ", audio=" + Arrays.toString(audio) +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }

    public CollectedData(byte[] image, byte[] audio, String latitude, String longitude) {

        this.image = image;
        this.audio = audio;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
