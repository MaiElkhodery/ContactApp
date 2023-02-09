package com.example.recyclerviewproject.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    private int contact_img;
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String contact_no;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String contact_name;

    public Contact(int contact_img, String contact_name, String contact_no) {
        this.contact_img = contact_img;
        this.contact_no = contact_no;
        this.contact_name = contact_name;
    }

    public int getContact_img() {
        return contact_img;
    }

    public void setContact_img(int contact_img) {
        this.contact_img = contact_img;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }
}
