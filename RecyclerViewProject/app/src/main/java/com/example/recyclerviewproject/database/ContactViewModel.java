package com.example.recyclerviewproject.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    private ContactsRepository repository;
    private LiveData<List<Contact>> contactList;
    public ContactViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactsRepository(application);
        contactList = repository.getAllContacts();
    }
    public void insert(Contact contact){
        repository.insert(contact);
    }
    public void update(Contact contact){
        repository.update(contact);
    }
    public void delete(Contact contact){
        repository.delete(contact);
    }
    public void deleteAll(){
        repository.deleteAll();
    }
    public LiveData<List<Contact>> getAll(){
        return contactList;
    }
}
