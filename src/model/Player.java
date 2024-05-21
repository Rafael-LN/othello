package model;

import java.io.Serializable;
import java.util.Base64;

public class Player implements Serializable {
    private String nickname;
    private String password;
    private String nationality;
    private int age;
    private byte[] photoData;
    private int wins = 0;
    private int losses = 0;
    private long timeSpent; // Time spent in milliseconds
    private long startTime; // Start time
    private long endTime; // End time

    public Player(String nickname, String password, String nationality, int age, byte[] photoData) {
        this.nickname = nickname;
        this.password = password;
        this.nationality = nationality;
        this.age = age;
        this.photoData = photoData;
    }

    public Player(String nickname, String password){
        this.nickname = nickname;
        this.password = password;
    }

    // Getters and setters
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public byte[] getPhotoData() {
        return photoData;
    }

    public void setPhotoData(byte[] photoData) {
        this.photoData = photoData;
    }

    // Method to get Base64-encoded photo data
    public String getBase64Photo() {
        return Base64.getEncoder().encodeToString(photoData);
    }

    // Method to set photo data from Base64-encoded string
    public void setBase64Photo(String base64Photo) {
        this.photoData = Base64.getDecoder().decode(base64Photo);
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getTimeSpent() {
        return endTime - startTime;
    }

    public void setTimeSpent(long timeSpent) {
        this.timeSpent = timeSpent;
    }

    @Override
    public String toString() {
        return "Player {" +
                "nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", nationality='" + nationality + '\'' +
                ", age=" + age + '\'' +
                ", photo='" + getBase64Photo()+ '\'' +
                ", wins=" + wins + '\'' +
                ", losses=" + losses + '\'' +
                ", timeSpent=" + timeSpent + '\'' +
                '}';
    }
}
