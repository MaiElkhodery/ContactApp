package com.example.recyclerviewproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.recyclerviewproject.database.Contact;
import com.example.recyclerviewproject.database.ContactsDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity  {

    public final String CONTACT_NAME="contact_name";
    public final String CONTACT_NUMBER="contact_number";
    public ContactsDatabase database;
    ArrayList<Contact> arrayOfData;
    Recyc_Adapter adapter;
    RecyclerView recyclerView;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                arrayOfData = new ArrayList<>();
                initRecyclerView();
                callDatabase();
                FloatingActionButton add_button = findViewById(R.id.addButton);
                add_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this,InputIngoActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
        saveInfo();
    }
    public void initRecyclerView(){
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new Recyc_Adapter(arrayOfData);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
    public void callDatabase(){
        database = ContactsDatabase.getINSTANCE(this);
        Log.d("MainAC","setup"+database.hashCode());
    }
    public void showContacts(){

    }

    public void saveInfo() {
        Intent get_intent=getIntent();
        String name = get_intent.getStringExtra(CONTACT_NAME);
        String number = get_intent.getStringExtra(CONTACT_NUMBER);
        if(name != null && number != null) {
            database.contactDAO().
                    insertContact(new Contact(R.drawable.mmm, name, number));
            database.contactDAO().retrieveAllContacts().observe(this, new Observer<List<Contact>>() {
                @Override
                public void onChanged(List<Contact> contacts) {
                    arrayOfData.clear();
                    arrayOfData.addAll(contacts);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }



}