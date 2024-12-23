package com.example.duda;


import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    int id;
    String name;
    String email;
    int password;
    String photo;
    int usersCategories_id;
    private boolean success;
    private String message;


    public User(int id, String name, String email, int password, String photo, int usersCategories_id, boolean success, String message) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.usersCategories_id = usersCategories_id;
        this.success = success;
        this.message = message;
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getUsersCategories_id() {
        return usersCategories_id;
    }

    public void setUsersCategories_id(int usersCategories_id) {
        this.usersCategories_id = usersCategories_id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}