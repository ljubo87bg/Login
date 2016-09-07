package com.ljubo.application.model.users;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.ljubo.application.RegisterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by ljubo on 9/7/2016.
 */
public class UsersManager {

    private static UsersManager ourInstance;
    HashMap<String, User> users;

    public static UsersManager getInstance(Activity activity) {
        if(ourInstance == null){
            ourInstance = new UsersManager(activity);
        }
        return ourInstance;
    }

    private UsersManager(Activity activity) {

        users = new HashMap<>();
        String json = activity.getSharedPreferences("Application", Context.MODE_PRIVATE).getString("registeredUsers", "no users");
        try {
            JSONArray arr = new JSONArray(json);
            for(int i = 0; i < arr.length(); i++){
                JSONObject obj = arr.getJSONObject(i);
                User user = new User(obj.getString("username"), obj.getString("password"), obj.getString("email"), obj.getString("address"));
                users.put(user.getUsername(), user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public boolean existsUser(String username) {
        return users.containsKey(username);
    }

    public void registerUser(Activity activity, String username, String pass1, String email, String address) {
        User user = new User(username, pass1,email, address);
        users.put(username, user);
        saveUsers(activity);
    }

    public boolean validalteLogin(String username, String password) {
        if (!existsUser(username)){
            return false;
        }
        if(!users.get(username).getPassword().equals(password)){
            return false;
        }
        return true;
    }


    public User getUser(String username){
        return users.get(username);
    }

    public void saveUsers(Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences("Application", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray jsonUsers = new JSONArray();
        try {
            for (User u : users.values()) {
                JSONObject jobj = new JSONObject();
                jobj.put("username", u.getUsername());
                jobj.put("password", u.getPassword());
                jobj.put("email", u.getEmail());
                jobj.put("address", u.getAddress());
                jsonUsers.put(jobj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        editor.putString("registeredUsers", jsonUsers.toString());
        editor.commit();
    }

}
