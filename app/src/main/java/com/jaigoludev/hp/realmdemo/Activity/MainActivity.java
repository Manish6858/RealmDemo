package com.jaigoludev.hp.realmdemo.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jaigoludev.hp.realmdemo.Config.DetailGetSet;
import com.jaigoludev.hp.realmdemo.Config.RealmController;
import com.jaigoludev.hp.realmdemo.R;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    private Realm realm;
    EditText name, age, contact, email;
    TextView submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //get realm instance
        this.realm = RealmController.with(this).getRealm();
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        contact = findViewById(R.id.contact);
        email = findViewById(R.id.email);


        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailGetSet detailGetSet = new DetailGetSet();
                detailGetSet.setId((int) (RealmController.getInstance().getDetail().size()+System.currentTimeMillis()));
                detailGetSet.setName(name.getText().toString());
                detailGetSet.setAge(age.getText().toString());
                detailGetSet.setContact(contact.getText().toString());
                detailGetSet.setEmail(email.getText().toString());

                if(name.getText().toString().equals("")|| age.getText().toString().equals("")|| contact.getText().toString().equals("")||email.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Entry empty", Toast.LENGTH_SHORT).show();
                } else {
                    realm.beginTransaction();
                    realm.copyToRealm(detailGetSet);
                    realm.commitTransaction();
                    name.setText("");
                    age.setText("");
                    contact.setText("");
                    email.setText("");

                }
            }
        });

        findViewById(R.id.get_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GetDetailPage.class));
            }
        });
    }
}
