package com.toan_itc.tn.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.toan_itc.tn.Lib.takephoto.RxPhoto;
import com.toan_itc.tn.Lib.takephoto.shared.TypeRequest;
import com.toan_itc.tn.R;
import com.toan_itc.tn.Utils.GETBITMAP;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by toan.it on 12/30/15.
 */
@SuppressLint("ValidFragment")
public class UpAnhFragment extends Fragment {
  @BindView(R.id.img_cmnd_mattruoc)
  ImageView imgCmndMattruoc;
  @BindView(R.id.img_cmnd_matsau)
  ImageView imgCmndMatsau;
  @BindView(R.id.img_hd_mattruoc)
  ImageView imgHdMattruoc;
  @BindView(R.id.img_hd_matsau)
  ImageView imgHdMatsau;
  @BindView(R.id.img_phuluc)
  ImageView imgPhuluc;
  private Unbinder mUnbinder;
  private Context mContext;

  public UpAnhFragment() {
    super();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    this.mContext = context;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_upanh, container, false);
    mUnbinder = ButterKnife.bind(this, view);
    return view;
  }

  @OnClick(R.id.btn_cmnd_mattruoc)
  public void btn_cmnd_mattruoc(){
    Dialog_Update_avatar(GETBITMAP.CMND1);
  }
  @OnClick(R.id.btn_cmnd_matsau)
  public void btn_cmnd_matsau(){
    Dialog_Update_avatar(GETBITMAP.CMND2);
  }
  @OnClick(R.id.btn_hd_mattruoc)
  public void btn_hd_mattruoc(){
    Dialog_Update_avatar(GETBITMAP.HD1);
  }
  @OnClick(R.id.btn_hd_matsau)
  public void btn_hd_matsau(){
    Dialog_Update_avatar(GETBITMAP.HD2);
  }
  @OnClick(R.id.btn_phuluc)
  public void btn_phuluc(){
    Dialog_Update_avatar(GETBITMAP.PL4);
  }
  private void Dialog_Update_avatar(GETBITMAP index) {
    final Dialog dialog = new Dialog(mContext);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.layout_dialog_avatar);
    Window window = dialog.getWindow();
    WindowManager.LayoutParams wlp = window.getAttributes();
    wlp.gravity = Gravity.BOTTOM;
    window.setAttributes(wlp);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    Button camera = (Button) dialog.findViewById(R.id.camera);
    Button thuvien = (Button) dialog.findViewById(R.id.thuvien);
    Button huy = (Button) dialog.findViewById(R.id.huy);
    camera.setOnClickListener(v -> {
      RxPhoto.requestBitmap(mContext, TypeRequest.CAMERA)
              // define your schedulers if necessary
              .doOnNext((bitmap) -> {
                returnBitmap(index,bitmap);
              })
              .subscribe();
    });
    thuvien.setOnClickListener(v -> {
      RxPhoto.requestBitmap(mContext, TypeRequest.GALLERY)
              // define your schedulers if necessary
              .doOnNext((bitmap) -> {
                returnBitmap(index,bitmap);
              })
              .subscribe();

    });
    huy.setOnClickListener(v -> dialog.dismiss());
    dialog.show();
  }

  private void returnBitmap(GETBITMAP index, Bitmap bitmap){
    GETBITMAP getbitmap = index;
    switch (getbitmap){
      case CMND1:
        imgCmndMattruoc.setImageBitmap(bitmap);
        break;
      case CMND2:
        imgCmndMatsau.setImageBitmap(bitmap);
        break;
      case HD1:
        imgHdMattruoc.setImageBitmap(bitmap);
        break;
      case HD2:
        imgHdMatsau.setImageBitmap(bitmap);
        break;
      case PL4:
        imgPhuluc.setImageBitmap(bitmap);
        break;
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mUnbinder.unbind();
  }
}
