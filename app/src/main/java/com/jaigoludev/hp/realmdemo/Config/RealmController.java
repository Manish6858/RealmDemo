package com.jaigoludev.hp.realmdemo.Config;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    private RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }



    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }



    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }


    public void clearAll() {

        realm.beginTransaction();
        realm.clear(DetailGetSet.class);
        realm.commitTransaction();
    }


    public RealmResults<DetailGetSet> getDetail() {

        return realm.where(DetailGetSet.class).findAll();
    }

    //query a single item with the given id
    public DetailGetSet getSingleDetail(String id) {

        return realm.where(DetailGetSet.class).equalTo("id", id).findFirst();
    }

}
