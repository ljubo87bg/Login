package com.ljubo.application;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ljubo.application.model.users.User;
import com.ljubo.application.model.users.UsersManager;

public class MainActivity extends AppCompatActivity {

    User user;
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences prefs = MainActivity.this.getSharedPreferences("Application", Context.MODE_PRIVATE);
        User user = UsersManager.getInstance(MainActivity.this).getUser(prefs.getString("currentUser", null));

        logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = MainActivity.this.getSharedPreferences("Application", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("logged_in", false);
                editor.commit();
                Intent intent= new Intent(MainActivity.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        UsersManager.getInstance(MainActivity.this).saveUsers(MainActivity.this);
        super.onDestroy();
    }
}
