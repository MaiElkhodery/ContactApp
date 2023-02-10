package com.example.recyclerviewproject.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContact(Contact contact);
    @Query("SELECT * FROM Contact WHERE id=:id")
    Contact retrieveContact(long id);
    @Query("SELECT * FROM Contact")
    LiveData<List<Contact>> retrieveAllContacts();
    @Delete
    void deleteContact(Contact contact);
    @Query("DELETE FROM Contact")
    void deleteAllContacts();
    @Update
    void update(Contact contact);
}
