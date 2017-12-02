package com.toan_itc.tn.Service;

import android.content.Context;

import com.toan_itc.tn.Api.RestClient;
import com.toan_itc.tn.Model.Menu;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MenuService {
    private RestClient mRestClient;
    private Realm realm;

    public MenuService(Context context, RestClient restClient) {
        try {
            this.mRestClient = restClient;
            this.realm = Realm.getDefaultInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Observable<List<Menu>> getmenu() {
        RealmResults<Menu> menuRealmResults = realm.where(Menu.class).findAll();
        return Observable.just(menuRealmResults);
    }
    public Observable<List<Menu>> updatemenu() {
        return mRestClient.request().listmenu()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(menuJsonArrayMenu -> menuJsonArrayMenu.menu)
                .map(this::copyOrUpdateData);
    }
    private List<Menu> copyOrUpdateData(List<Menu> menus) {
        realm.beginTransaction();
        realm.where(Menu.class).findAll().deleteAllFromRealm();
        realm.copyToRealmOrUpdate(menus);
        realm.commitTransaction();
        return realm.where(Menu.class).findAll();
    }
}
