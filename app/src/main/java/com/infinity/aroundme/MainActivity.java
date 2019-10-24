package com.infinity.aroundme;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.infinity.aroundme.domain.User;
import com.infinity.aroundme.service.impl.FireStoreImpl;


public class MainActivity extends AppCompatActivity {

    Button goButton;
    TextInputEditText displayNameEditText, usernameEditText;
    FireStoreImpl fireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        fireStore = new FireStoreImpl(MainActivity.this);

        goButton = findViewById(R.id.go);
        displayNameEditText = findViewById(R.id.display_name);
        usernameEditText = findViewById(R.id.username);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String displayNameString = displayNameEditText.getText().toString();
                final String usernameString = usernameEditText.getText().toString();
                if (displayNameString.isEmpty() || usernameString.isEmpty()) {
                    hideKeyboard();
                    Snackbar.make(view, "You got NULL data!", Snackbar.LENGTH_SHORT).show();
                }
                else {
                    User user = new User(displayNameString, usernameString);
                    fireStore.registerUser(user);
                }
            }
        });


    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
