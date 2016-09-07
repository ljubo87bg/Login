package com.ljubo.application;

import android.content.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.ljubo.application.model.users.User;
import com.ljubo.application.model.users.UsersManager;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_REG_USER = 10;
    EditText usernameLoginET;
    EditText passwordLoginET;
    Button loginButton;
    Button registerButton;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameLoginET = (EditText) findViewById(R.id.user_login);
        passwordLoginET = (EditText) findViewById(R.id.pass_login);
        loginButton = (Button) findViewById(R.id.login_button);
        registerButton = (Button) findViewById(R.id.register_button_login);

        if(LoginActivity.this.getSharedPreferences("Application",Context.MODE_PRIVATE).getString("currentUser", null)!=null) {
            user = getCurrentUser(LoginActivity.this);
        }
        maintainLogin(LoginActivity.this);



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                usernameLoginET.setError(null);
                startActivityForResult(intent, REQUEST_REG_USER);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameLoginET.getText().toString();
                String password = passwordLoginET.getText().toString();

                if (username.isEmpty()) {
                    usernameLoginET.setError("Username is compulsory");
                    usernameLoginET.requestFocus();
                    return;
                }
                if (password.length() == 0) {
                    passwordLoginET.setError("Password is compulsory");
                    passwordLoginET.requestFocus();
                    return;
                }
                if (!UsersManager.getInstance(LoginActivity.this).validalteLogin(username, password)) {
                    usernameLoginET.setError("Invalid credentials");
                    usernameLoginET.setText("");
                    passwordLoginET.setText("");
                    usernameLoginET.requestFocus();
                    return;
                }

                SharedPreferences prefs = LoginActivity.this.getSharedPreferences("Application", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("logged_in", true);
                editor.putString("currentUser",username);
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_REG_USER){
            if(resultCode == RegisterActivity.RESULT_REG_SUCCESSFUL){
                String user = data.getStringExtra("username");
                String pass = data.getStringExtra("password");
                usernameLoginET.setText(user);
                passwordLoginET.setText(pass);
            }
        }
    }

    public void maintainLogin(Context activity){

        if (activity.getSharedPreferences("Application", Context.MODE_PRIVATE).getBoolean("logged_in", false)) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("loggedUser", UsersManager.getInstance(LoginActivity.this).getUser(usernameLoginET.getText().toString()));
            startActivity(intent);
            finish();
        }
    }

    public User getCurrentUser(Context activity){
        String username=activity.getSharedPreferences("Application", Context.MODE_PRIVATE).getString("currentUser", null);
        return UsersManager.getInstance(LoginActivity.this).getUser(username);
    }
}
