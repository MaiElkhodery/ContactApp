package com.example.recyclerviewproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.recyclerviewproject.database.Contact;

public class InputIngoActivity extends AppCompatActivity {

    EditText contact_name;
    EditText contact_no;
    Button save_button;
    String name;
    String number;
    public final String CONTACT_NAME="contact_name";
    public final String CONTACT_NUMBER="contact_number";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_ingo);

        contact_name=findViewById(R.id.editTextPersonName);

        contact_no=findViewById(R.id.editTextPhone);

        save_button=findViewById(R.id.saveButton);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=contact_name.getText().toString();
                number=contact_no.getText().toString();
                Intent intent = new Intent(InputIngoActivity.this,MainActivity.class);
                intent.putExtra(CONTACT_NAME,name);
                intent.putExtra(CONTACT_NUMBER,number);
                startActivity(intent);
                onStop();
            }
        });

    }

}