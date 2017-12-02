package com.toan_itc.tn.Service;

import android.content.Context;

import com.toan_itc.tn.Api.RestClient;
import com.toan_itc.tn.Model.DSlienheThucuoc;
import com.toan_itc.tn.Model.DSlienhenapthecao;
import com.toan_itc.tn.Model.DStinCVQT;
import com.toan_itc.tn.Model.DStinDichVuPhu;
import com.toan_itc.tn.Model.DStinKMthucuoc;
import com.toan_itc.tn.Model.DStinMobileInternet;
import com.toan_itc.tn.Model.DStinThecao;
import com.toan_itc.tn.Model.DStincanhanKhuyenmaiRealm;
import com.toan_itc.tn.Model.DStincanhanThutucRealm;
import com.toan_itc.tn.Model.DStindoanhnghiepKhuyenmai;
import com.toan_itc.tn.Model.DStindoanhnghiepThutuc;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.exceptions.RealmMigrationNeededException;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ApiService {
    private RestClient mRestClient;
    private Realm realm;
    public ApiService(Context context, RestClient restClient) {
        try {
            this.mRestClient = restClient;
            this.realm = Realm.getDefaultInstance();
        }catch (RealmMigrationNeededException e){
            e.printStackTrace();
        }
    }
    //CaNhan
    public Observable<List<DStincanhanKhuyenmaiRealm>> getcanhan() {
        RealmResults<DStincanhanKhuyenmaiRealm> stincanhanKhuyenmaiRealms= realm.where(DStincanhanKhuyenmaiRealm.class).findAllSorted("idTin",Sort.DESCENDING);
        return Observable.just(stincanhanKhuyenmaiRealms);
    }
    public Observable<List<DStincanhanKhuyenmaiRealm>> updatecanhan() {
        return mRestClient.request().listCanhan()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(dStincanhanKhuyenmaiRealmJsonArrayCanhan -> dStincanhanKhuyenmaiRealmJsonArrayCanhan.DStincanhanKhuyenmai)
                .map(this::copyOrUpdateData);
    }
    private List<DStincanhanKhuyenmaiRealm> copyOrUpdateData(List<DStincanhanKhuyenmaiRealm> dStincanhanKhuyenmaiRealms) {
        realm.beginTransaction();
        realm.where(DStincanhanKhuyenmaiRealm.class).findAll().deleteAllFromRealm();
        realm.copyToRealmOrUpdate(dStincanhanKhuyenmaiRealms);
        realm.commitTransaction();
        return realm.where(DStincanhanKhuyenmaiRealm.class).findAll();
    }
    public Observable<List<DStincanhanThutucRealm>> getcanhanthutuc() {
        RealmResults<DStincanhanThutucRealm> stincanhanThutucRealms= realm.where(DStincanhanThutucRealm.class).findAllSorted("idTin",Sort.DESCENDING);
        return Observable.just(stincanhanThutucRealms);
    }
    public Observable<List<DStincanhanThutucRealm>> updatecanhanthutuc() {
        return mRestClient.request().listthutuc()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(dStincanhanThutucRealmJsonArraycanhanTT -> dStincanhanThutucRealmJsonArraycanhanTT.DStincanhanThutuc)
                .map(this::copyOrUpdateData1);
    }
    private List<DStincanhanThutucRealm> copyOrUpdateData1(List<DStincanhanThutucRealm> dStincanhanThutucRealms) {
      List<DStincanhanThutucRealm> realmList=null;
      try {
        realm.beginTransaction();
        realm.where(DStincanhanThutucRealm.class).findAll().deleteAllFromRealm();
        realm.copyToRealm(dStincanhanThutucRealms);
        realm.commitTransaction();
        realmList=realm.copyFromRealm(realm.where(DStincanhanThutucRealm.class).findAll());
        realm.close();
      }catch (Exception e){
        e.printStackTrace();
      }
        return realmList;
    }
    //Doanh nghiep
    public Observable<List<DStindoanhnghiepThutuc>> getttdoanhnghiep() {
        RealmResults<DStindoanhnghiepThutuc> dStindoanhnghiepThutucRealmResults= realm.where(DStindoanhnghiepThutuc.class).findAllSorted("idTin",Sort.DESCENDING);
        return Observable.just(dStindoanhnghiepThutucRealmResults);
    }
    public Observable<List<DStindoanhnghiepThutuc>> updatettdoanhnghiep() {
        return mRestClient.request().listdntt()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(dStindoanhnghiepThutucJsonArraydoanhnghiepTT -> dStindoanhnghiepThutucJsonArraydoanhnghiepTT.DStindoanhnghiepThutuc)
                .map(this::copyOrUpdatedntt);
    }
    private List<DStindoanhnghiepThutuc> copyOrUpdatedntt(List<DStindoanhnghiepThutuc> dStincanhanKhuyenmaiRealms) {
        realm.beginTransaction();
      realm.where(DStindoanhnghiepThutuc.class).findAll().deleteAllFromRealm();
        realm.copyToRealmOrUpdate(dStincanhanKhuyenmaiRealms);
        realm.commitTransaction();
        return realm.where(DStindoanhnghiepThutuc.class).findAll();
    }

    public Observable<List<DStindoanhnghiepKhuyenmai>> getdnkm() {
        RealmResults<DStindoanhnghiepKhuyenmai> dStindoanhnghiepKhuyenmaiRealmResults= realm.where(DStindoanhnghiepKhuyenmai.class).findAllSorted("idTin",Sort.DESCENDING);
        return Observable.just(dStindoanhnghiepKhuyenmaiRealmResults);
    }
    public Observable<List<DStindoanhnghiepKhuyenmai>> updatedoanhnghiepkm() {
        return mRestClient.request().listdnkm()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(dStindoanhnghiepKhuyenmaiJsonArraydoanhnghiepKM -> dStindoanhnghiepKhuyenmaiJsonArraydoanhnghiepKM.DStindoanhnghiepKhuyenmai)
                .map(this::copyOrUpdatednkm);
    }
    private List<DStindoanhnghiepKhuyenmai> copyOrUpdatednkm(List<DStindoanhnghiepKhuyenmai> dStincanhanThutucRealms) {
        realm.beginTransaction();
      realm.where(DStindoanhnghiepKhuyenmai.class).findAll().deleteAllFromRealm();
        realm.copyToRealmOrUpdate(dStincanhanThutucRealms);
        realm.commitTransaction();
        return realm.where(DStindoanhnghiepKhuyenmai.class).findAll();
    }

    //TheCao
    public Observable<List<DStinThecao>> getthecao() {
        RealmResults<DStinThecao> stinThecaoRealmResults= realm.where(DStinThecao.class).findAllSorted("idTin",Sort.DESCENDING);
        return Observable.just(stinThecaoRealmResults);
    }
    public Observable<List<DStinThecao>> updatethecao() {
        return mRestClient.request().listthecao()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(dStinThecaoJsonArrayThecao -> dStinThecaoJsonArrayThecao.DStinThecao)
                .map(this::copyOrUpdateData2);
    }
    private List<DStinThecao> copyOrUpdateData2(List<DStinThecao> dStinThecaos) {
        realm.beginTransaction();
      realm.where(DStinThecao.class).findAll().deleteAllFromRealm();
        realm.copyToRealmOrUpdate(dStinThecaos);
        realm.commitTransaction();
        return realm.where(DStinThecao.class).findAll();
    }

    public Observable<List<DSlienhenapthecao>> getthecaolh() {
        RealmResults<DSlienhenapthecao> stinThecaoRealmResults= realm.where(DSlienhenapthecao.class).findAllSorted("idLienHe",Sort.DESCENDING);
        return Observable.just(stinThecaoRealmResults);
    }
    public Observable<List<DSlienhenapthecao>> updatethecaolh() {
        return mRestClient.request().listthecaolh()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(dStinThecaoJsonArrayThecao -> dStinThecaoJsonArrayThecao.DSlienhenapthecao)
                .map(this::copyOrUpdateData3);
    }
    private List<DSlienhenapthecao> copyOrUpdateData3(List<DSlienhenapthecao> dSlienhenapthecaos) {
        realm.beginTransaction();
      realm.where(DSlienhenapthecao.class).findAll().deleteAllFromRealm();

        realm.copyToRealmOrUpdate(dSlienhenapthecaos);
        realm.commitTransaction();
        return realm.where(DSlienhenapthecao.class).findAll();
    }
    //ThuCuoc
    public Observable<List<DStinKMthucuoc>> getthucuockm() {
        RealmResults<DStinKMthucuoc> dStinKMthucuocs= realm.where(DStinKMthucuoc.class).findAllSorted("idTin",Sort.DESCENDING);
        return Observable.just(dStinKMthucuocs);
    }
    public Observable<List<DStinKMthucuoc>> updatethucuockm() {
        return mRestClient.request().listthucuockm()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(dStinKMthucuocJsonArrayThuCuocKM -> dStinKMthucuocJsonArrayThuCuocKM.DStinKMthucuoc)
                .map(this::copyOrUpdateData4);
    }
    private List<DStinKMthucuoc> copyOrUpdateData4(List<DStinKMthucuoc> dStinThecaos) {
        realm.beginTransaction();
      realm.where(DStinKMthucuoc.class).findAll().deleteAllFromRealm();
        realm.copyToRealmOrUpdate(dStinThecaos);
        realm.commitTransaction();
        return realm.where(DStinKMthucuoc.class).findAll();
    }

    public Observable<List<DSlienheThucuoc>> getthucuoclh() {
        RealmResults<DSlienheThucuoc> dSlienheThucuocs= realm.where(DSlienheThucuoc.class).findAllSorted("idLienHe",Sort.DESCENDING);
        return Observable.just(dSlienheThucuocs);
    }
    public Observable<List<DSlienheThucuoc>> updatethucuoclh() {
        return mRestClient.request().listthucuoclh()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(dSlienheThucuocJsonArrayThuCuocLH -> dSlienheThucuocJsonArrayThuCuocLH.DSlienheThucuoc)
                .map(this::copyOrUpdateData5);
    }
    private List<DSlienheThucuoc> copyOrUpdateData5(List<DSlienheThucuoc> dSlienhenapthecaos) {
        realm.beginTransaction();
      realm.where(DSlienheThucuoc.class).findAll().deleteAllFromRealm();

        realm.copyToRealmOrUpdate(dSlienhenapthecaos);
        realm.commitTransaction();
        return realm.where(DSlienheThucuoc.class).findAll();
    }
    //Dichvukhac
    public Observable<List<DStinMobileInternet>> getdvmobi() {
        RealmResults<DStinMobileInternet> dStinMobileInternets= realm.where(DStinMobileInternet.class).findAllSorted("idTin",Sort.DESCENDING);
        return Observable.just(dStinMobileInternets);
    }
    public Observable<List<DStinMobileInternet>> updatedvmobi() {
        return mRestClient.request().listmobifone()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(dStinMobileInternetJsonArraydichvumobi -> dStinMobileInternetJsonArraydichvumobi.DStinMobileInternet)
                .map(this::copyOrUpdateData6);
    }
    private List<DStinMobileInternet> copyOrUpdateData6(List<DStinMobileInternet> dStinThecaos) {
        realm.beginTransaction();
      realm.where(DStinMobileInternet.class).findAll().deleteAllFromRealm();
        realm.copyToRealmOrUpdate(dStinThecaos);
        realm.commitTransaction();
        return realm.where(DStinMobileInternet.class).findAll();
    }

    public Observable<List<DStinDichVuPhu>> getdvphu() {
        RealmResults<DStinDichVuPhu> dStinDichVuPhus= realm.where(DStinDichVuPhu.class).findAllSorted("idTin",Sort.DESCENDING);
        return Observable.just(dStinDichVuPhus);
    }
    public Observable<List<DStinDichVuPhu>> updatedvphu() {
        return mRestClient.request().listdichvuphu()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(dStinDichVuPhuJsonArraydichvuphu -> dStinDichVuPhuJsonArraydichvuphu.DStinDichVuPhu)
                .map(this::copyOrUpdateData7);
    }
    private List<DStinDichVuPhu> copyOrUpdateData7(List<DStinDichVuPhu> dSlienhenapthecaos) {
        realm.beginTransaction();
      realm.where(DStinDichVuPhu.class).findAll().deleteAllFromRealm();
        realm.copyToRealmOrUpdate(dSlienhenapthecaos);
        realm.commitTransaction();
        return realm.where(DStinDichVuPhu.class).findAll();
    }

    public Observable<List<DStinCVQT>> getdvcv() {
        RealmResults<DStinCVQT> dStinCVQTs= realm.where(DStinCVQT.class).findAllSorted("idTin",Sort.DESCENDING);
        return Observable.just(dStinCVQTs);
    }
    public Observable<List<DStinCVQT>> updatedvcv() {
        return mRestClient.request().listchuyenvung()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(dStinCVQTJsonArraydichvuchuyenvung -> dStinCVQTJsonArraydichvuchuyenvung.DStinCVQT)
                .map(this::copyOrUpdateData8);
    }
    private List<DStinCVQT> copyOrUpdateData8(List<DStinCVQT> dSlienhenapthecaos) {
        realm.beginTransaction();
      realm.where(DStinCVQT.class).findAll().deleteAllFromRealm();
        realm.copyToRealmOrUpdate(dSlienhenapthecaos);
        realm.commitTransaction();
        return realm.where(DStinCVQT.class).findAll();
    }
}
