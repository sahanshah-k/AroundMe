package com.infinity.aroundme.utilities;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Utils {

    Context context;

    View view;

    public Utils(Context context) {
        this.context = context;
        view = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);

    }

    public void hideKeyboard() {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
