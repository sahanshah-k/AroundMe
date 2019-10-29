package com.infinity.aroundme.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.aroundme.R;
import com.infinity.aroundme.domain.Message;
import com.infinity.aroundme.domain.User;

import java.text.DateFormat;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;


    private Context mContext;
    private List<Message> mMessageList;
    private User mUser;

    public MessageListAdapter(Context context, List<Message> messageList, User user) {
        mContext = context;
        mMessageList = messageList;
        mUser = user;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = (Message) mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }
    @Override
    public int getItemViewType(int position) {
        Message message = (Message) mMessageList.get(position);

        if (message.getSender().equals(mUser.getUsername())) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }



    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView profileImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_message_body_rec);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time_rec);
            nameText = (TextView) itemView.findViewById(R.id.text_message_name_rec);
            profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile_rec);
        }

        void bind(Message message) {
            messageText.setText(message.getMessageText());

            // Format the stored timestamp into a readable String using method.
            timeText.setText(message.getDate().toString());
            nameText.setText(message.getReceiver());

            // Insert the profile image from the URL into the ImageView.
           // Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
        }
    }


    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        }

        void bind(Message message) {
            messageText.setText(message.getMessageText());
            DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT);

            // Format the stored timestamp into a readable String using method.
            timeText.setText(message.getDate() != null ? df.format(message.getDate()): "NULL");
        }

    }

}

