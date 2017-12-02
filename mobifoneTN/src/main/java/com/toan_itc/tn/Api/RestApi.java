package com.toan_itc.tn.Api;

import com.toan_itc.tn.Api.jsonArray.JsonArrayMenu;
import com.toan_itc.tn.Api.jsonArray.JsonArrayThecao;
import com.toan_itc.tn.Api.jsonArray.JsonArrayThecaoLH;
import com.toan_itc.tn.Api.jsonArray.JsonArrayThuCuocKM;
import com.toan_itc.tn.Api.jsonArray.JsonArrayThuCuocLH;
import com.toan_itc.tn.Api.jsonArray.JsonArraycanhanKM;
import com.toan_itc.tn.Api.jsonArray.JsonArraycanhanTT;
import com.toan_itc.tn.Api.jsonArray.JsonArraydichvuchuyenvung;
import com.toan_itc.tn.Api.jsonArray.JsonArraydichvumobi;
import com.toan_itc.tn.Api.jsonArray.JsonArraydichvuphu;
import com.toan_itc.tn.Api.jsonArray.JsonArraydoanhnghiepKM;
import com.toan_itc.tn.Api.jsonArray.JsonArraydoanhnghiepTT;
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
import com.toan_itc.tn.Model.Menu;
import com.toan_itc.tn.Model.PostData;
import com.toan_itc.tn.Network.Constants;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface RestApi {
    @GET(Constants.Menu)
    Observable<JsonArrayMenu<Menu>> listmenu();
    //CaNhan
    @GET(Constants.KMCaNhan)
    Observable<JsonArraycanhanKM<DStincanhanKhuyenmaiRealm>> listCanhan();

    @GET(Constants.ThuTucCaNhan)
    Observable<JsonArraycanhanTT<DStincanhanThutucRealm>> listthutuc();
    //Doanhnghiep
    @GET(Constants.ThuTucDoanhNghiep)
    Observable<JsonArraydoanhnghiepTT<DStindoanhnghiepThutuc>> listdntt();

    @GET(Constants.KMDoanhNghiep)
    Observable<JsonArraydoanhnghiepKM<DStindoanhnghiepKhuyenmai>> listdnkm();
    //ThuCuoc
    @GET(Constants.ThuCuocKM)
    Observable<JsonArrayThuCuocKM<DStinKMthucuoc>> listthucuockm();

    @GET(Constants.ThuCuocLH)
    Observable<JsonArrayThuCuocLH<DSlienheThucuoc>> listthucuoclh();
    //TheCao
    @GET(Constants.MenhGia)
    Observable<JsonArrayThecao<DStinThecao>> listthecao();

    @GET(Constants.TheCaoLH)
    Observable<JsonArrayThecaoLH<DSlienhenapthecao>> listthecaolh();
    //Dichvukhac
    @GET(Constants.MoblieInternet)
    Observable<JsonArraydichvumobi<DStinMobileInternet>> listmobifone();

    @GET(Constants.DichVuKhac)
    Observable<JsonArraydichvuphu<DStinDichVuPhu>> listdichvuphu();

    @GET(Constants.ChuyenVung)
    Observable<JsonArraydichvuchuyenvung<DStinCVQT>> listchuyenvung();

    @FormUrlEncoded
    @POST(Constants.ThongTinKH)
    Observable<PostData> postdata(@Field("TenKH") String TenKH,@Field("HovaTenDem") String HovaTenDem,@Field("SDTKH") String SDTKH,@Field("SoNha") String SoNha
    ,@Field("Duong") String Duong,@Field("Phuong") String Phuong,@Field("Quan") String Quan);

    @FormUrlEncoded
    @POST(Constants.GopY)
    Observable<PostData> postgopy(@Field("TenKH") String TenKH,@Field("NoiDung") String NoiDung);

    @FormUrlEncoded
    @POST(Constants.Notification)
    Observable<PostData> posttoken(@Field("Aid") String Adi, @Field("RegID") String RegID);

    @FormUrlEncoded
    @POST(Constants.PostTheCao)
    Observable<PostData> post_thecao(@Field("SDT") String SDT,@Field("MenhGia") String MenhGia,@Field("ViTri") String ViTri);
}
