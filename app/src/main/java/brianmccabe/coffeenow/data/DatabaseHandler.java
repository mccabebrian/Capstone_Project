package brianmccabe.coffeenow.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import brianmccabe.coffeenow.models.Coffee;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CoffeeNow";
    private static final String TABLE_FAVORITES = "Favorites";
    private static final String TABLE_CART = "Cart";
    private static final String KEY_NAME = "name";
    private static final String KEY_COFFEE_IMAGE = "image";
    private static final String KEY_PRICE = "price";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_FAVORITES + "("
                + KEY_NAME + " TEXT,"
                + KEY_COFFEE_IMAGE + " BLOB,"
                + KEY_PRICE + " TEXT" + ")";

        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + "("
                + KEY_NAME + " TEXT,"
                + KEY_COFFEE_IMAGE + " BLOB,"
                + KEY_PRICE + " TEXT" + ")";

        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_CART_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);

        // Create tables again
        onCreate(db);
    }

    public void addCoffee(Coffee coffee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, coffee.getName()); // Contact Name
        values.put(KEY_COFFEE_IMAGE, coffee.getCoffeeImage()); // Contact Phone Number
        values.put(KEY_PRICE, coffee.getPrice()); // Contact Phone Number

        // Inserting Row
        db.insert(TABLE_FAVORITES, null, values);
        db.close(); // Closing database connection
    }

    public void addToCart(Coffee coffee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, coffee.getName()); // Contact Name
        values.put(KEY_COFFEE_IMAGE, coffee.getCoffeeImage()); // Contact Phone Number
        values.put(KEY_PRICE, coffee.getPrice()); // Contact Phone Number

        // Inserting Row
        db.insert(TABLE_CART, null, values);
        db.close(); // Closing database connection
    }

    public List<Coffee> getAllCoffees() {
        List<Coffee> contactList = new ArrayList<Coffee>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FAVORITES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Coffee contact = new Coffee();
                contact.setName(cursor.getString(0));
                contact.setCoffeeImage(cursor.getBlob(1));
                contact.setPrice(cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<Coffee> getAllCoffeesInCart() {
        List<Coffee> contactList = new ArrayList<Coffee>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CART;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Coffee contact = new Coffee();
                contact.setName(cursor.getString(0));
                contact.setCoffeeImage(cursor.getBlob(1));
                contact.setPrice(cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public Coffee getCoffee(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FAVORITES, new String[] {KEY_NAME,
                KEY_COFFEE_IMAGE, KEY_PRICE }, KEY_NAME + "=?",
                new String[] { String.valueOf(name) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();


        if(cursor == null || cursor.getCount() == 0){
            return null;
        }
        return new Coffee(cursor.getString(0),
                cursor.getBlob(1), cursor.getString(2));
    }

    public Coffee getCoffeeFromCart(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CART, new String[] {KEY_NAME,
                        KEY_COFFEE_IMAGE, KEY_PRICE }, KEY_NAME + "=?",
                new String[] { String.valueOf(name) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();


        if(cursor == null || cursor.getCount() == 0){
            return null;
        }
        return new Coffee(cursor.getString(0),
                cursor.getBlob(1), cursor.getString(2));
    }
}