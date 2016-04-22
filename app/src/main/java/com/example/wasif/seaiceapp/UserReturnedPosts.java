package com.example.wasif.seaiceapp;

import java.util.Arrays;

/**
 * Created by wasif on 4/23/16.
 */
public class UserReturnedPosts {
    int postId;
    double latitude;
    double longitude;
    double distance;
    byte[] image;
    byte[] audio;
    String time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserReturnedPosts)) return false;

        UserReturnedPosts that = (UserReturnedPosts) o;

        if (getPostId() != that.getPostId()) return false;
        if (Double.compare(that.getLatitude(), getLatitude()) != 0) return false;
        if (Double.compare(that.getLongitude(), getLongitude()) != 0) return false;
        if (Double.compare(that.getDistance(), getDistance()) != 0) return false;
        if (!Arrays.equals(getImage(), that.getImage())) return false;
        if (!Arrays.equals(getAudio(), that.getAudio())) return false;
        if (!getTime().equals(that.getTime())) return false;
        if (!getUserId().equals(that.getUserId())) return false;
        return !(getInternalDatabaseId() != null ? !getInternalDatabaseId().equals(that.getInternalDatabaseId()) : that.getInternalDatabaseId() != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getPostId();
        temp = Double.doubleToLongBits(getLatitude());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getLongitude());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getDistance());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + Arrays.hashCode(getImage());
        result = 31 * result + Arrays.hashCode(getAudio());
        result = 31 * result + getTime().hashCode();
        result = 31 * result + getUserId().hashCode();
        result = 31 * result + (getInternalDatabaseId() != null ? getInternalDatabaseId().hashCode() : 0);
        return result;
    }

    public double getDistance() {

        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInternalDatabaseId() {
        return internalDatabaseId;
    }

    public void setInternalDatabaseId(String internalDatabaseId) {
        this.internalDatabaseId = internalDatabaseId;
    }

    String userId;
    String internalDatabaseId;

    @Override
    public String toString() {
        return "UserReturnedPosts{" +
                "postId=" + postId +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", distance=" + distance +
                ", image=" + Arrays.toString(image) +
                ", audio=" + Arrays.toString(audio) +
                ", time='" + time + '\'' +
                ", userId='" + userId + '\'' +
                ", internalDatabaseId='" + internalDatabaseId + '\'' +
                '}';
    }

    public UserReturnedPosts(int postId, double latitude, double longitude, double distance, byte[] image, String time, byte[] audio, String userId) {
        this.postId = postId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.image = image;
        this.time = time;
        this.audio = audio;
        this.userId = userId;
    }
}
