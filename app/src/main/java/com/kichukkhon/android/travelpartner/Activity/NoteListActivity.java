package com.kichukkhon.android.travelpartner.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kichukkhon.android.travelpartner.Adapter.NoteAdapter;
import com.kichukkhon.android.travelpartner.Class.Note;
import com.kichukkhon.android.travelpartner.Database.NoteDBManager;
import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Util.Preference;

import java.util.ArrayList;

public class NoteListActivity extends BaseDrawerActivity {
    RecyclerView recyclerView;
    ArrayList<Note> noteList;
    NoteAdapter noteAdapter;
    NoteDBManager noteDBManager;
    Note noteInfo;
    Toolbar toolbar;
    int currentTourId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        toolbar = (Toolbar) findViewById(R.id.toolbarWithAppbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Notes");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        InitCommonUIElements();

        recyclerView = (RecyclerView) findViewById(R.id.rvTourList);

        noteDBManager = new NoteDBManager(this);

        Preference preference = new Preference(this);
        currentTourId = preference.getCurrentlySelectedTourId();

        getNoteData();

    }

    public void getNoteData() {
        noteList = noteDBManager.getNoteInfoByTourId(currentTourId);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        noteAdapter = new NoteAdapter(noteList, this);
        noteAdapter.notifyDataSetChanged();

        recyclerView.setAdapter(noteAdapter);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void fabAddExpense(View view) {
        Intent intent = new Intent(this, NoteEntryActivity.class);
        startActivity(intent);
    }
}
