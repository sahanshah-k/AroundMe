package com.infinity.aroundme.service.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.infinity.aroundme.R;
import com.infinity.aroundme.activities.DisplayUsersActivity;
import com.infinity.aroundme.domain.Message;
import com.infinity.aroundme.domain.User;
import com.infinity.aroundme.service.FireStore;
import com.infinity.aroundme.utilities.DisplayUserAdapter;
import com.infinity.aroundme.utilities.MessageListAdapter;
import com.infinity.aroundme.utilities.Utils;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class FireStoreImpl implements FireStore {

    private static final String USER_COLLECTION = "user";
    private static final String MESSAGE_COLLECTION = "message";

    MessageListAdapter messageListAdapter;


    private Context context;
    FirebaseFirestore db;
    TextInputEditText search;
    RecyclerView recyclerView;




    private RecyclerView displayUsersRecyclerView;
    private DisplayUserAdapter displayUserAdapter;
    private List<User> userList;

    View rootView;

    public FireStoreImpl(Context context) {
        this.context = context;
        db = FirebaseFirestore.getInstance();
        rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
    }

    @Override
    public void registerUser(final User user) {

        DocumentReference docRef = db.collection(USER_COLLECTION).document(user.getUsername());

        final Utils utils = new Utils(context);

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    utils.hideKeyboard();
                    Snackbar.make(rootView, user.getUsername() + " is already here!", Snackbar.LENGTH_LONG).show();
                }
                else {
                    upsertUser(user);
                    utils.hideKeyboard();
                    //Snackbar.make(rootView, "You are ready to roam!", Snackbar.LENGTH_LONG).show();
                    Toast.makeText(context, "You are ready to roam!", Toast.LENGTH_SHORT).show();
                    Intent listUsersIntent = new Intent(context, DisplayUsersActivity.class);
                    listUsersIntent.putExtra("user",user);
                    context.startActivity(listUsersIntent);
                }
            }
        });
    }

    @Override
    public void upsertUser(final User user) {
        db.collection(USER_COLLECTION).document(user.getUsername())
                .set(user);
    }


        @Override
    public void listMessage(final User sender, String receiver) {






        recyclerView = rootView.findViewById(R.id.reyclerview_message_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        db.collection(MESSAGE_COLLECTION)
                .whereEqualTo("sender", sender.getUsername())
                .whereEqualTo("receiver", receiver)
                .orderBy("date", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        List<Message> messageList = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            if (doc.exists())
                                messageList.add(doc.toObject(Message.class));
                        }
                        Log.d(TAG, "Current cites in CA: " );
                        messageListAdapter  = new MessageListAdapter(context, messageList, sender);
                        recyclerView.setAdapter(messageListAdapter);

                    }
                });

    }


    @Override
    public void sendMessage(String sender, String receiver, String message) {
        Message messageObject = new Message(message, sender, receiver);

        String messageId;

        if (sender.compareTo(receiver) < 0) {
            messageId = sender + "~" + receiver;
        }
        else {
            messageId = receiver + "~" + sender;
        }

        db.collection(MESSAGE_COLLECTION).add(messageObject);
    }

    @Override
    public void displayUsers(final User currentUser) {
        displayUsersRecyclerView = rootView.findViewById(R.id.recycler_view_users);
        displayUsersRecyclerView.setHasFixedSize(true);
        displayUsersRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        userList = new ArrayList<>();

        db.collection(USER_COLLECTION)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        userList.clear();
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                User userObject = doc.toObject(User.class);
                                if (!currentUser.getUsername().equals(userObject.getUsername())) {
                                    userList.add(userObject);
                                }
                        }

                        displayUserAdapter = new DisplayUserAdapter(context, userList, currentUser);
                        displayUsersRecyclerView.setAdapter(displayUserAdapter);
                    }
                });

    }


}
