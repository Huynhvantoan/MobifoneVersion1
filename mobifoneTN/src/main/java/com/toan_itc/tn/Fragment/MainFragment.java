package com.toan_itc.tn.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toan_itc.tn.Activity.BaseActivity;
import com.toan_itc.tn.R;
import com.toan_itc.tn.Utils.Utils;

/**
 * Created by Toan.itc on 12/23/2015.
 */
@SuppressLint("ValidFragment")
public class MainFragment extends Fragment {
  BaseActivity activity;
  public MainFragment() {
    super();
  }

  public MainFragment(BaseActivity activity) {
    this.activity = activity;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view=inflater.inflate(R.layout.fragment_main, container, false);
    Utils.toFragment(this,new HomeFragment(activity),R.id.fragment);
    return view;
  }
}
