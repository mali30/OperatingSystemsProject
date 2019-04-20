package com.code.mohamedali.osproj;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {

    int noteID;
    int myProcessID = android.os.Process.myPid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);


        // Displays document that the user has selected
        EditText editText = (EditText)findViewById(R.id.editText);

        // Gets NoteID that passed to this activity from MainActivity

        Intent intent = getIntent();
         noteID = intent.getIntExtra("noteID", -1);

        if(noteID != -1){
            editText.setText(MainActivity.notes.get(noteID));
        }else{
             // Add a new note

            // creating empty note
            MainActivity.notes.add("");
            noteID = MainActivity.notes.size() -1;
            // updating array adapter for list view
            MainActivity.arrayAdapter.notifyDataSetChanged();

        }
        // Listens to when you change your text
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                // Updates Notes ArrayList from the Main Activity and then automatically saves them
                MainActivity.notes.set(noteID,String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();



                // Saving Notes
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.code.mohamedali.osproj"
                , Context.MODE_PRIVATE);

                HashSet<String> set = new HashSet<>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes" , set).apply();


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}
