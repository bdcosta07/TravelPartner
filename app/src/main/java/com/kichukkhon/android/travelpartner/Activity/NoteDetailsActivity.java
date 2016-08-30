package com.kichukkhon.android.travelpartner.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.kichukkhon.android.travelpartner.Class.Note;
import com.kichukkhon.android.travelpartner.Database.NoteDBManager;
import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Util.AppUtils;

public class NoteDetailsActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tvDate;
    TextView tvDetails;
    NoteDBManager noteDBManager;
    Note noteInfo;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        noteDBManager = new NoteDBManager(this);
        int noteId = getIntent().getIntExtra("noteId", 1);
        noteInfo = noteDBManager.getNoteInfoById(noteId);

        toolbar = (Toolbar) findViewById(R.id.toolbarWithAppbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(noteInfo.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvDate = (TextView) findViewById(R.id.tvNoteDate);
        tvDetails = (TextView) findViewById(R.id.tvNoteDetails);

        getData();
    }

    public void getData() {
        long date = noteInfo.getCreatedAt();
        String noteDescription = noteInfo.getNote();

        tvDate.setText(AppUtils.getFormattedDate(context, date));
        tvDetails.setText(noteDescription);
    }
}
