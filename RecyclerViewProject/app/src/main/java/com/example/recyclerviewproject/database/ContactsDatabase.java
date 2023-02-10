package com.example.recyclerviewproject.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.recyclerviewproject.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Contact.class},version = 1)
public abstract class ContactsDatabase extends RoomDatabase {

    public abstract ContactDAO contactDAO();
    //all threads can notice the change in this instance
    private volatile static ContactsDatabase INSTANCE;
//    public final static int NUMBER_OF_THREADS=4;
//    static final ExecutorService databaseWriteExecutor =
//            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    //double check locking
    public static ContactsDatabase getINSTANCE(final Context context){

        //prevent overhead of synchronize
        if (INSTANCE == null) {
            //to prevent multiple threads to access and modify database at the same time
            synchronized (ContactsDatabase.class) {
                //if statement prevents repeating of creating the database multiple times when rotating
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, ContactsDatabase.class, "ContactDB")
                            .addCallback(room_db_callback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }
    private static RoomDatabase.Callback room_db_callback = new RoomDatabase.Callback() {
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //any thing you want it to execute after creating DB

            new Populate_DB_AsyncTask(INSTANCE).execute();
        }
    };
    private static class Populate_DB_AsyncTask extends AsyncTask<Void, Void, Void> {
        private ContactDAO contactDAO;
        private Populate_DB_AsyncTask(ContactsDatabase contactsDatabase){
            contactDAO = contactsDatabase.contactDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("insertContacts","insert contacts is done");
            contactDAO.insertContact(new Contact(R.drawable.boo2,"Mai","01028360746"));
            contactDAO.insertContact(new Contact(R.drawable.boo2,"Mohamed","0103323746"));
            contactDAO.insertContact(new Contact(R.drawable.boo2,"Nour","01000360746"));
            contactDAO.insertContact(new Contact(R.drawable.boo2,"Omer","01028362226"));
            return null;
        }
    }
}
