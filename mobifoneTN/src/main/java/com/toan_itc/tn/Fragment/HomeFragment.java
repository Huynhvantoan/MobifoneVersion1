package com.toan_itc.tn.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toan_itc.tn.Activity.BaseActivity;
import com.toan_itc.tn.Network.ApiController;
import com.toan_itc.tn.R;
import com.toan_itc.tn.Utils.Utils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Toan.itc on 12/23/2015.
 */
@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment {
  BaseActivity activity;
  private Unbinder mUnbinder;
  public HomeFragment() {
    super();
  }

  public HomeFragment(BaseActivity activity) {
    this.activity = activity;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    mUnbinder=ButterKnife.bind(this, view);
    return view;
  }

  @OnClick(R.id.layout_congno)
  public void click_congno(){
    Utils.toFragment(this,new KhoSoFragment(activity, ApiController.MENU1),R.id.fragment);
  }

  @OnClick(R.id.layout_khoso)
  public void click_khoso(){
    Utils.toFragment(this,new KhoSoFragment(activity, ApiController.MENU1),R.id.fragment);
  }

  @OnClick(R.id.layout_thutuc)
  public void click_thutuc(){
    Utils.toFragment(this,new KhoSoFragment(activity, ApiController.MENU1),R.id.fragment);
  }

  @OnClick(R.id.layout_ctkm)
  public void click_ctkm(){
    Utils.toFragment(this,new KhoSoFragment(activity, ApiController.MENU1),R.id.fragment);
  }

  @OnClick(R.id.layout_upanh)
  public void click_upanh(){
    Utils.toFragment(this,new UpAnhFragment(),R.id.fragment);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mUnbinder.unbind();
  }
}
