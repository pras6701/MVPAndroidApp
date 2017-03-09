package com.mvp.app.data.db.table;

import android.content.ContentValues;
import android.database.Cursor;

import com.mvp.app.data.db.model.User;

public final class UserTable {
        public static final String TABLE_NAME = "user";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TOKEN = "token";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " TEXT PRIMARY KEY," +
                        COLUMN_NAME + " TEXT NOT NULL," +
                        COLUMN_TOKEN + " TEXT NOT NULL"+
                        " );";

        public static ContentValues toContentValues(User user) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, user.getId());
            values.put(COLUMN_NAME, user.getName());
            values.put(COLUMN_TOKEN, user.getToken());
            return values;
        }

        public static User parseCursor(Cursor cursor) {
            User user = new User();
            user.setId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            user.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            user.setToken(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TOKEN)));
            return user;
        }
    }