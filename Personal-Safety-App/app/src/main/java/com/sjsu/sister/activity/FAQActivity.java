package com.sjsu.sister.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.sjsu.sister.R;
import com.sjsu.sister.adapter.FAQsAdapter;
import com.sjsu.sister.model.FAQs;

import java.util.ArrayList;
import java.util.List;

public class FAQActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<FAQs> faqsList;
   // private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        recyclerView = findViewById(R.id.recycleView);

        initData();
        setRecyclerView();

    }

    private void setRecyclerView(){
        FAQsAdapter faqsAdapter = new FAQsAdapter(faqsList);
        recyclerView.setAdapter(faqsAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void initData(){
        faqsList = new ArrayList<>();
        faqsList.add(new FAQs("How can I add my Emergency contacts?","When installing Sister. Then, go to the side bar, select Emergency Contacts, and add your trusted contacts."));
        faqsList.add(new FAQs("How can I edit my contact list ?","Go to the side bar, select Emergency Contacts, choose one contacts and edit the information"));
        faqsList.add(new FAQs("Is my data private?","Yes, on Sister your location is encrypted with dynamic public and private keys, a military- level of encryption, so nobody else can see it."));
        faqsList.add(new FAQs("What happens if I activate SOS?","Two things happen automatically.\n" +
                "1.We send to your trusted contacts that have the app installed your location in real time.\n" +
                "2. We open your calls app and type the number of the emergency services in your country so you can call them if you want with just a click."));
        faqsList.add(new FAQs("How can I activate the deterrent alarm?","Click on the Siren button on the home page"));
        faqsList.add(new FAQs("How can I change my password?","Go to the side bar, select Setting, change your password with double confirmation"));
    }
}
