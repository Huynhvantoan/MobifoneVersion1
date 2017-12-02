package com.toan_itc.tn.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.toan_itc.tn.Activity.BaseActivity;
import com.toan_itc.tn.Adapter.CaNhanThutucAdapter;
import com.toan_itc.tn.Adapter.DVkhacAdapter;
import com.toan_itc.tn.Adapter.DoanhnghiepKmAdapter;
import com.toan_itc.tn.Adapter.ThecaoLHAdapter;
import com.toan_itc.tn.Adapter.ThuCuocLHAdapter;
import com.toan_itc.tn.Api.RestClient;
import com.toan_itc.tn.Model.DSlienheThucuoc;
import com.toan_itc.tn.Model.DSlienhenapthecao;
import com.toan_itc.tn.Model.DStinDichVuPhu;
import com.toan_itc.tn.Model.DStincanhanThutucRealm;
import com.toan_itc.tn.Model.DStindoanhnghiepKhuyenmai;
import com.toan_itc.tn.R;
import com.toan_itc.tn.Service.ApiService;
import com.toan_itc.tn.Utils.DividerItemDecoration;
import com.toan_itc.tn.Utils.Settings;
import com.toan_itc.tn.Utils.Utils;

import java.util.Date;
import java.util.List;

/**
 * Created by Toan.itc on 12/23/2015.
 */
