package com.mundoti.listcontatosanderson;

import android.provider.BaseColumns;

public class ContactContract {
    private ContactContract() {}

    // Defina as constantes da tabela de contatos
    public static class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "contacts";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PHONE = "phone";
    }

    // Consulta SQL para criar a tabela de contatos
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ContactEntry.TABLE_NAME + " (" +
                    ContactEntry._ID + " INTEGER PRIMARY KEY," +
                    ContactEntry.COLUMN_NAME + " TEXT," +
                    ContactEntry.COLUMN_EMAIL + " TEXT," +
                    ContactEntry.COLUMN_PHONE + " TEXT)";

    // Consulta SQL para excluir a tabela de contatos
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContactEntry.TABLE_NAME;
}
