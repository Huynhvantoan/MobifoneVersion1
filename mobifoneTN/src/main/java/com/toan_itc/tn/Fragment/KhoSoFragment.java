package com.toan_itc.tn.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toan_itc.tn.Activity.BaseActivity;
import com.toan_itc.tn.Adapter.TabPagerAdapter;
import com.toan_itc.tn.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Toan.itc on 12/23/2015.
 */
@SuppressLint("ValidFragment")
public class KhoSoFragment extends Fragment {
    BaseActivity activity;
    int screen = 1;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private Unbinder mUnbinder;
    public KhoSoFragment() {
        super();
    }

    public KhoSoFragment(BaseActivity activity, int screen) {
        this.activity = activity;
        this.screen = screen;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khoso, container, false);
        mUnbinder=ButterKnife.bind(this, view);
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getActivity().getSupportFragmentManager());
        tabPagerAdapter.addFragment(new RecyclerViewFragment(activity, screen), getResources().getString(R.string.KM));
        tabPagerAdapter.addFragment(new RecyclerView2Fragment(activity, screen), getResources().getString(R.string.ThuTuc));
        tabPagerAdapter.addFragment(new RecyclerView3Fragment(activity, screen), getResources().getString(R.string.ThuTuc));
        mViewPager.setAdapter(tabPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        return view;
    }
  /*  @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Subscribe
    public void onEventMainThread(String refresh) {
        if(refresh.equalsIgnoreCase(ApiController.Refresh))
        EventBus.getDefault().post(screen);
    }*/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
