package com.example.recyclerviewproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviderGetKt;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.recyclerviewproject.database.Contact;
import com.example.recyclerviewproject.database.ContactViewModel;
import com.example.recyclerviewproject.database.ContactsDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements Recyc_Adapter.SetOnClickListener {

    private ContactViewModel viewModel;
    ContactsDatabase database;
    ArrayList<Contact> contactList;
    Recyc_Adapter adapter;
    RecyclerView recyclerView;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    MediaPlayer audio;
    Snackbar snackbar;
    CoordinatorLayout coordinatorLayout;
    ItemTouchHelper itemTouchHelper;
    public static final int ADD_CONTACT_REQUEST =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        contactList = new ArrayList<>();
        contactList.add(new Contact(R.drawable.boo2,"Mai","028"));
        contactList.add(new Contact(R.drawable.boo2,"donia","00000"));
        initRecyclerView();
        initDatabase();
        showList();
        FloatingActionButton add_button = findViewById(R.id.addButton);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,InputIngoActivity.class);
                startActivityForResult(intent,ADD_CONTACT_REQUEST);
            }
        });
        deleteItem();
        //itemsDragDrop();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    public void initRecyclerView(){
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new Recyc_Adapter(contactList,this);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
//    public void initViewModel(){
//        viewModel = new ViewModelProvider(this).get(ContactViewModel.class);
//        viewModel.getAll().observe(this, new Observer<List<Contact>>() {
//            @Override
//            public void onChanged(List<Contact> contacts) {
//                //adapter.setContacts(contacts);
//                Log.d("observer","done");
//            }
//        });
//    }
    public void initDatabase(){
        database=ContactsDatabase.getINSTANCE(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == ADD_CONTACT_REQUEST && resultCode == RESULT_OK){
            insertData();
            Toast.makeText(this,"insertion done",Toast.LENGTH_SHORT).show();
        }

    }
    public void showList(){
        database.contactDAO().retrieveAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
               contactList.clear();
               contactList.addAll(contacts);
               adapter.notifyDataSetChanged();
                Log.d("getContacts","Contact database size = "+contactList.size());
            }
        });
    }

    public void insertData() {
        String name = getIntent().getStringExtra(InputIngoActivity.CONTACT_NAME);
        String number = getIntent().getStringExtra(InputIngoActivity.CONTACT_NUMBER);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                database.contactDAO().insertContact(new Contact(R.drawable.mmm, name, number));
            }
        });
        showList();
    }
    //swipe right to delete a contact
    public void deleteItem(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        database.contactDAO()
                                .deleteContact(adapter.getContact(viewHolder.getAdapterPosition()));
                    }
                });
            }
        }).attachToRecyclerView(recyclerView);
    }

    //make sound methods..implementing the interface
    @Override
    public void onClickItem(Contact contact) {
        playClickAudio();
    }

    @Override
    public void onLongClickItem(Contact contact) {
        playLongClickAudio();
        showSnackBar(contact);
    }

    public void playClickAudio(){
        audio = MediaPlayer.create(this,R.raw.short_click);
        audio.start();
        audio.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                audio.release();
            }
        });
    }
    public void playLongClickAudio(){
        audio = MediaPlayer.create(this,R.raw.long_click);
        audio.start();
        audio.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                audio.release();
            }
        });
    }
    public void showSnackBar(Contact contact){
        snackbar = Snackbar.make(coordinatorLayout,"Contact name: "+
                contact.getContact_name()+", Contact number: "+contact.getContact_no(),
                Snackbar.LENGTH_LONG);
        snackbar.show();
    }
    //Drag and drop items in recyclerview

         ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP |
                ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END,0){

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();

                Collections.swap(contactList,fromPosition,toPosition);
                adapter.notifyItemMoved(fromPosition,toPosition);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };


}