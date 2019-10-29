package com.infinity.aroundme.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.infinity.aroundme.R;
import com.infinity.aroundme.domain.User;
import com.infinity.aroundme.service.impl.FireStoreImpl;

public class DisplayUsersActivity extends AppCompatActivity {

    private FireStoreImpl fireStore;

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.display_users);

        fireStore = new FireStoreImpl(DisplayUsersActivity.this);
        User user = (User)getIntent().getSerializableExtra("user");

        fireStore.displayUsers(user);

    }
}
