package com.firebaseio.geofriend.geofriend;

public class User {

    private String id;
    private String fname ;
    private String lname;
    private String pic;

    public User(){

    }

    public User(String id, String fname, String lname, String pic){
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.pic = pic;
    }

    public String getId(){
        return id;
    }

    public String getFName(){
        return fname;
    }

    public String getLName(){
        return lname;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setFName(String fname){
        this.fname = fname;
    }

    public void getLName(String lname){
        this.lname = lname;
    }

    public String getPic(){
        return pic;
    }

    public void setPic(String pic){
        this.pic = pic;
    }
}
