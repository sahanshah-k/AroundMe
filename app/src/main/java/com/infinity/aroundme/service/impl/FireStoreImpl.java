package com.infinity.aroundme.service.impl;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.infinity.aroundme.domain.User;
import com.infinity.aroundme.service.FireStore;
import com.infinity.aroundme.utilities.Utils;


public class FireStoreImpl implements FireStore {

    private static final String USER_COLLECTION = "user";

    private Context context;

    FirebaseFirestore db;

    String returnString = "";

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
                    Snackbar.make(rootView, "You are ready to roam!", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void upsertUser(User user) {
        db.collection(USER_COLLECTION).document(user.getUsername())
                .set(user);
    }

}
