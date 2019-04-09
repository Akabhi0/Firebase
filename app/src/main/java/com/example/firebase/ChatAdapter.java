package com.example.firebase;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firebase.databinding.ChatadapterBinding;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewModel> {

    private ArrayList<ChatModel> chatModels;
    private Context context;

    public ChatAdapter(Comments comments, ArrayList<ChatModel> chatModels) {
        this.chatModels = chatModels;
        this.context = comments;
    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }

    @NonNull
    @Override
    public ViewModel onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.chatadapter, null);
        return new ViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewModel holder, int position) {
        holder.getBinding().chatConversation.setText(chatModels.get(position).getMessage());
        holder.getBinding().chatDate.setText(chatModels.get(position).getDate());
    }

    public class ViewModel extends RecyclerView.ViewHolder {
        private ChatadapterBinding binding;

        public ChatadapterBinding getBinding() {
            return binding;
        }

        public ViewModel(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
