package com.ljubo.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ljubo.application.model.users.UsersManager;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameR;
    EditText passR;
    EditText confirmPassR;
    EditText emailR;
    EditText addressR;
    Button registerR;
    Button cancel;
    public static final int RESULT_REG_SUCCESSFUL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        usernameR = (EditText) findViewById(R.id.user_register);
        passR = (EditText) findViewById(R.id.pass_register);
        confirmPassR = (EditText) findViewById(R.id.pass_register2);
        emailR = (EditText) findViewById(R.id.email_register);
        addressR = (EditText) findViewById(R.id.address_register);
        registerR = (Button) findViewById(R.id.register_button_register);
        cancel = (Button) findViewById(R.id.cancel_button);


        registerR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameU = usernameR.getText().toString();
                String passU = passR.getText().toString();
                String confirmU = confirmPassR.getText().toString();
                String emailU = emailR.getText().toString();
                String address = addressR.getText().toString();
                String emailPattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

                if (usernameU.isEmpty()) {
                    usernameR.setError("Username is compulsory");
                    usernameR.requestFocus();
                    return;
                }
                if (passU.length() == 0) {
                    passR.setError("Password is compulsory");
                    passR.requestFocus();
                    return;
                }
                if (confirmU.length() == 0) {
                    confirmPassR.setError("Second password is compulsory");
                    confirmPassR.requestFocus();
                    return;
                }
                if (emailU.isEmpty()) {
                    emailR.setError("Email is compulsory");
                    emailR.requestFocus();
                    return;
                }

                if( !emailU.matches(emailPattern)){
                    emailR.setError("Invalid email");
                    emailR.requestFocus();
                    return;
                }


                if (!(passU.equals(confirmU))){
                    passR.setError("Passwords mismatch");
                    passR.setText("");
                    confirmPassR.setText("");
                    passR.requestFocus();
                    return;
                }

                if (UsersManager.getInstance(RegisterActivity.this).existsUser(usernameU)) {
                    usernameR.setError("User already exists");
                    usernameR.setText("");
                    usernameR.requestFocus();
                    return;
                }

                UsersManager.getInstance(RegisterActivity.this).registerUser(RegisterActivity.this, usernameU, passU, emailU, address);
                Intent intent = new Intent();
                intent.putExtra("username", usernameR.getText().toString());
                intent.putExtra("password", passR.getText().toString());
                setResult(RESULT_REG_SUCCESSFUL, intent);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
