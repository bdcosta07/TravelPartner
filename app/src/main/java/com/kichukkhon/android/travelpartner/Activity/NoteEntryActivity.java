package com.kichukkhon.android.travelpartner.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.kichukkhon.android.travelpartner.Class.Note;
import com.kichukkhon.android.travelpartner.Database.NoteDBManager;
import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Util.AppUtils;
import com.kichukkhon.android.travelpartner.Util.Constants;

public class NoteEntryActivity extends AppCompatActivity {
    EditText txtTitle;
    EditText txtNote;
    Note noteInfo;
    NoteDBManager noteDBManager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_entry);

        txtTitle=(EditText)findViewById(R.id.note_title);
        txtNote=(EditText)findViewById(R.id.note_content);

        toolbar = (Toolbar) findViewById(R.id.toolbarWithAppbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Note");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveNoteInfo() {

        String noteTitle = txtTitle.getText().toString();
        String noteDesc = txtNote.getText().toString();

        noteInfo = new Note();
        noteDBManager = new NoteDBManager(this);

        noteInfo.setTitle(noteTitle);
        noteInfo.setNote(noteDesc);

        boolean inserted = noteDBManager.addNote(noteInfo);
        if (inserted) {
            Toast.makeText(this, "Data Inserted", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "Data not Inserted", Toast.LENGTH_SHORT).show();
    }
}
