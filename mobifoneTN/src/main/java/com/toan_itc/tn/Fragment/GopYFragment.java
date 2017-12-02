package com.toan_itc.tn.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
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
public class GopYFragment extends Fragment {
    @BindView(R.id.txt_name)
    MaterialEditText mTxtName;
    @BindView(R.id.txt_noidung)
    MaterialEditText mTxtNoidung;
    @BindView(R.id.btn_send)
    RippleView mBtnSend;
    private Context activity;
    private String status = "",name="",noidung="";
    private PostDataService mPostDataService;
    private View view;
    private Unbinder mUnbinder;
    public GopYFragment() {
        super();
    }

    public GopYFragment(Context activity) {
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
        view = inflater.inflate(R.layout.fragment_gopy, container, false);
        mUnbinder=ButterKnife.bind(this, view);
        return view;
    }
    @OnClick(R.id.btn_send) public void send_data(){
        if(checkInput()) {
            mPostDataService.postgopy(name,noidung)
                    .subscribe(postDataRealmResults -> {
                        status = postDataRealmResults.getMessage();
                        Log.wtf("status=",status);
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
        name=mTxtName.getText().toString();
        noidung=mTxtNoidung.getText().toString();
        if (name.trim().equalsIgnoreCase("")) {
            Utils.showdialog_error(activity, getString(R.string.name_empty)).show();
            return false;
        }
        if (noidung.trim().equalsIgnoreCase("")) {
            Utils.showdialog_error(activity, getString(R.string.noidung_empty)).show();
            return false;
        }
        return true;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
