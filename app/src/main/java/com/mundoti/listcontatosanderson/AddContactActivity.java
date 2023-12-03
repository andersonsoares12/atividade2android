package com.mundoti.listcontatosanderson;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddContactActivity extends AppCompatActivity {

    private ContactDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);

        dbHelper = new ContactDbHelper(this);

        final EditText nameEditText = findViewById(R.id.editTextName);
        final EditText emailEditText = findViewById(R.id.editTextEmail);
        final EditText phoneEditText = findViewById(R.id.editTextPhone);
        Button saveButton = findViewById(R.id.buttonSave);

        dbHelper = new ContactDbHelper(this);



        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Código a ser executado quando o botão "Save" for clicado

                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String phone = phoneEditText.getText().toString();

                // Insira um novo contato no banco de dados
                dbHelper.insertContact(new Contact(name, email, phone));

                // Exiba uma mensagem ou faça qualquer outra ação desejada
                Toast.makeText(AddContactActivity.this, "Contato salvo com sucesso", Toast.LENGTH_SHORT).show();

                // Volte para a ContactListActivity
                startActivity(new Intent(AddContactActivity.this, ContactListActivity.class));
                finish(); // Finalize a atividade atual para evitar empilhamento desnecessário
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String phone = phoneEditText.getText().toString();

                dbHelper.insertContact(new Contact(name, email, phone));

                showToast("Contato salvo com sucesso!");

                startActivity(new Intent(AddContactActivity.this, ContactListActivity.class));
                finish();
            }

        });

    }

    private void showToast(String message) {
        Toast.makeText(AddContactActivity.this, message, Toast.LENGTH_SHORT).show();
    }

}
