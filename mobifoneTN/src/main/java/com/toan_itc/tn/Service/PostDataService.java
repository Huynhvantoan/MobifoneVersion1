package com.toan_itc.tn.Service;

import android.content.Context;

import com.toan_itc.tn.Api.RestClient;
import com.toan_itc.tn.Model.PostData;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PostDataService {
    private RestClient mRestClient;
    public PostDataService(Context context, RestClient restClient) {
        this.mRestClient = restClient;
    }
    public Observable<PostData> postdata(String ten,String ho,String sdt,String sonha,String duong,String phuong,String quan) {
        return mRestClient.request().postdata(ten,ho,sdt,sonha,duong,phuong,quan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(postData -> postData);
    }

    public Observable<PostData> postgopy(String ten,String noidung) {
        return mRestClient.request().postgopy(ten,noidung)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(postData -> postData);
    }
    public Observable<PostData> posttoken(String adi, String regid) {
        return mRestClient.request().posttoken(adi,regid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(postData -> postData);
    }
    public Observable<PostData> post_thecao(String sdt,String menhgia,String diachi) {
        return mRestClient.request().post_thecao(sdt,menhgia,diachi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(postData -> postData);
    }
}
