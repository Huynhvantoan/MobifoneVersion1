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
import com.toan_itc.tn.Network.ApiController;
import com.toan_itc.tn.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by toan.it on 12/30/15.
 */
@SuppressLint("ValidFragment")
public class TheCaoFragment extends Fragment {
    private BaseActivity activity;
    private int screen=1;
    public TheCaoFragment(){
        super();
    }
    public TheCaoFragment(BaseActivity activity, int screen){
        this.activity=activity;
        this.screen=screen;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main,container,false);
        ViewPager viewPager=(ViewPager)view.findViewById(R.id.view_pager);
        TabLayout tabLayout=(TabLayout)view.findViewById(R.id.tabLayout);
        TabPagerAdapter tabPagerAdapter=new TabPagerAdapter(activity.getSupportFragmentManager());
        tabPagerAdapter.addFragment(new RecyclerViewFragment(activity,screen),getResources().getString(R.string.Menhgia));
        tabPagerAdapter.addFragment(new RecyclerView2Fragment(activity,screen),getResources().getString(R.string.Lienhe));
        viewPager.setAdapter(tabPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
    @Override
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
    }
}
