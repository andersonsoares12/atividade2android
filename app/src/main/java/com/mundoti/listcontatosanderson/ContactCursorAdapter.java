package com.mundoti.listcontatosanderson;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ContactCursorAdapter extends RecyclerView.Adapter<ContactCursorAdapter.ViewHolder> {

    private Context context;
    private Cursor cursor;

    public ContactCursorAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    public Cursor getCursor() {
        return cursor;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView emailTextView;
        public TextView phoneTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewName);
            emailTextView = itemView.findViewById(R.id.textViewEmail);
            phoneTextView = itemView.findViewById(R.id.textViewPhone);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }

        String name = cursor.getString(cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_NAME));
        String email = cursor.getString(cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_EMAIL));
        String phone = cursor.getString(cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_PHONE));

        holder.nameTextView.setText(name);
        holder.emailTextView.setText(email);
        holder.phoneTextView.setText(phone);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}
