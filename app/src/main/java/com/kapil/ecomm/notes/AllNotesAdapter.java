package com.kapil.ecomm.notes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.kapil.ecomm.R;
import com.kapil.ecomm.data.Note;
import com.kapil.ecomm.util.DateTimeUtils;

import java.util.ArrayList;
import java.util.List;


public class AllNotesAdapter extends RecyclerView.Adapter<AllNotesAdapter.NotesViewHolder> {

    private List<Note> notesList = new ArrayList<>();
    private AllNotesViewModel viewModel;
    private Context context;

    public AllNotesAdapter(Context context, AllNotesViewModel viewModel,List<Note> notesList) {
        this.context = context;
        this.viewModel = viewModel;
        this.notesList = notesList;
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(NotesViewHolder holder, final int position) {

        final Note note = notesList.get(position);
        holder.getRowView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.noteClicked(note.getId());
            }
        });

        holder.getDeleteButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.deleteNote(note.getId());
                notesList.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.getDescTextView().setText(note.getDescription());
        holder.getTileTextView().setText(note.getTitle());
        holder.getTimeTextView().setText(DateTimeUtils.getHumanReadableDate(note.getDatetime()));
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        private TextView tileTextView;
        private TextView descTextView;
        private TextView timeTextView;
        private View rowView;
        private Button deleteButton;

        NotesViewHolder(View rowView) {
            super(rowView);
            tileTextView = (TextView) rowView.findViewById(R.id.note_title_text_view);
            descTextView = (TextView) rowView.findViewById(R.id.note_desc_text_view);
            timeTextView = (TextView) rowView.findViewById(R.id.note_time_text_view);
            deleteButton = (Button) rowView.findViewById(R.id.delete_note_button);
            this.rowView = rowView;
        }

        public TextView getTileTextView() {
            return tileTextView;
        }

        public TextView getDescTextView() {
            return descTextView;
        }

        public TextView getTimeTextView() {
            return timeTextView;
        }

        public View getRowView() {
            return rowView;
        }

        public Button getDeleteButton() {
            return deleteButton;
        }

    }

}
