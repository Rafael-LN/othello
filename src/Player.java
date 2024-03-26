package src;

import java.io.Serializable;

public class Player implements Serializable {
    private String nickname;
    private String password;
    private String nationality;
    private int age;
    private String photoUrl;

    public Player(String nickname, String password, String nationality, int age, String photoUrl) {
        this.nickname = nickname;
        this.password = password;
        this.nationality = nationality;
        this.age = age;
        this.photoUrl = photoUrl;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    // toString method to print player information for testing
}
