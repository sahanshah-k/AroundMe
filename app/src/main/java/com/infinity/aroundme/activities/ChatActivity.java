package com.infinity.aroundme.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.infinity.aroundme.R;
import com.infinity.aroundme.domain.User;
import com.infinity.aroundme.service.impl.FireStoreImpl;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    ImageButton sendButton;
    EditText messageSend;
    User rec_user, send_user;
    FireStoreImpl fireStore;




    CircleImageView circleImageViewChat;
    TextView usernameChat;



    @Override
    public void onBackPressed(){
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.ChatTheme);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chat_activity);

        rec_user = (User)getIntent().getSerializableExtra("rec_user");
        send_user = (User)getIntent().getSerializableExtra("send_user");

        fireStore = new FireStoreImpl(ChatActivity.this);

        sendButton = findViewById(R.id.button_chatbox_send);
        messageSend = findViewById(R.id.edittext_chatbox);

        circleImageViewChat = findViewById(R.id.profile_image_chat);
        usernameChat = findViewById(R.id.display_name_chat);

        usernameChat.setText(rec_user.getDisplayName());

        if (rec_user.getImageUrl().equalsIgnoreCase("default")) {
            circleImageViewChat.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(ChatActivity.this).load(rec_user.getImageUrl()).into(circleImageViewChat);
        }


        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        fireStore.listMessage(send_user, rec_user.getUsername());

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageSend.getText().toString();
                if (!message.equals("")) {
                    fireStore.sendMessage(send_user.getUsername(), rec_user.getUsername(), message);
                }
                else {
                    Toast.makeText(getApplicationContext(), "You can't send empty message!", Toast.LENGTH_SHORT).show();
                }
                messageSend.setText("");
            }
        });
    }
}
