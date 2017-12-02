package com.toan_itc.tn.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.toan_itc.tn.Api.RestClient;
import com.toan_itc.tn.Lib.ripeffect.RippleView;
import com.toan_itc.tn.Network.ApiController;
import com.toan_itc.tn.R;
import com.toan_itc.tn.Service.PostDataService;
import com.toan_itc.tn.Utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by toan.it on 12/30/15.
 */
@SuppressLint("ValidFragment")
public class ThongtinKHFragment extends Fragment {
    @BindView(R.id.txt_name)
    MaterialEditText mTxtName;
    @BindView(R.id.txt_hoten)
    MaterialEditText mTxtHoten;
    @BindView(R.id.txt_sdt)
    MaterialEditText mTxtSdt;
    @BindView(R.id.txt_sonha)
    MaterialEditText mTxtSonha;
    @BindView(R.id.txt_duong)
    MaterialEditText mTxtDuong;
    @BindView(R.id.txt_phuong)
    MaterialEditText mTxtPhuong;
    @BindView(R.id.txt_quan)
    MaterialEditText mTxtQuan;
    @BindView(R.id.btn_send)
    RippleView mBtnSend;
    private Context activity;
    private String status = "";
    private View view;
    private PostDataService mPostDataService;
    private Unbinder mUnbinder;
    private String username="",sdt="",hoten="",sonha="",duong="",quan="",phuong="";
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    public ThongtinKHFragment() {
        super();
    }

    public ThongtinKHFragment(Context activity) {
        this.activity = activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        RestClient api = RestClient.with(context);
        mPostDataService = new PostDataService(context, api);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thongtinkh, container, false);
        mUnbinder=ButterKnife.bind(this, view);
        mSharedPreferences=getActivity().getSharedPreferences(ApiController.APPTN,Context.MODE_PRIVATE);
        mEditor=mSharedPreferences.edit();
        return view;
    }
    @OnClick(R.id.btn_send) public void send_data(){
        if(checkInput()) {
            mPostDataService.postdata(username, hoten, sdt, sonha, duong, phuong, quan)
                    .subscribe(postDataRealmResults -> {
                        status = postDataRealmResults.getMessage();
                        Log.wtf("status=",status);
                        mEditor.putString(ApiController.Username,username);
                        mEditor.putString(ApiController.Phone,sdt);
                        mEditor.commit();
                    }, this::unableToUpdateMessage);
        }
    }
    private void unableToUpdateMessage(Throwable throwable) {
        Log.wtf("throwable=", throwable.getMessage() + "");
        Snackbar snackbar = Snackbar.make(view, R.string.error_connect, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        TextView snackbarTextView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        if (Utils.isAndroid6())
            snackbarTextView.setTextColor(ContextCompat.getColor(activity, R.color.white));
        else
            snackbarTextView.setTextColor(activity.getResources().getColor(R.color.white));
        snackbar.show();
    }
    public Boolean checkInput() {
        hoten=mTxtHoten.getText().toString();
        sonha=mTxtSonha.getText().toString();
        duong=mTxtDuong.getText().toString();
        phuong=mTxtPhuong.getText().toString();
        quan=mTxtQuan.getText().toString();

        username=mTxtName.getText().toString();
        sdt=mTxtSdt.getText().toString();
        if (username.trim().equalsIgnoreCase("")) {
            Utils.showdialog_error(activity, getString(R.string.name_empty)).show();
            return false;
        }
        if (sdt.trim().equalsIgnoreCase("")) {
            Utils.showdialog_error(activity, getString(R.string.sdt_empty)).show();
            return false;
        }else{
            if(sdt.length()<8||sdt.length()>12) {
                Utils.showdialog_error(activity, getString(R.string.sdt_error)).show();
                return false;
            }
        }
        return true;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
