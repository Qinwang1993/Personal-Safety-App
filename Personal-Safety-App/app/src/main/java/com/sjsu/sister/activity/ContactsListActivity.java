package com.sjsu.sister.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sjsu.sister.R;
import com.sjsu.sister.adapter.ContactAdapter;
import com.sjsu.sister.model.Contact;
import com.sjsu.sister.sqlite.DatabaseHelper;
import com.sjsu.sister.util.CustomToast;

import java.util.ArrayList;
import java.util.Collections;

public class ContactsListActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private FloatingActionButton fab;
    private String testImageURL = "https://dyl80ryjxr1ke.cloudfront.net/external_assets/hero_examples/hair_beach_v1785392215/original.jpeg";
    private ContactAdapter mAdapter;
    private ListView contactsList;
    private static final String TAG = "contactlist";
    private DatabaseHelper databaseHelper;
    private String userEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactslist);
        userEmail = getIntent().getStringExtra("Email");
        //Setup ListView
        contactsList = (ListView)findViewById(R.id.List_view);
        //setupContactsList();
        // Setup toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in = new Intent(ContactsListActivity.this, HomeActivity.class);
//                in.putExtra("Email",userEmail);
//                startActivity(in);
//                finish();
//            }
//        });
        // Setup floating bottom
        fab = findViewById(R.id.fabAddContact);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Respond to FAB click
                Intent in = new Intent(ContactsListActivity.this, AddContactActivity.class);
                in.putExtra("Email",userEmail);
                startActivity(in);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                mAdapter.filter(text);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        final ArrayList<Contact> contacts = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);
        Cursor cursor = databaseHelper.getAllContacts(userEmail);
        if( cursor.moveToNext()){
            do{
//            System.out.println("ContactsList: " +cursor.getString(1));
//            System.out.println("ContactsList: " +cursor.getString(2));
//            System.out.println("ContactsList: " +cursor.getString(3));
//            System.out.println("ContactsList: " +cursor.getString(4));
                contacts.add(new Contact(
                        cursor.getString(1),// Name
                        cursor.getString(2),// Phone
                        cursor.getString(3),// Device
                        cursor.getString(4),// Email
                        cursor.getString(5)));// Image uri
            }while(cursor.moveToNext());
        }

        Collections.sort(contacts, (a,b) -> a.getName().compareToIgnoreCase(b.getName()));
        mAdapter = new ContactAdapter(this, R.layout.layout_contact_list, contacts, "", userEmail);
        contactsList.setAdapter(mAdapter);
        contactsList.setEmptyView(findViewById(R.id.empty_view));
        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(ContactsListActivity.this, ViewContactsActivity.class);
                in.putExtra("Contact", contacts.get(position));
                in.putExtra("Email",userEmail);
                startActivity(in);
                finish();
            }
        });

        contactsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                showNotification(userEmail, contacts.get(position).getPhoneNumber());
                return false;
            }
        });
        super.onResume();
    }
    public void showNotification(String email, String phoneNumber){
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setIcon(R.mipmap.edit);
        alert.setTitle("Hint：");
        alert.setMessage("Set as Emergency call?");
        alert.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CustomToast.pop(ContactsListActivity.this, "Cancel", Toast.LENGTH_LONG).show();
            }
        });
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "Sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseHelper.updateEmergencyCall(email, phoneNumber);
                System.out.println("Set Emergency Call");
                onResume();
                alert.dismiss();
            }
        });
        alert.show();
    }


}

