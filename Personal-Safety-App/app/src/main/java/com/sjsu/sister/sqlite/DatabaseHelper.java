package com.sjsu.sister.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sjsu.sister.model.Contact;
import com.sjsu.sister.model.FakeCall;
import com.sjsu.sister.model.User;

public class  DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 7;

    // Database Name
    private static final String DATABASE_NAME = "UserAccount.db";

    // Table name
    private static final String TABLE_USER = "user";
    private static final String TABLE_CONTACT = "contact";
//    where I modify
    private static final String TABLE_FAKECALL = "fakecall";


    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_IMAGE = "user_image";
    private static final String COLUMN_USER_EMERGENCYCALL = "emergency_call";

    private static final String COLUMN_CONTACT_NAME = "contact_name";
    private static final String COLUMN_CONTACT_PHONE = "contact_phone";
    private static final String COLUMN_CONTACT_EMAIL = "contact_email";
    private static final String COLUMN_CONTACT_IMAGE = "contact_image";
    private static final String COLUMN_CONTACT_DEVICE = "contact_device";
    private static final String COLUMN_CONTACT_ID = "contact_id";
//    where I modify
    private static final String COLUMN_FAKECALL_NAME = "fakecall_name";
    private static final String COLUMN_FAKECALL_FILEPATH = "fakecall_filepath";
    private static final String COLUMN_FAKECALL_ISPLAY = "fakecall_isplay";
    private static final String COLUMN_FAKECALL_ID = "fakecall_id";

    // create table sql query
    private String CREATE_CONTACT_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_CONTACT + "("
            + COLUMN_CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CONTACT_NAME + " TEXT, "
            + COLUMN_CONTACT_PHONE + " TEXT, "
            + COLUMN_CONTACT_DEVICE + " TEXT, "
            + COLUMN_CONTACT_EMAIL + " TEXT, "
            + COLUMN_CONTACT_IMAGE + " TEXT, "
            + COLUMN_USER_EMAIL + " TEXT, "
            + " FOREIGN KEY (" + COLUMN_USER_EMAIL + ") REFERENCES " + TABLE_USER  + "(" + COLUMN_USER_EMAIL + "))";

    private String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT,"
            + COLUMN_USER_IMAGE + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT,"
            + COLUMN_USER_EMERGENCYCALL + " TEXT " + ")";
//    where I modify
    private String CREATE_FAKECALL_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_FAKECALL + "("
            + COLUMN_FAKECALL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_FAKECALL_NAME + " TEXT, "
            + COLUMN_FAKECALL_FILEPATH + " TEXT, "
            + COLUMN_FAKECALL_ISPLAY + " INTEGER, "
            + COLUMN_USER_EMAIL + " TEXT, "
            + " FOREIGN KEY (" + COLUMN_USER_EMAIL + ") REFERENCES " + TABLE_USER  + "(" + COLUMN_USER_EMAIL + "))";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private String DROP_CONTACT_TABLE = "DROP TABLE IF EXISTS " + TABLE_CONTACT;
//    where I modify
    private String DROP_FAKECALL_TABLE = "DROP TABLE IF EXISTS " + TABLE_FAKECALL;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_CONTACT_TABLE);
