package com.mundoti.listcontatosanderson;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ContactListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContactDbHelper dbHelper;
    private ContactCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        dbHelper = new ContactDbHelper(this);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cursorAdapter = new ContactCursorAdapter(this, dbHelper.getAllContacts());
        recyclerView.setAdapter(cursorAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // Obtenha o contato clicado usando o CursorAdapter
                        Cursor cursor = cursorAdapter.getCursor();
                        cursor.moveToPosition(position);
                        Contact contact = dbHelper.getContactFromCursor(cursor);

                        // Abra a EditContactActivity para editar o contato
                        Intent intent = new Intent(ContactListActivity.this, EditContactActivity.class);
                        intent.putExtra("contactId", contact.getId());
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // Implemente a lógica para clique longo (exclusão, etc.) se necessário
                    }
                }));
    }
}
