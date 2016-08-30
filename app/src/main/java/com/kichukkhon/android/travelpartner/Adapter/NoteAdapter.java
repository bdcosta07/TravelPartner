package com.kichukkhon.android.travelpartner.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kichukkhon.android.travelpartner.Activity.NoteDetailsActivity;
import com.kichukkhon.android.travelpartner.Class.Note;
import com.kichukkhon.android.travelpartner.R;
import com.kichukkhon.android.travelpartner.Util.AppUtils;
import com.kichukkhon.android.travelpartner.Util.Preference;

import java.util.ArrayList;

/**
 * Created by Bridget on 8/29/2016.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    ArrayList<Note> noteList = new ArrayList<>();
    Context context;
    Preference preference;


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvDescription;
        TextView tvDate;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Context context=view.getContext();
                    Intent intent = new Intent(context, NoteDetailsActivity.class);
                    int selectedNoteId = noteList.get(getAdapterPosition()).getId();
                    intent.putExtra("noteId", selectedNoteId);
                    context.startActivity(intent);

                }
            });

            tvTitle = (TextView) itemView.findViewById(R.id.noteTitle);
            tvDescription = (TextView) itemView.findViewById(R.id.noteContent);
            tvDate = (TextView) itemView.findViewById(R.id.noteDate);
        }
    }

    public NoteAdapter(ArrayList<Note> noteList, Context context) {
        this.noteList = noteList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View rowView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_row, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        String noteTitle = noteList.get(position).getTitle();
        String noteDescription = noteList.get(position).getNote().trim();
        long date = noteList.get(position).getCreatedAt();

        viewHolder.tvTitle.setText(noteTitle);
        viewHolder.tvDescription.setText(noteDescription);
        viewHolder.tvDate.setText(AppUtils.getFormattedDate(context, date));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
}