@SuppressLint("ValidFragment")
public class RecyclerView2Fragment extends Fragment implements CaNhanThutucAdapter.OnItemClickListener,ThuCuocLHAdapter.OnItemClickListener,DoanhnghiepKmAdapter.OnItemClickListener,ThecaoLHAdapter.OnItemClickListener,DVkhacAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    public static final String LOG_TAG = RecyclerViewFragment.class.getSimpleName();
    private static final String ADAPTER_SELECTED_ITEM = "ADAPTER_SELECTED_ITEM";
    private static final int ARTICLES_BEFORE_MORE = 3;
    private static final int FIVE_MINUTES = 5 * 60 * 1000;
    private ApiService mCaNhanService;
    private Settings settings;
    private BaseActivity activity;
    public interface FragmentCallback2 {
        void onItemSelected2(String link);
        boolean isTwoPane2();
    }

    /**
     * The fragment's current OnItemSelectedCallback object, which is notified of list item
     * clicks.
     */
    private FragmentCallback2 fragmentCallback;
    /**
     * The fragment view that gets inflated inside onCreateView.
     */
    private View fragmentView;
    /**
     * The LinearLayoutManager for the RecyclerView.
     */
    private LinearLayoutManager layoutManager;
    /**
     * The RecyclerView adapter that has all the articles.
     */
    private CaNhanThutucAdapter mCaNhanAdapter;
    private DoanhnghiepKmAdapter mDoanhnghiepKmAdapter;
    private ThecaoLHAdapter mThecaoLHAdapter;
    private DVkhacAdapter mDVkhacAdapter;
    private ThuCuocLHAdapter mThuCuocLHAdapter;
    /**
     * The list of all articles.
     */
    private List<DStincanhanThutucRealm> mDStincanhanThutucRealms;
    private List<DStindoanhnghiepKhuyenmai> mStindoanhnghiepKhuyenmaiList;
    private List<DSlienhenapthecao> mDSlienhenapthecaos;
    private List<DStinDichVuPhu> mDStinDichVuPhusList;
    private List<DSlienheThucuoc> mDSlienheThucuocList;
    private SuperRecyclerView recyclerView;
    /**
     * This indicates if more older articles are currently loading
     */
    private boolean isLoadingMore = false;
    int screen=1;
    public RecyclerView2Fragment() {
    }

    public RecyclerView2Fragment(BaseActivity activity,int screen) {
        this.activity = activity;
        this.screen=screen;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Activities containing this fragment must implement its callbacks.
        if (!(context instanceof FragmentCallback2)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        fragmentCallback = (FragmentCallback2) context;

        RestClient api = RestClient.with(context);
        mCaNhanService = new ApiService(context, api);
        settings = new Settings(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.updateAll();
        if(screen==1) {
            mCaNhanService.getcanhanthutuc()
                    .subscribe(stincanhanThutucRealms -> this.mDStincanhanThutucRealms = stincanhanThutucRealms);
            mCaNhanAdapter = new CaNhanThutucAdapter(activity, this.mDStincanhanThutucRealms, this);
        }else if(screen==2){
            mCaNhanService.getthucuoclh()
                    .subscribe(dSlienheThucuocList -> this.mDSlienheThucuocList = dSlienheThucuocList);
            mThuCuocLHAdapter = new ThuCuocLHAdapter(activity, this.mDSlienheThucuocList, this);
        }else if(screen==3){
            mCaNhanService.getthecaolh()
                    .subscribe(dSlienhenapthecaos -> this.mDSlienhenapthecaos = dSlienhenapthecaos);
            mThecaoLHAdapter = new ThecaoLHAdapter(activity, this.mDSlienhenapthecaos, this);
        }else if(screen==4){
            mCaNhanService.getdvphu()
                    .subscribe(dStinDichVuPhuList -> this.mDStinDichVuPhusList = dStinDichVuPhuList);
            mDVkhacAdapter = new DVkhacAdapter(activity, this.mDStinDichVuPhusList, this);
        }else if(screen==5){
            mCaNhanService.getdnkm()
                    .subscribe(dStindoanhnghiepKhuyenmaiList -> this.mStindoanhnghiepKhuyenmaiList = dStindoanhnghiepKhuyenmaiList);
            mDoanhnghiepKmAdapter = new DoanhnghiepKmAdapter(activity, this.mStindoanhnghiepKhuyenmaiList, this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.fragment_recyclerview2, container, false);
        recyclerView=(SuperRecyclerView)fragmentView.findViewById(R.id.recyclerview_2);
        layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        if(screen==1){
            recyclerView.addItemDecoration(new DividerItemDecoration(activity, null));
            recyclerView.setAdapter(mCaNhanAdapter);}
        else if(screen==2){
            recyclerView.setAdapter(mThuCuocLHAdapter);}
        else if(screen==3){
            recyclerView.setAdapter(mThecaoLHAdapter);}
        else if(screen==4){
            recyclerView.setAdapter(mDVkhacAdapter);}
        else if(screen==5){
            recyclerView.setAdapter(mDoanhnghiepKmAdapter);}
        recyclerView.setRefreshListener(this);
        recyclerView.setRefreshingColorResources(
                R.color.refresh1,
                R.color.refresh2,
                R.color.refresh3,
                R.color.refresh3
        );
        // Only update articles on start if it hasn't been done in the past 5 minutes.
        if (new Date().getTime() - settings.getLastArticlesUpdate() > FIVE_MINUTES) {
            if ((mDStincanhanThutucRealms!=null&&mDStincanhanThutucRealms.size()> 0)||(mDSlienhenapthecaos!=null&&mDSlienhenapthecaos.size()> 0)
                    ||(mDStinDichVuPhusList!=null&&mDStinDichVuPhusList.size()>0)||(mDSlienheThucuocList!=null&&mDSlienheThucuocList.size()>0)
                    ||(mStindoanhnghiepKhuyenmaiList!=null&&mStindoanhnghiepKhuyenmaiList.size()>0)) {
                recyclerView.getSwipeToRefresh().post(() -> recyclerView.getSwipeToRefresh().setRefreshing(true));
            }
            this.updateAll();
        }
       /* recyclerView.setupMoreListener((numberOfItems, numberBeforeMore, currentItemPos) -> {
            DStincanhanKhuyenmai lastArticle = adapter.getLastArticle();
            if (!isLoadingMore) {
                isLoadingMore = true;
                articleService.getArticlesOlderThan(lastArticle.getId())
                        .subscribe(articles -> {
                            this.articles = articles;
                            adapter.notifyDataSetChanged();
                            recyclerView.hideMoreProgress();
                            isLoadingMore = false;
                        }, (throwable) -> {
                            recyclerView.hideMoreProgress();
                            isLoadingMore = false;
                            this.unableToUpdateMessage(throwable);
                        });
            } else {
                recyclerView.hideMoreProgress();
            }
        }, ARTICLES_BEFORE_MORE);*/
        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try{
            if(screen==1) {
                if (savedInstanceState != null) {
                    mCaNhanAdapter.setSelectedItem(savedInstanceState.getInt(ADAPTER_SELECTED_ITEM, -1));
                }
                mCaNhanAdapter.setTwoPane(false);
            }else if(screen==2){
                if (savedInstanceState != null) {
                    mThuCuocLHAdapter.setSelectedItem(savedInstanceState.getInt(ADAPTER_SELECTED_ITEM, -1));
                }
                mThuCuocLHAdapter.setTwoPane(false);
            }else if(screen==3){
                if (savedInstanceState != null) {
                    mThecaoLHAdapter.setSelectedItem(savedInstanceState.getInt(ADAPTER_SELECTED_ITEM, -1));
                }
                mThecaoLHAdapter.setTwoPane(false);
            }else if(screen==4){
                if (savedInstanceState != null) {
                    mDVkhacAdapter.setSelectedItem(savedInstanceState.getInt(ADAPTER_SELECTED_ITEM, -1));
                }
                mDVkhacAdapter.setTwoPane(false);
            }else if(screen==5){
                if (savedInstanceState != null) {
                    mDoanhnghiepKmAdapter.setSelectedItem(savedInstanceState.getInt(ADAPTER_SELECTED_ITEM, -1));
                }
                mDoanhnghiepKmAdapter.setTwoPane(false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        try{
            if(screen==1)
                outState.putInt(ADAPTER_SELECTED_ITEM, mCaNhanAdapter.getSelectedItem());
            else if(screen==2)
                outState.putInt(ADAPTER_SELECTED_ITEM, mThuCuocLHAdapter.getSelectedItem());
            else if(screen==3)
                outState.putInt(ADAPTER_SELECTED_ITEM, mThecaoLHAdapter.getSelectedItem());
            else if(screen==4)
                outState.putInt(ADAPTER_SELECTED_ITEM, mDVkhacAdapter.getSelectedItem());
            else if(screen==5)
                outState.putInt(ADAPTER_SELECTED_ITEM, mDoanhnghiepKmAdapter.getSelectedItem());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(String link) {
        fragmentCallback.onItemSelected2(link);
    }
    @Override
    public void onItemClickTheCaoLH(String phone) {

    }
    @Override
    public void onItemClickDVPhu(String link) {
        fragmentCallback.onItemSelected2(link);
    }
    @Override
    public void onItemClickThuCuocLH(String phone) {

    }
    @Override
    public void onItemClickdnkm(String link) {
        fragmentCallback.onItemSelected2(link);
    }
    @Override
    public void onRefresh() {
        this.updateAll();
    }
    private void updateAll() {
        if(screen==1) {
            mCaNhanService.updatecanhanthutuc()
                    .subscribe(dStincanhanThutucRealmJsonArraycanhanTT -> {
                        this.mDStincanhanThutucRealms = dStincanhanThutucRealmJsonArraycanhanTT;
                        mCaNhanAdapter.notifyDataSetChanged();
                        refesh_off();
                        settings.setLastArticlesUpdate();
                    }, this::unableToUpdateMessage);
        }else if(screen==2){
            mCaNhanService.updatethucuoclh()
                    .subscribe(dSlienheThucuocList -> {
                        this.mDSlienheThucuocList = dSlienheThucuocList;
                        mThuCuocLHAdapter.notifyDataSetChanged();
                        refesh_off();
                        settings.setLastArticlesUpdate();
                    }, this::unableToUpdateMessage);
        }else if(screen==3){
            mCaNhanService.updatethecaolh()
                    .subscribe(dSlienhenapthecaos -> {
                        this.mDSlienhenapthecaos = dSlienhenapthecaos;
                        mThecaoLHAdapter.notifyDataSetChanged();
                        refesh_off();
                        settings.setLastArticlesUpdate();
                    }, this::unableToUpdateMessage);
        }else if(screen==4){
            mCaNhanService.updatedvphu()
                    .subscribe(dStinDichVuPhuList -> {
                        this.mDStinDichVuPhusList = dStinDichVuPhuList;
                        mDVkhacAdapter.notifyDataSetChanged();
                        refesh_off();
                        settings.setLastArticlesUpdate();
                    }, this::unableToUpdateMessage);
        }else if(screen==5){
            mCaNhanService.updatedoanhnghiepkm()
                    .subscribe(dStindoanhnghiepKhuyenmaiList -> {
                        this.mStindoanhnghiepKhuyenmaiList = dStindoanhnghiepKhuyenmaiList;
                        mDoanhnghiepKmAdapter.notifyDataSetChanged();
                        refesh_off();
                        settings.setLastArticlesUpdate();
                    }, this::unableToUpdateMessage);
        }
    }
    private void refesh_off(){
        if(recyclerView!=null)
        recyclerView.getSwipeToRefresh().post(() -> recyclerView.getSwipeToRefresh().setRefreshing(false));
    }
    private void unableToUpdateMessage(Throwable throwable) {
        Log.wtf("throwable=",throwable.getMessage()+"");
        recyclerView.getSwipeToRefresh().post(() -> recyclerView.getSwipeToRefresh().setRefreshing(false));
        Snackbar snackbar = Snackbar.make(fragmentView, R.string.error_connect, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        TextView snackbarTextView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        if(Utils.isAndroid6())
            snackbarTextView.setTextColor(ContextCompat.getColor(activity,R.color.white));
        else
            snackbarTextView.setTextColor(activity.getResources().getColor(R.color.white));
        snackbar.show();
    }
  /*  @Subscribe
    public void onEventMainThread(Integer screen) {
        if(screen==1) {
            Log.wtf("onEventMainThread","onEventMainThread1111111");
            mCaNhanService.updatecanhanthutuc()
                    .subscribe(dStincanhanThutucRealmJsonArraycanhanTT -> {
                        this.mCaNhanAdapter.clear();
                        mCaNhanAdapter.addAll(dStincanhanThutucRealmJsonArraycanhanTT);
                        refesh_off();
                        settings.setLastArticlesUpdate();
                    }, this::unableToUpdateMessage);
        }else if(screen==2){
            mCaNhanService.updatethucuoclh()
                    .subscribe(dSlienheThucuocList -> {
                        this.mDSlienheThucuocList = dSlienheThucuocList;
                        mThuCuocLHAdapter.notifyDataSetChanged();
                        refesh_off();
                        settings.setLastArticlesUpdate();
                    }, this::unableToUpdateMessage);
        }else if(screen==3){
            mCaNhanService.updatethecaolh()
                    .subscribe(dSlienhenapthecaos -> {
                        this.mDSlienhenapthecaos = dSlienhenapthecaos;
                        mThecaoLHAdapter.notifyDataSetChanged();
                        refesh_off();
                        settings.setLastArticlesUpdate();
                    }, this::unableToUpdateMessage);
        }else if(screen==4){
            mCaNhanService.updatedvphu()
                    .subscribe(dStinDichVuPhuList -> {
                        this.mDStinDichVuPhusList = dStinDichVuPhuList;
                        mDVkhacAdapter.notifyDataSetChanged();
                        refesh_off();
                        settings.setLastArticlesUpdate();
                    }, this::unableToUpdateMessage);
        }else if(screen==5){
            mCaNhanService.updatedoanhnghiepkm()
                    .subscribe(dStindoanhnghiepKhuyenmaiList -> {
                        this.mStindoanhnghiepKhuyenmaiList = dStindoanhnghiepKhuyenmaiList;
                        mDoanhnghiepKmAdapter.notifyDataSetChanged();
                        refesh_off();
                        settings.setLastArticlesUpdate();
                    }, this::unableToUpdateMessage);
        }
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
    }*/
}