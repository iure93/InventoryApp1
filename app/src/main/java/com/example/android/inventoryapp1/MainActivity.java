package com.example.android.inventoryapp1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.android.inventoryapp1.data.BookDbHelper;

import static com.example.android.inventoryapp1.data.BookContract.BookEntry;


/**
 * Displays list of books that were entered and stored in the app.
 */
public class MainActivity extends AppCompatActivity {

    /** Database helper that will provide us access to the database */
    private BookDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new BookDbHelper(this);

        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the book database.
     */
    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM books"
        // to get a Cursor that contains all rows from the books table.
        Cursor cursor = db.rawQuery("SELECT * FROM " +BookEntry.TABLE_NAME, null);
        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // books table in the database).
            TextView displayView = (TextView) findViewById(R.id.text_view_book);
            displayView.setText("Number of rows in books database table: " + cursor.getCount());
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    /**
     * Helper method to insert hardcoded book data into the database. For debugging purposes only.
     */
    private void insertBook() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and Odissea's book attributes are the values.
        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_PRODUCT_NAME, "Odissea");
        values.put(BookEntry.COLUMN_PRICE, "19.99");
        values.put(BookEntry.COLUMN_QUANTITY, "2");
        values.put(BookEntry.COLUMN_SUPPLIER_NAME,  "Vasta");
        values.put(BookEntry.COLUMN_SUPPLIER_PHONE_NUMBER,  "0481582");


        long newRowId = db.insert(BookEntry.TABLE_NAME, null, values);
        Log.v("MainActivity", "New row ID" + newRowId);
    }
}