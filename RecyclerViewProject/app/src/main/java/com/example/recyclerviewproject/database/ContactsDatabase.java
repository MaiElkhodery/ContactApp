package com.example.recyclerviewproject.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Contact.class},version = 1)
public abstract class ContactsDatabase extends RoomDatabase {

    public abstract ContactDAO contactDAO();
    //all threads can notice the change in this instance
    private volatile static ContactsDatabase INSTANCE;
    public final static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    //double check locking
    public static ContactsDatabase getINSTANCE(final Context context){

        //prevent overhead of synchronize
        if (INSTANCE == null) {
            //to prevent multiple threads to access and modify database at the same time
            synchronized (ContactsDatabase.class) {
                //if statement prevents repeating of creating the database multiple times when rotating
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, ContactsDatabase.class, "ContactDB").build();
                }
            }
        }

        return INSTANCE;
    }
    private static ContactsDatabase.Callback room_db_callback = new RoomDatabase.Callback() {
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //any thing you want it to execute after creating DB
            databaseWriteExecutor.execute(() -> {

            });
        }
    };
}
