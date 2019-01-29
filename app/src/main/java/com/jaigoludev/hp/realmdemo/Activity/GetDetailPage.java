package com.jaigoludev.hp.realmdemo.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.jaigoludev.hp.realmdemo.Config.DetailGetSet;
import com.jaigoludev.hp.realmdemo.Config.RealmController;
import com.jaigoludev.hp.realmdemo.R;

import java.util.List;

public class GetDetailPage extends AppCompatActivity {

    List<DetailGetSet> data_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_detail_page);
        RealmController.with(this).refresh();
        data_list = RealmController.with(this).getDetail();


    }
}
