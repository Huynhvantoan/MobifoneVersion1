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
import com.toan_itc.tn.Adapter.DVCVAdapter;
import com.toan_itc.tn.Api.RestClient;
import com.toan_itc.tn.Model.DStinCVQT;
import com.toan_itc.tn.R;
import com.toan_itc.tn.Service.ApiService;
import com.toan_itc.tn.Utils.Settings;
import com.toan_itc.tn.Utils.Utils;

import java.util.Date;
import java.util.List;

/**
 * Created by Toan.itc on 12/23/2015.
 */
@SuppressLint("ValidFragment")
public class RecyclerView3Fragment extends Fragment implements DVCVAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String LOG_TAG = RecyclerViewFragment.class.getSimpleName();
    private static final String ADAPTER_SELECTED_ITEM = "ADAPTER_SELECTED_ITEM";
    private static final int ARTICLES_BEFORE_MORE = 3;
    private static final int FIVE_MINUTES = 5 * 60 * 1000;
    private ApiService mCaNhanService;
    private Settings settings;
    private BaseActivity activity;
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
    private DVCVAdapter mCaNhanAdapter;
    private FragmentCallback3 fragmentCallback;
    /**
     * The list of all articles.
     */
    private List<DStinCVQT> mKhuyenmaiRealmList;
    private SuperRecyclerView recyclerView;
    /**
     * This indicates if more older articles are currently loading
     */
    private boolean isLoadingMore = false;
    int screen=1;
    public interface FragmentCallback3 {
        void onItemSelected3(String link);
        boolean isTwoPane3();
    }
    public RecyclerView3Fragment() {
    }

    public RecyclerView3Fragment(BaseActivity activity, int screen) {
        this.activity = activity;
        this.screen=screen;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Activities containing this fragment must implement its callbacks.
        if (!(context instanceof RecyclerViewFragment.FragmentCallback)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        fragmentCallback = (FragmentCallback3) context;
        RestClient api = RestClient.with(context);
        mCaNhanService = new ApiService(context, api);
        settings = new Settings(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.updateAll();
        mCaNhanService.getdvcv()
                .subscribe(dStinCVQTs -> this.mKhuyenmaiRealmList = dStinCVQTs);
        mCaNhanAdapter = new DVCVAdapter(activity, this.mKhuyenmaiRealmList, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.fragment_recyclerview3, container, false);
        recyclerView=(SuperRecyclerView)fragmentView.findViewById(R.id.recyclerview_3);
        layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mCaNhanAdapter);
        recyclerView.setRefreshListener(this);
        recyclerView.setRefreshingColorResources(
                R.color.refresh1,
                R.color.refresh2,
                R.color.refresh3,
                R.color.refresh3
        );
        // Only update articles on start if it hasn't been done in the past 5 minutes.
        if (new Date().getTime() - settings.getLastArticlesUpdate() > FIVE_MINUTES) {
            if ((mKhuyenmaiRealmList!=null&&mKhuyenmaiRealmList.size()> 0)) {
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
        if (savedInstanceState != null) {
            mCaNhanAdapter.setSelectedItem(savedInstanceState.getInt(ADAPTER_SELECTED_ITEM, -1));
        }
        mCaNhanAdapter.setTwoPane(false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ADAPTER_SELECTED_ITEM, mCaNhanAdapter.getSelectedItem());
    }
    @Override
    public void onItemClick(String link) {
        fragmentCallback.onItemSelected3(link);
    }
    @Override
    public void onRefresh() {
        this.updateAll();
    }

    private void updateAll() {
        mCaNhanService.updatedvcv()
                .subscribe(dStinCVQTList -> {
                    this.mKhuyenmaiRealmList = dStinCVQTList;
                    mCaNhanAdapter.notifyDataSetChanged();
                    refesh_off();
                    settings.setLastArticlesUpdate();
                }, this::unableToUpdateMessage);
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
   /* @Subscribe
    public void onEventMainThread(Integer screen) {
        if(screen!=0) {
            mCaNhanService.updatedvcv()
                    .subscribe(dStinCVQTList -> {
                        this.mKhuyenmaiRealmList = dStinCVQTList;
                        mCaNhanAdapter.notifyDataSetChanged();
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