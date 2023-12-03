package com.mundoti.listcontatosanderson;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditContactActivity extends AppCompatActivity {

    private ContactDbHelper dbHelper;
    private long contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);

        dbHelper = new ContactDbHelper(this);

        final EditText nameEditText = findViewById(R.id.editTextName);
        final EditText emailEditText = findViewById(R.id.editTextEmail);
        final EditText phoneEditText = findViewById(R.id.editTextPhone);
        Button saveButton = findViewById(R.id.buttonSave);

        // Obtenha o ID do contato a ser editado da Intent
        Intent intent = getIntent();
        contactId = intent.getLongExtra("contactId", -1);

        // Obtenha os dados do contato e preencha os campos de entrada
        Contact contact = dbHelper.getContact(contactId);
        nameEditText.setText(contact.getName());
        emailEditText.setText(contact.getEmail());
        phoneEditText.setText(contact.getPhone());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String phone = phoneEditText.getText().toString();

                // Atualize o contato no banco de dados
                dbHelper.updateContact(new Contact(contactId, name, email, phone));

                // Volte para a ContactListActivity
                startActivity(new Intent(EditContactActivity.this, ContactListActivity.class));
                finish(); // Finalize a atividade atual para evitar empilhamento desnecessário
            }
        });
    }
}
