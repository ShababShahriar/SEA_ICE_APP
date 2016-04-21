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
    private String timeOfRecording;
    private int internalTableId;

    public int getInternalTableId() {
        return internalTableId;
    }

    public void setInternalTableId(int internalTableId) {
        this.internalTableId = internalTableId;
    }



    @Override
    public String toString() {
        return "CollectedData{" +
                "image=" + Arrays.toString(image) +
                ", audio=" + Arrays.toString(audio) +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", timeOfRecording='" + timeOfRecording + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CollectedData)) return false;

        CollectedData that = (CollectedData) o;

        if (!Arrays.equals(getImage(), that.getImage())) return false;
        if (!Arrays.equals(getAudio(), that.getAudio())) return false;
        if (!getLatitude().equals(that.getLatitude())) return false;
        if (!getLongitude().equals(that.getLongitude())) return false;
        if (!getTimeOfRecording().equals(that.getTimeOfRecording())) return false;
        return getUserId().equals(that.getUserId());

    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(getImage());
        result = 31 * result + Arrays.hashCode(getAudio());
        result = 31 * result + getLatitude().hashCode();
        result = 31 * result + getLongitude().hashCode();
        result = 31 * result + getTimeOfRecording().hashCode();
        result = 31 * result + getUserId().hashCode();
        return result;
    }

    public String getUserId() {

        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public CollectedData(byte[] image, byte[] audio, String latitude, String longitude, String timeOfRecording, String userId) {

        this.image = image;
        this.audio = audio;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeOfRecording = timeOfRecording;
        this.userId = userId;
    }

    private String userId;

    public String getTimeOfRecording() {
        return timeOfRecording;
    }

    public void setTimeOfRecording(String timeOfRecording) {
        this.timeOfRecording = timeOfRecording;
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


}
