package com.example.firebase;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.firebase.databinding.ActivityCommentsBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Comments extends AppCompatActivity {

    private Toolbar toolbar;
    private ActivityCommentsBinding binding;
    private String name, comments;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    private DatabaseReference rootChild;
    private String temp_key;
    private ChatAdapter chatAdapter;
    private ArrayList<ChatModel> chatModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comments);
        binding.setMessage(comments);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitle("Comments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /**
         * This is for creating the database row one
         */
        if (!name.isEmpty()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(String.valueOf(name), "");
            root.updateChildren(map);
        }

        rootChild = FirebaseDatabase.getInstance().getReference().child(name);

        binding.buttonChatboxSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                Map<String, Object> map = new HashMap<String, Object>();
                temp_key = rootChild.push().getKey();
                rootChild.updateChildren(map);

                DatabaseReference message_root = rootChild.child(temp_key);
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("name", name);
                map2.put("msg", binding.getMessage());
                map2.put("date", currentDateTimeString);

                message_root.updateChildren(map2);
                binding.setMessage("");
            }
        });

        /**
         * This is adding for data into UI
         */

        rootChild.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String chat_msg, chat_user_name, chat_date;

    private void append_chat_conversation(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()) {
            chat_date = (String) ((DataSnapshot) i.next()).getValue();
            chat_msg = (String) ((DataSnapshot) i.next()).getValue();
            chat_user_name = (String) ((DataSnapshot) i.next()).getValue();

            ChatModel chatModel = new ChatModel();
            chatModel.setDate(chat_date);
            chatModel.setMessage(chat_msg);
            chatModels.add(chatModel);

            chatAdapter = new ChatAdapter(Comments.this, chatModels);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            binding.recyclerView.setLayoutManager(linearLayoutManager);
            binding.recyclerView.setAdapter(chatAdapter);
            chatAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