//    where I modify
        db.execSQL(CREATE_FAKECALL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_CONTACT_TABLE);
        db.execSQL(DROP_FAKECALL_TABLE);
        // Create tables again
        onCreate(db);
    }

    /**
     * Operations of TABLE_USER
     */

    // Create user record
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_EMERGENCYCALL, user.getEmergencyCall());
        // Inserting Row
        db.insertWithOnConflict(TABLE_USER, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    // Update user record
    public void updateUserPwd(String email, String newPwd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_PASSWORD, newPwd);
        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_EMAIL + " = ?",
                new String[]{String.valueOf(email)});
    }

    public void updateUser(String email,String newname, String newImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, newname);
        values.put(COLUMN_USER_IMAGE, newImage);
        // updating
        db.update(TABLE_USER, values, COLUMN_USER_EMAIL + " = ?",
                new String[]{String.valueOf(email)});
        db.close();
    }

    public void updateEmergencyCall(String email, String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMERGENCYCALL, phoneNumber);
        // updating
        db.update(TABLE_USER, values, COLUMN_USER_EMAIL + " = ?",
                new String[]{String.valueOf(email)});
        db.close();
    }

    // Delete user record
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    // Check user exist or not
    public boolean checkUser(String email) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    // Check email and password
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    // Name of Email
    public User userOfEmail(String email) {
        String[] columns = new String[] {
                COLUMN_USER_NAME,COLUMN_USER_PASSWORD,COLUMN_USER_IMAGE, COLUMN_USER_EMERGENCYCALL
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection arguments
        String[] selectionArgs = {email};

        // query user table with conditions
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();

        if (cursorCount > 0) {
            while (cursor.moveToNext()) {
                return new User(cursor.getString(0), email, cursor.getString(1), cursor.getString(2),cursor.getString(3));
            }
        }
        return new User();
    }


    /**
         * Operations of TABLE_CONTACT
     */
    // Add Contact
    public boolean addContact(String email, Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTACT_NAME, contact.getName());
        values.put(COLUMN_CONTACT_PHONE, contact.getPhoneNumber());
        values.put(COLUMN_CONTACT_DEVICE, contact.getDevice());
        values.put(COLUMN_CONTACT_EMAIL, contact.getEmail());
        values.put(COLUMN_CONTACT_IMAGE, contact.getProfileImage());
        values.put(COLUMN_USER_EMAIL, email);
        // Inserting Row
        long result = db.insert(TABLE_CONTACT, null, values);
        if(result == -1)
            return false;
        else
            return true;
    }

    // Retrieve all contacts
    public Cursor getAllContacts(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CONTACT
                + " WHERE " + COLUMN_USER_EMAIL + " = '" + email + "'", null);
    }

    // Update contact record
    public boolean updateContact(String email, Contact contact, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTACT_NAME, contact.getName());
        values.put(COLUMN_CONTACT_PHONE, contact.getPhoneNumber());
        values.put(COLUMN_CONTACT_EMAIL, contact.getEmail());
        values.put(COLUMN_CONTACT_DEVICE, contact.getDevice());
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_CONTACT_IMAGE, contact.getProfileImage());
        // updating row
        long result = db.update(TABLE_CONTACT, values, COLUMN_USER_EMAIL + " = ? AND " + COLUMN_CONTACT_ID + " = ?" ,
                new String[]{email,String.valueOf(id)});
        System.out.println( "Updating contact" +result);
        if(result != 1)
            return false;
        else
            return true;
    }

    // Retrieve the id of contact
    public int getContactID(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_CONTACT
                + " WHERE " + COLUMN_CONTACT_NAME + " = '" + contact.getName() + "'"
                + " AND " + COLUMN_CONTACT_PHONE + " = '" + contact.getPhoneNumber() + "'";
        Cursor cursor = db.rawQuery(sql,null);
        int contactId = -1;
        while(cursor.moveToNext()){
            contactId = cursor.getInt(0);
        }
        return contactId;
    }

    public Contact getContactOfID(int contactID){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT " + COLUMN_CONTACT_NAME + " FROM " + TABLE_CONTACT
                + " WHERE " + COLUMN_CONTACT_ID + " = '" + contactID + "'";
        Cursor cursor = db.rawQuery(sql,null);
        Contact contact = new Contact();
        while(cursor.moveToNext()){
            contact = new Contact(
                    cursor.getString(1),// Name
                    cursor.getString(5),// Phone
                    cursor.getString(4),// Device
                    cursor.getString(2),// Email
                    cursor.getString(3));// Image uri
        }
        return contact;
    }

    public Integer deleteContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CONTACT, COLUMN_CONTACT_ID + " = ?", new String[]{String.valueOf(id)});
    }



    /**
     * Operations of TABLE_FAKECALL
     */
    // Add FAKECALL
    public boolean addFakeCall(String email, FakeCall fakeCall){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FAKECALL_NAME, fakeCall.getName());
        values.put(COLUMN_FAKECALL_FILEPATH, fakeCall.getFilePath());
        values.put(COLUMN_USER_EMAIL, email);
        // Inserting Row
        long result = db.insert(TABLE_FAKECALL, null, values);
        if(result == -1)
            return false;
        else
            return true;
    }

    // Retrieve all FAKECALLS
    public Cursor getAllFakeCalls(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_FAKECALL
                + " WHERE " + COLUMN_USER_EMAIL + " = '" + email + "'", null);
    }

    // Retrieve the id of FAKECALL
    public int getFakeCallID(FakeCall fakeCall){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_FAKECALL
                + " WHERE " + COLUMN_FAKECALL_NAME + " = '" + fakeCall.getName() + "'"
                + " AND " + COLUMN_FAKECALL_FILEPATH + " = '" + fakeCall.getFilePath() + "'";
        Cursor cursor = db.rawQuery(sql,null);
        int fakeCallId = -1;
        while(cursor.moveToNext()){
            fakeCallId = cursor.getInt(0);
        }
        return fakeCallId;
    }

    // DELETE A FAKECALL USE ID
    public Integer deleteFakeCall(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_FAKECALL, COLUMN_FAKECALL_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public boolean updateFakeCall(String email, FakeCall fakeCall, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FAKECALL_ISPLAY, fakeCall.getIsPlay());
        // updating row
        long result = db.update(TABLE_FAKECALL, values, COLUMN_USER_EMAIL + " = ? AND " + COLUMN_FAKECALL_ID + " = ?" ,
                new String[]{email,String.valueOf(id)});
        System.out.println( "Updating contact" +result);
        if(result != 1)
            return false;
        else
            return true;
    }

    public boolean checkFakeCall( String fakeCallName,String email) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_FAKECALL_NAME
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_FAKECALL_NAME + " = ?" + " AND " + COLUMN_USER_EMAIL + " = ?";

        // selection arguments
        String[] selectionArgs = {fakeCallName, email};

        // query fakecall table with conditions
        Cursor cursor = db.query(TABLE_FAKECALL, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }





}