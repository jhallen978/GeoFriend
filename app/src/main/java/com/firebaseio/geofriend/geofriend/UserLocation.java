package com.firebaseio.geofriend.geofriend;

import android.util.Log;

public class UserLocation {
    //vars
    public String id;
    public String fname;
    public String lname;
    public double lat;
    public double lon;
    public String pic;

    public UserLocation(){

    }

    //constructor
    public UserLocation(String id, String fname, String lname, double lat, double lon, String pic){
        this.id = id;
        this.fname= fname;
        this.lname=lname;
        this.lat=lat;
        this.lon=lon;
        this.pic=pic;
    }

    //get obj vars
    public String getId(){
        return id;
    }

    public String getFname(){
        return fname;
    }

    public String getLname(){
        return lname;
    }

    public double getLat(){
        return lat;
    }

    public double getLon(){
        return lon;
    }

    public String getPic(){
        return pic;
    }

    //set object vars
    public void setId(String id){
        this.id = id;
    }

    public void setFname(String fname){
        this.fname=fname;
    }

    public void setLname(String lname){
        this.lname=lname;
    }

    public void setLat(double lat){
        this.lat=lat;
    }

    public void setLon(double lon){
        this.lon=lon;
    }

    public void setPic(String pic){
        this.pic=pic;
    }

    public String print(){
        return "\nid: " + id +
               "\nfn: " + fname +
               "\nln: " + lname +
               "\nlat: " + lat +
               "\nlon: " + lon +
               "\npic:" + pic ;
    }
}
