package com.jaigoludev.hp.realmdemo.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaigoludev.hp.realmdemo.Config.DetailGetSet;
import com.jaigoludev.hp.realmdemo.Config.RealmController;
import com.jaigoludev.hp.realmdemo.R;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    private Realm realm;
    EditText name, age, contact, email;
    TextView submit;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String userId;


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
        database = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        databaseReference = database.getReference("users_detail");

        // store app title to 'app_title' node
        database.getReference("app_title").setValue("User Detail");



        findViewById(R.id.submit).setOnClickListener(view ->{
            {
                DetailGetSet detailGetSet = new DetailGetSet();
                detailGetSet.setId((int) (RealmController.getInstance().getDetail().size()+System.currentTimeMillis()));
                detailGetSet.setName(name.getText().toString());
                detailGetSet.setAge(age.getText().toString());
                detailGetSet.setContact(contact.getText().toString());
                detailGetSet.setEmail(email.getText().toString());

                if(name.getText().toString().equals("")|| age.getText().toString().equals("")|| contact.getText().toString().equals("")||email.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Entry empty", Toast.LENGTH_SHORT).show();
                } else {
                    insertFirebaseDatabase(detailGetSet);
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

        findViewById(R.id.get_detail).setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, GetDetailPage.class)));
    }

    private void insertFirebaseDatabase(DetailGetSet detailGetSet) {
        if (TextUtils.isEmpty(userId)) {
            userId = databaseReference.push().getKey();
        }
        databaseReference.child(userId).setValue(detailGetSet);
        addUserChangeListener();

    }

    private void addUserChangeListener() {
        databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DetailGetSet user = dataSnapshot.getValue(DetailGetSet.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
