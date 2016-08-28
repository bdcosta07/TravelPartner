package com.kichukkhon.android.travelpartner.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.kichukkhon.android.travelpartner.Class.Note;
import com.kichukkhon.android.travelpartner.Database.NoteDBManager;
import com.kichukkhon.android.travelpartner.R;

public class NoteEntryActivity extends AppCompatActivity {
    EditText txtTitle;
    EditText txtNote;
    Note noteInfo;
    NoteDBManager noteDBManager;
    Toolbar toolbar;
    int currentTourId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_entry);

        txtTitle = (EditText) findViewById(R.id.note_title);
        txtNote = (EditText) findViewById(R.id.note_content);

        noteInfo = new Note();
        noteDBManager = new NoteDBManager(this);


        toolbar = (Toolbar) findViewById(R.id.toolbarWithAppbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("New Note");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        currentTourId = 1;
        //noteInfo = noteDBManager.getNoteInfoById(currentTourId);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.icon_save) {
            saveNoteInfo();
            Intent intent = new Intent(this, NoteListActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveNoteInfo() {

        String noteTitle = txtTitle.getText().toString();
        String noteDesc = txtNote.getText().toString();
        long date = System.currentTimeMillis();

        noteInfo.setTitle(noteTitle);
        noteInfo.setNote(noteDesc);
        noteInfo.setCreatedAt(date);
        noteInfo.setTourId(currentTourId);

        boolean inserted = noteDBManager.addNote(noteInfo);
        if (inserted) {
            Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "Data not Inserted", Toast.LENGTH_SHORT).show();
    }
}
