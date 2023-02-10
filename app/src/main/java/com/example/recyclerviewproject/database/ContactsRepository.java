package com.example.recyclerviewproject.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactsRepository {

    private ContactDAO contactDAO;
    private LiveData<List<Contact>> contactsList;
    public  ContactsRepository(Application application){
        ContactsDatabase contactsDatabase = ContactsDatabase.getINSTANCE(application);
        contactDAO = contactsDatabase.contactDAO();
        contactsList = contactDAO.retrieveAllContacts();
    }

    public void insert(Contact contact){
        new InsertContactAsyncTask(contactDAO).execute(contact);
    }
    public void delete(Contact contact){
        new DeleteContactAsyncTask(contactDAO).execute(contact);
    }
    public void deleteAll(){
        new DeleteALlContactAsyncTask(contactDAO).execute();
    }
    public void update(Contact contact){
        new UpdateContactAsyncTask(contactDAO).execute(contact);
    }
    public LiveData<List<Contact>> getAllContacts(){
        return contactsList;
    }
    private static class InsertContactAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDAO contactDAO;
        private InsertContactAsyncTask(ContactDAO contactDAO){
            this.contactDAO=contactDAO;
        }
        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDAO.insertContact(contacts[0]);
            return null;
        }
    }
    private static class DeleteContactAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDAO contactDAO;
        private DeleteContactAsyncTask(ContactDAO contactDAO){
            this.contactDAO=contactDAO;
        }
        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDAO.deleteContact(contacts[0]);
            return null;
        }
    }
    private static class DeleteALlContactAsyncTask extends AsyncTask<Void, Void, Void> {

        private ContactDAO contactDAO;
        private DeleteALlContactAsyncTask(ContactDAO contactDAO){
            this.contactDAO=contactDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            contactDAO.deleteAllContacts();
            return null;
        }
    }
    private static class UpdateContactAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDAO contactDAO;
        private UpdateContactAsyncTask(ContactDAO contactDAO){
            this.contactDAO=contactDAO;
        }
        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDAO.update(contacts[0]);
            return null;
        }
    }
}
