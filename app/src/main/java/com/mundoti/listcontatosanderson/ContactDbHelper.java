package com.mundoti.listcontatosanderson;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class ContactDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 1;

    public ContactDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(ContactContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Atualize a tabela de contatos se necessário
        db.execSQL(ContactContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    // Método para inserir um novo contato
    public void insertContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(ContactContract.ContactEntry.COLUMN_NAME, contact.getName());
            values.put(ContactContract.ContactEntry.COLUMN_EMAIL, contact.getEmail());
            values.put(ContactContract.ContactEntry.COLUMN_PHONE, contact.getPhone());

            // Insira a nova linha na tabela de contatos
            db.insert(ContactContract.ContactEntry.TABLE_NAME, null, values);
        } finally {
            db.close();
        }

        // Adicione um log para verificar se o método é chamado
        Log.d("ContactDbHelper", "Contato inserido com sucesso.");
    }

    public Cursor getAllContacts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                ContactContract.ContactEntry.TABLE_NAME,  // Tabela
                null,                                   // Colunas (null para todas)
                null,                                   // Cláusula WHERE
                null,                                   // Argumentos da cláusula WHERE
                null,                                   // GROUP BY
                null,                                   // HAVING
                null);                                  // ORDER BY
    }
    // Método para obter um contato específico pelo ID
    public Contact getContact(long contactId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                ContactContract.ContactEntry._ID,
                ContactContract.ContactEntry.COLUMN_NAME,
                ContactContract.ContactEntry.COLUMN_EMAIL,
                ContactContract.ContactEntry.COLUMN_PHONE
        };
        String selection = ContactContract.ContactEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(contactId)};

        Cursor cursor = db.query(
                ContactContract.ContactEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Contact contact = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(ContactContract.ContactEntry._ID);
                int nameIndex = cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_NAME);
                int emailIndex = cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_EMAIL);
                int phoneIndex = cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_PHONE);

                if (idIndex != -1 && nameIndex != -1 && emailIndex != -1 && phoneIndex != -1) {
                    long id = cursor.getLong(idIndex);
                    String name = cursor.getString(nameIndex);
                    String email = cursor.getString(emailIndex);
                    String phone = cursor.getString(phoneIndex);

                    contact = new Contact(id, name, email, phone);
                }
            }
            cursor.close();
        }

        return contact;
    }

    // Método para obter um objeto Contact a partir de um cursor
    public Contact getContactFromCursor(Cursor cursor) {
        Contact contact = null;
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(ContactContract.ContactEntry._ID);
            int nameIndex = cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_NAME);
            int emailIndex = cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_EMAIL);
            int phoneIndex = cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_PHONE);

            if (idIndex != -1 && nameIndex != -1 && emailIndex != -1 && phoneIndex != -1) {
                long id = cursor.getLong(idIndex);
                String name = cursor.getString(nameIndex);
                String email = cursor.getString(emailIndex);
                String phone = cursor.getString(phoneIndex);

                contact = new Contact(id, name, email, phone);
            }
        }

        return contact;
    }


    // Método para atualizar um contato no banco de dados
    public void updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ContactContract.ContactEntry.COLUMN_NAME, contact.getName());
        values.put(ContactContract.ContactEntry.COLUMN_EMAIL, contact.getEmail());
        values.put(ContactContract.ContactEntry.COLUMN_PHONE, contact.getPhone());

        String selection = ContactContract.ContactEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(contact.getId())};

        // Atualize a linha correspondente na tabela de contatos
        db.update(
                ContactContract.ContactEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );

        db.close();
    }
}
