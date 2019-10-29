package com.infinity.aroundme.utilities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.infinity.aroundme.R;
import com.infinity.aroundme.activities.ChatActivity;
import com.infinity.aroundme.domain.User;

import java.util.List;

public class DisplayUserAdapter extends RecyclerView.Adapter<DisplayUserAdapter.ViewHolder> {

    private Context context;
    private List<User> mUser;
    private User currentUser;

    public DisplayUserAdapter(Context context, List<User> user, User currentUser) {
        this.context = context;
        this.mUser = user;
        this.currentUser = currentUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return  new DisplayUserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User user = mUser.get(position);
        holder.display_username.setText(user.getDisplayName());
        if (user.getImageUrl().equalsIgnoreCase("default")) {
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(context).load(user.getImageUrl()).into(holder.profile_image);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("rec_user",user);
                intent.putExtra("send_user",currentUser);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView display_username;
        public ImageView profile_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            display_username = itemView.findViewById(R.id.display_username);
            profile_image = itemView.findViewById(R.id.profile_image);

        }


    }


}
