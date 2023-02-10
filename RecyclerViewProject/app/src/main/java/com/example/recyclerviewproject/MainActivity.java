package com.example.recyclerviewproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviderGetKt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.recyclerviewproject.database.Contact;
import com.example.recyclerviewproject.database.ContactViewModel;
import com.example.recyclerviewproject.database.ContactsDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity  {

    private ContactViewModel viewModel;
    Recyc_Adapter adapter;
    RecyclerView recyclerView;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
        initViewModel();


        FloatingActionButton add_button = findViewById(R.id.addButton);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,InputIngoActivity.class);
                startActivity(intent);
            }
        });

    }
    public void initRecyclerView(){
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new Recyc_Adapter();
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
    public void initViewModel(){
        viewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        viewModel.getAll().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                adapter.setContacts(contacts);
                Log.d("observer","done");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        insertData();
    }

    public void insertData() {
        Intent get_intent=getIntent();
        String name = get_intent.getStringExtra(InputIngoActivity.CONTACT_NAME);
        String number = get_intent.getStringExtra(InputIngoActivity.CONTACT_NUMBER);
        viewModel.insert(new Contact(R.drawable.mmm, name, number));
    }

}