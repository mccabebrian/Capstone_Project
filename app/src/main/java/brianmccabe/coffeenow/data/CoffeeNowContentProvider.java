package brianmccabe.coffeenow.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.HashMap;

/**
 * Created by brian on 13/02/2017.
 */

public class CoffeeNowContentProvider extends ContentProvider {
    static final String PROVIDER_NAME = "com.abcd";
    public static final String URL = "content://" + PROVIDER_NAME + "/Favorites";
    public static final String URL_CART = "content://" + PROVIDER_NAME + "/Cart";
    public static final Uri CONTENT_URI = Uri.parse(URL);
    public static final Uri CONTENT_URI_CART = Uri.parse(URL_CART);

    public static final String name = "name";
    public static final String icon = "image";
    public static final String price = "price";
    static final int uriCode = 1;
    static final int uriCodeCart = 2;
    static final UriMatcher uriMatcher;
    private static HashMap<String, String> values;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "Favorites", uriCode);
        uriMatcher.addURI(PROVIDER_NAME, "Favorites/*", uriCode);
        uriMatcher.addURI(PROVIDER_NAME, "Cart", uriCodeCart);
        uriMatcher.addURI(PROVIDER_NAME, "Cart/*", uriCodeCart);
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        if (db != null) {
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        switch (uriMatcher.match(uri)) {
            case uriCode:
                qb.setTables(TABLE_FAVORITES);
                qb.setProjectionMap(values);
                break;
            case uriCodeCart:
                qb.setTables(TABLE_FAVORITES);
                qb.setProjectionMap(values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder == "") {
            sortOrder = name;
        }
        Cursor c = qb.query(db, projection, selection, selectionArgs, null,
                null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case uriCode:
                return "vnd.android.cursor.dir/cte";
            case uriCodeCart:
                return "vnd.android.cursor.dir/cte";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        switch (uriMatcher.match(uri)){
            case uriCode:
                long rowID = db.insert(TABLE_FAVORITES, "", values);
                if (rowID > 0) {
                    Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                break;
            case uriCodeCart:
                long _ID2 = db.insert(TABLE_CART, "", values);
                if (_ID2 > 0) {
                    Uri _uri = ContentUris.withAppendedId(CONTENT_URI, _ID2);
                    getContext().getContentResolver().notifyChange(_uri, null);
                    return _uri;
                }
                break;
            default: throw new SQLException("Failed to insert row into " + uri);
        }


        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case uriCode:
                count = db.delete(TABLE_FAVORITES, selection, selectionArgs);
                break;
            case uriCodeCart:
                count = db.delete(TABLE_CART, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case uriCode:
                count = db.update(TABLE_FAVORITES, values, selection, selectionArgs);
                break;
            case uriCodeCart:
                count = db.update(TABLE_CART, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "CoffeeNow";
    static final int DATABASE_VERSION = 1;
    private static final String TABLE_FAVORITES = "Favorites";
    private static final String TABLE_CART = "Cart";
    private static final String KEY_NAME = "name";
    private static final String KEY_COFFEE_IMAGE = "image";
    private static final String KEY_PRICE = "price";
    static String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_FAVORITES + "("
            + KEY_NAME + " TEXT,"
            + KEY_COFFEE_IMAGE + " BLOB,"
            + KEY_PRICE + " TEXT" + ")";

    static String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + "("
            + KEY_NAME + " TEXT,"
            + KEY_COFFEE_IMAGE + " BLOB,"
            + KEY_PRICE + " TEXT" + ")";

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_CONTACTS_TABLE);
            db.execSQL(CREATE_CART_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + CREATE_CONTACTS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + CREATE_CART_TABLE);
            onCreate(db);
        }
    }

}
