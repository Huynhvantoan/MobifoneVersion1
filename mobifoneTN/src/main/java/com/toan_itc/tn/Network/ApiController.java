package com.toan_itc.tn.Network;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by PC-07 on 7/17/2015.
 */
public class ApiController {
    public static String request_login(String email,String password) {
        String request ="";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            JSONArray array=new JSONArray();
            array.put(jsonObject);
            JSONObject mainObj = new JSONObject();
            mainObj.put("data", array);
            request=mainObj.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return request;
    }
    public static String request_register(String email,String username,String password) {
        String request ="";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("username", username);
            jsonObject.put("password", password);
            JSONArray array=new JSONArray();
            array.put(jsonObject);
            JSONObject mainObj = new JSONObject();
            mainObj.put("data", array);
            request=mainObj.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return request;
    }
    public static String request(String count,String start) {
        String request ="";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("pageCount", count);
            jsonObject.put("startIndex", start);
            request=jsonObject.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return request;
    }
    public static String check_token(String token,String userID) {
        String request ="";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", token);
            jsonObject.put("user_id", userID);
            request=jsonObject.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return request;
    }
    public static String request_profile(String userID) {
        String request ="";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("user_id", userID);
            JSONArray array=new JSONArray();
            array.put(jsonObject);
            JSONObject mainObj = new JSONObject();
            mainObj.put("data", array);
            request=mainObj.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return request;
    }
    public static String getlink_show(String id,String user_id,String token) {
        String request ="";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("show_id", id);
            jsonObject.put("user_id", user_id);
            jsonObject.put("token", token);
            request=jsonObject.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return request;
    }
    public static String request_getlink(String id,String user_id,String token) {
        String request ="";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("movie_id", id);
            jsonObject.put("user_id", user_id);
            jsonObject.put("token", token);
            request=jsonObject.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return request;
    }
    public static String bundle_getlink(String id,String user_id,String token) {
        String request ="";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("movie_id", id);
            jsonObject.put("user_id", user_id);
            jsonObject.put("token", token);
            request=jsonObject.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return request;
    }
    public static String show_getlink(String id,String user_id,String token) {
        String request ="";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("show_id", id);
            jsonObject.put("user_id", user_id);
            jsonObject.put("token", token);
            request=jsonObject.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return request;
    }
    public static String request_movies_details(String id) {
        String request ="";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("movie_id", id);
            request=jsonObject.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return request;
    }
    public static String request_show_details(String id) {
        String request ="";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("show_id", id);
            request=jsonObject.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return request;
    }
    public static String request_movies_related(int pageCount,String movie_id,int startIndex) {
        String request ="";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("movie_id", movie_id);
            jsonObject.put("pageCount", pageCount);
            jsonObject.put("startIndex", startIndex);
            request=jsonObject.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return request;
    }
    public static String request_search(String key,int pageCount,int startIndex) {
        String request ="";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("key", key);
            jsonObject.put("pageCount", pageCount);
            jsonObject.put("startIndex", startIndex);
            request=jsonObject.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return request;
    }
    public static String request_login_facebook(String id,String email,String name,String birthday,String sex) {
        String request ="";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id_social_network", id);
            jsonObject.put("email",email);
            jsonObject.put("fullname", name);
            jsonObject.put("birthday",birthday);
            jsonObject.put("gender",sex);
            jsonObject.put("type", "facebook");
            JSONArray array=new JSONArray();
            array.put(jsonObject);
            JSONObject mainObj = new JSONObject();
            mainObj.put("data", array);
            request=mainObj.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return request;
    }
    final public static String Version="Version";
    final public static int MENU1=1;
    final public static int MENU2=2;
    final public static int MENU3=3;
    final public static int MENU4=4;
    final public static int MENU5=5;
    final public static String Main="Main";
    final public static String UID="User_ID";
    final public static String APPTN="APPTN";
    final public static String User="UserName";
    final public static String Pass="Password";
    final public static String EXTRA_IMAGE = "EXTRA_IMAGE";
    final public static String EXTRA_LINK = "EXTRA_LINK";
    public static String Subtitle = "Subtitle";
    final public static String Pass_not_md5="Password_not_md5";
    final public static String Phone="Phone";
    final public static String Date_create="Date_create";
    final public static String Birthday="Birthday";
    final public static String fullname="fullname";
    final public static String Username="Username";
    final public static String Avatar="Avatar";
    final public static String Refresh="Refresh";
    final public static String Sex="Sex";
    final public static String PUSH_NOTIFICATION="PUSH_NOTIFICATION";
    final public static String PUSH_MESSAGE="PUSH_MESSAGE";
    final public static String PUSH_TITLE="PUSH_TITLE";
    final public static String PUSH_DATA="PUSH_DATA";
}
