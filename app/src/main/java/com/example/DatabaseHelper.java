package com.example;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

    public class DatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "pantry.db";
        private static final int DATABASE_VERSION = 1;

        public static final String TABLE_PANTRY = "pantry";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_CATEGORY = "category";

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String createTable = "CREATE TABLE " + TABLE_PANTRY + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_QUANTITY + " INTEGER NOT NULL, " +
                    COLUMN_CATEGORY + " TEXT)";

            db.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
            onCreate(db);
        }

        public boolean addPantryItem(String name, int quantity, String category) {

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(COLUMN_NAME, name);
            values.put(COLUMN_QUANTITY, quantity);
            values.put(COLUMN_CATEGORY, category);

            long result = db.insert(TABLE_PANTRY, null, values);

            return result != -1;
        }

        public Cursor getAllPantryItems() {

            SQLiteDatabase db = this.getReadableDatabase();

            return db.rawQuery(
                    "SELECT * FROM " + TABLE_PANTRY,
                    null
            );
        }

        public boolean updatePantryItem(
                int id,
                String name,
                int quantity,
                String category) {

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(COLUMN_NAME, name);
            values.put(COLUMN_QUANTITY, quantity);
            values.put(COLUMN_CATEGORY, category);

            int result = db.update(
                    TABLE_PANTRY,
                    values,
                    COLUMN_ID + "=?",
                    new String[]{String.valueOf(id)}
            );

            return result > 0;
        }

        public boolean deletePantryItem(int id) {

            SQLiteDatabase db = this.getWritableDatabase();

            int result = db.delete(
                    TABLE_PANTRY,
                    COLUMN_ID + "=?",
                    new String[]{String.valueOf(id)}
            );

            return result > 0;
        }
    }
}
