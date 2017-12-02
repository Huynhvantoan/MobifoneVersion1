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
public class KhoSoOldFragment extends Fragment {
    BaseActivity activity;
    int screen = 1;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private View view;
    private Unbinder mUnbinder;
    private TabPagerAdapter tabPagerAdapter;
    public KhoSoOldFragment() {
        super();
    }

    public KhoSoOldFragment(BaseActivity activity, int screen) {
        this.activity = activity;
        this.screen = screen;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_khoso, container, false);
        mUnbinder=ButterKnife.bind(this, view);
        tabPagerAdapter = new TabPagerAdapter(getActivity().getSupportFragmentManager());
        if(screen==1) {
            tabPagerAdapter.addFragment(new RecyclerViewFragment(activity, screen), getResources().getString(R.string.KM));
            tabPagerAdapter.addFragment(new RecyclerView2Fragment(activity, screen), getResources().getString(R.string.ThuTuc));
        }else{
            tabPagerAdapter.addFragment(new RecyclerViewFragment(activity, screen), getResources().getString(R.string.ThuTuc));
            tabPagerAdapter.addFragment(new RecyclerView2Fragment(activity, screen), getResources().getString(R.string.KM));
        }
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
        if(view!=null)
            view=null;
        if(tabPagerAdapter!=null)
            tabPagerAdapter=null;
    }
}
