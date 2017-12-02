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
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.toan_itc.tn.Activity.BaseActivity;
import com.toan_itc.tn.Adapter.CaNhanKmAdapter;
import com.toan_itc.tn.Adapter.DVmobiAdapter;
import com.toan_itc.tn.Adapter.DoanhnghiepThutucAdapter;
import com.toan_itc.tn.Adapter.ThecaoAdapter;
import com.toan_itc.tn.Adapter.ThuCuocAdapter;
import com.toan_itc.tn.Api.RestClient;
import com.toan_itc.tn.Model.DStinKMthucuoc;
import com.toan_itc.tn.Model.DStinMobileInternet;
import com.toan_itc.tn.Model.DStinThecao;
import com.toan_itc.tn.Model.DStincanhanKhuyenmaiRealm;
import com.toan_itc.tn.Model.DStindoanhnghiepThutuc;
import com.toan_itc.tn.R;
import com.toan_itc.tn.Service.ApiService;
import com.toan_itc.tn.Service.PostDataService;
import com.toan_itc.tn.Utils.DividerItemDecoration;
import com.toan_itc.tn.Utils.Settings;
import com.toan_itc.tn.Utils.Utils;

import java.util.Date;
import java.util.List;

/**
 * Created by Toan.itc on 12/23/2015.
 */
@SuppressLint("ValidFragment")
public class RecyclerViewFragment extends Fragment implements CaNhanKmAdapter.OnItemClickListener,ThuCuocAdapter.OnItemClickListener,DoanhnghiepThutucAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener,ThecaoAdapter.OnItemClickListener,DVmobiAdapter.OnItemClickListener{
    public static final String LOG_TAG = RecyclerViewFragment.class.getSimpleName();
    private static final String ADAPTER_SELECTED_ITEM = "ADAPTER_SELECTED_ITEM";
    private static final int ARTICLES_BEFORE_MORE = 3;
    private static final int FIVE_MINUTES = 5 * 60 * 1000;
    private ApiService mCaNhanService;
    private PostDataService mPostDataService;
    private Settings settings;
    private BaseActivity activity;
    EditText txt_sdt,txt_diachi;
    public interface FragmentCallback {
        void onItemSelected(String id);
        boolean isTwoPane();
    }
    /**
     * The fragment's current OnItemSelectedCallback object, which is notified of list item
     * clicks.
     */
    private FragmentCallback fragmentCallback;
    /**
     * The fragment view that gets inflated inside onCreateView.
     */
    private View fragmentView;
    /**
     * The RecyclerView adapter that has all the articles.
     */
    private CaNhanKmAdapter mCaNhanAdapter;
    private DoanhnghiepThutucAdapter mDoanhnghiepThutucAdapter;
    private ThecaoAdapter mThecaoAdapter;
    private DVmobiAdapter mDVmobiAdapter;
    private ThuCuocAdapter mThuCuocAdapter;
    /**
     * The list of all articles.
     */
    private List<DStincanhanKhuyenmaiRealm> mKhuyenmaiRealmList;
    private List<DStindoanhnghiepThutuc> mDStindoanhnghiepThutucList;
    private List<DStinThecao> mDStinThecaoList;
    private List<DStinMobileInternet> mDStinMobileInternets;
    private List<DStinKMthucuoc> mDStinKMthucuocList;
    /**
     * The LinearLayoutManager for the RecyclerView.
     */
    private LinearLayoutManager layoutManager;
    /**
     * This indicates if more older articles are currently loading
     */
    private boolean isLoadingMore = false;
    int screen=1;
    SuperRecyclerView recyclerView;
    public RecyclerViewFragment() {
    }

    public RecyclerViewFragment(BaseActivity activity,int screen) {
        this.activity=activity;
        this.screen=screen;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Activities containing this fragment must implement its callbacks.
        if (!(context instanceof FragmentCallback)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        fragmentCallback = (FragmentCallback) context;
        RestClient api = RestClient.with(context);
        mCaNhanService = new ApiService(context, api);
        mPostDataService = new PostDataService(context, api);
        settings = new Settings(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.updateAll();
        if (screen == 1) {
            mCaNhanService.getcanhan()
                    .subscribe(stincanhanKhuyenmaiRealms -> this.mKhuyenmaiRealmList = stincanhanKhuyenmaiRealms);
            mCaNhanAdapter = new CaNhanKmAdapter(activity, this.mKhuyenmaiRealmList, this);
        } else if (screen == 2) {
            mCaNhanService.getthucuockm()
                    .subscribe(dStinKMthucuocList -> this.mDStinKMthucuocList = dStinKMthucuocList);
            mThuCuocAdapter = new ThuCuocAdapter(activity, this.mDStinKMthucuocList, this);
        } else if (screen == 3) {
            mCaNhanService.getthecao()
                    .subscribe(dStinThecaos -> this.mDStinThecaoList = dStinThecaos);
            mThecaoAdapter = new ThecaoAdapter(activity, this.mDStinThecaoList, this);
        } else if (screen == 4) {
            mCaNhanService.getdvmobi()
                    .subscribe(dStinMobileInternetList -> this.mDStinMobileInternets = dStinMobileInternetList);
            mDVmobiAdapter = new DVmobiAdapter(activity, this.mDStinMobileInternets, this);
        } else if (screen == 5) {
            mCaNhanService.getttdoanhnghiep()
                    .subscribe(dStindoanhnghiepThutucList -> this.mDStindoanhnghiepThutucList = dStindoanhnghiepThutucList);
            mDoanhnghiepThutucAdapter = new DoanhnghiepThutucAdapter(activity, this.mDStindoanhnghiepThutucList, this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.fragment_recyclerview1, container, false);
        recyclerView=(SuperRecyclerView)fragmentView.findViewById(R.id.recyclerview_1);
        layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        if(screen==1){
            recyclerView.addItemDecoration(new DividerItemDecoration(activity, null));
            recyclerView.setAdapter(mCaNhanAdapter);}
        else if(screen==2){
            recyclerView.setAdapter(mThuCuocAdapter);}
        else if(screen==3){
            recyclerView.setAdapter(mThecaoAdapter);}
        else if(screen==4){
            recyclerView.setAdapter(mDVmobiAdapter);}
        else if(screen==5){
            recyclerView.setAdapter(mDoanhnghiepThutucAdapter);}
        recyclerView.setRefreshListener(this);
        recyclerView.setRefreshingColorResources(
                R.color.refresh1,
                R.color.refresh2,
                R.color.refresh3,
                R.color.refresh3
        );
        // Only update articles on start if it hasn't been done in the past 5 minutes.
        if (new Date().getTime() - settings.getLastArticlesUpdate() > FIVE_MINUTES) {
            if ((mKhuyenmaiRealmList!=null&&mKhuyenmaiRealmList.size()>0||(mDStinThecaoList!=null&&mDStinThecaoList.size()>0)
                    ||(mDStinMobileInternets!=null&&mDStinMobileInternets.size()>0)||(mDStinKMthucuocList!=null&&mDStinKMthucuocList.size()>0)
                    ||(mDStindoanhnghiepThutucList!=null&&mDStindoanhnghiepThutucList.size()>0))) { // Don't show swipeToRefresh spinner if empty spinner already there.
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
        if (screen == 1) {
            if (savedInstanceState != null) {
                mCaNhanAdapter.setSelectedItem(savedInstanceState.getInt(ADAPTER_SELECTED_ITEM, -1));
            }
            mCaNhanAdapter.setTwoPane(false);
        } else if (screen == 2) {
            if (savedInstanceState != null) {
                mThuCuocAdapter.setSelectedItem(savedInstanceState.getInt(ADAPTER_SELECTED_ITEM, -1));
            }
            mThuCuocAdapter.setTwoPane(false);
        } else if (screen == 3) {
            if (savedInstanceState != null) {
                mThecaoAdapter.setSelectedItem(savedInstanceState.getInt(ADAPTER_SELECTED_ITEM, -1));
            }
            mThecaoAdapter.setTwoPane(false);
        } else if (screen == 4) {
            if (savedInstanceState != null) {
                mDVmobiAdapter.setSelectedItem(savedInstanceState.getInt(ADAPTER_SELECTED_ITEM, -1));
            }
            mDVmobiAdapter.setTwoPane(false);
        } else if (screen == 5) {
            if (savedInstanceState != null) {
                mDoanhnghiepThutucAdapter.setSelectedItem(savedInstanceState.getInt(ADAPTER_SELECTED_ITEM, -1));
            }
            mDoanhnghiepThutucAdapter.setTwoPane(false);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            if (screen == 1)
                outState.putInt(ADAPTER_SELECTED_ITEM, mCaNhanAdapter.getSelectedItem());
            else if (screen == 2)
                outState.putInt(ADAPTER_SELECTED_ITEM, mThuCuocAdapter.getSelectedItem());
            else if (screen == 3)
                outState.putInt(ADAPTER_SELECTED_ITEM, mThecaoAdapter.getSelectedItem());
            else if (screen == 4)
                outState.putInt(ADAPTER_SELECTED_ITEM, mDVmobiAdapter.getSelectedItem());
            else if (screen == 5)
                outState.putInt(ADAPTER_SELECTED_ITEM, mDoanhnghiepThutucAdapter.getSelectedItem());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onItemClick(String link) {
        fragmentCallback.onItemSelected(link);
    }
    @Override
    public void onItemClickDVMobi(String link) {
        fragmentCallback.onItemSelected(link);
    }
    @Override
    public void onItemClickThuCuoc(String link) {
        fragmentCallback.onItemSelected(link);
    }
    @Override
    public void onItemClickdntt(String link) {
        fragmentCallback.onItemSelected(link);
    }
    @Override
    public void onItemClickTheCao(String menhgia) {
        try {
            final String[] status = {null};
            MaterialDialog materialDialog = new MaterialDialog.Builder(getActivity())
                    .title("Nạp card")
                    .customView(R.layout.dialog_thecao, true)
                    .positiveText("Đồng Ý")
                    .negativeText("Không")
                    .onPositive((dialog, which) -> {
                        dialog.dismiss();
                        String sdt= txt_sdt.getText().toString();
                        String diachi=txt_diachi.getText().toString();
                        if(!sdt.equalsIgnoreCase("")&&!diachi.equalsIgnoreCase("")) {
                            mPostDataService.post_thecao(txt_sdt.getText().toString(), menhgia, txt_diachi.getText().toString())
                                    .subscribe(postData -> {
                                        status[0] = postData.getMessage();
                                        Snackbar.make(fragmentView,"Gửi thông tin thành công!",Snackbar.LENGTH_LONG).show();
                                    }, Throwable::printStackTrace);
                        }else{
                            Snackbar.make(fragmentView,"Mời bạn nhập chính xác và đầy đủ thông tin để nập card!",Snackbar.LENGTH_LONG).show();
                        }
                    })
                    .onNegative((materialDialog1, dialogAction) -> materialDialog1.dismiss())
                    .buttonRippleColorRes(R.color.primary_light)
                    .iconRes(R.mipmap.ic_launcher)
                    .positiveColorRes(R.color.primary_dark)
                    .build();
            txt_sdt = (EditText) materialDialog.getCustomView().findViewById(R.id.txt_sdt);
            txt_diachi = (EditText) materialDialog.getCustomView().findViewById(R.id.txt_diachi);
            materialDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onRefresh() {
        this.updateAll();
    }
    private void updateAll() {
        try {
            if (screen == 1) {
                mCaNhanService.updatecanhan()
                        .subscribe(stincanhanKhuyenmaiRealms -> {
                            this.mKhuyenmaiRealmList = stincanhanKhuyenmaiRealms;
                            mCaNhanAdapter.notifyDataSetChanged();
                            refesh_off();
                            settings.setLastArticlesUpdate();
                        }, this::unableToUpdateMessage);
            } else if (screen == 2) {
                mCaNhanService.updatethucuockm()
                        .subscribe(dStinKMthucuocList -> {
                            this.mDStinKMthucuocList = dStinKMthucuocList;
                            mThuCuocAdapter.notifyDataSetChanged();
                            refesh_off();
                            settings.setLastArticlesUpdate();
                        }, this::unableToUpdateMessage);
            } else if (screen == 3) {
                mCaNhanService.updatethecao()
                        .subscribe(dStinThecaos -> {
                            this.mDStinThecaoList = dStinThecaos;
                            mThecaoAdapter.notifyDataSetChanged();
                            refesh_off();
                            settings.setLastArticlesUpdate();
                        }, this::unableToUpdateMessage);
            } else if (screen == 4) {
                mCaNhanService.updatedvmobi()
                        .subscribe(dStinMobileInternetList -> {
                            this.mDStinMobileInternets = dStinMobileInternetList;
                            mDVmobiAdapter.notifyDataSetChanged();
                            refesh_off();
                            settings.setLastArticlesUpdate();
                        }, this::unableToUpdateMessage);
            } else if (screen == 5) {
                mCaNhanService.updatettdoanhnghiep()
                        .subscribe(dStindoanhnghiepThutucList -> {
                            this.mDStindoanhnghiepThutucList = dStindoanhnghiepThutucList;
                            mDoanhnghiepThutucAdapter.notifyDataSetChanged();
                            refesh_off();
                            settings.setLastArticlesUpdate();
                        }, this::unableToUpdateMessage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void refesh_off(){
        if(recyclerView!=null){
            recyclerView.getSwipeToRefresh().post(() -> recyclerView.getSwipeToRefresh().setRefreshing(false));
        }
    }
    private void unableToUpdateMessage(Throwable throwable) {
        Log.wtf("throwable=",throwable.getMessage()+"");
       // recyclerView.getSwipeToRefresh().post(() -> recyclerView.getSwipeToRefresh().setRefreshing(false));
        Snackbar snackbar = Snackbar.make(fragmentView, R.string.error_connect, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        TextView snackbarTextView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        if(Utils.isAndroid6())
            snackbarTextView.setTextColor(ContextCompat.getColor(activity,R.color.white));
        else
            snackbarTextView.setTextColor(activity.getResources().getColor(R.color.white));
        snackbar.show();
    }
    /*@Subscribe
    public void onEventMainThread(Integer screen) {
        try {
            if (screen == 1) {
                mCaNhanService.updatecanhan()
                        .subscribe(stincanhanKhuyenmaiRealms -> {
                            this.mKhuyenmaiRealmList = stincanhanKhuyenmaiRealms;
                            mCaNhanAdapter.notifyDataSetChanged();
                            refesh_off();
                            settings.setLastArticlesUpdate();
                        }, this::unableToUpdateMessage);
            } else if (screen == 2) {
                mCaNhanService.updatethucuockm()
                        .subscribe(dStinKMthucuocList -> {
                            this.mDStinKMthucuocList = dStinKMthucuocList;
                            mThuCuocAdapter.notifyDataSetChanged();
                            refesh_off();
                            settings.setLastArticlesUpdate();
                        }, this::unableToUpdateMessage);
            } else if (screen == 3) {
                mCaNhanService.updatethecao()
                        .subscribe(dStinThecaos -> {
                            this.mDStinThecaoList = dStinThecaos;
                            mThecaoAdapter.notifyDataSetChanged();
                            refesh_off();
                            settings.setLastArticlesUpdate();
                        }, this::unableToUpdateMessage);
            } else if (screen == 4) {
                mCaNhanService.updatedvmobi()
                        .subscribe(dStinMobileInternetList -> {
                            this.mDStinMobileInternets = dStinMobileInternetList;
                            mDVmobiAdapter.notifyDataSetChanged();
                            refesh_off();
                            settings.setLastArticlesUpdate();
                        }, this::unableToUpdateMessage);
            } else if (screen == 5) {
                mCaNhanService.updatettdoanhnghiep()
                        .subscribe(dStindoanhnghiepThutucList -> {
                            this.mDStindoanhnghiepThutucList = dStindoanhnghiepThutucList;
                            mDoanhnghiepThutucAdapter.notifyDataSetChanged();
                            refesh_off();
                            settings.setLastArticlesUpdate();
                        }, this::unableToUpdateMessage);
            }
        }catch (Exception e){
            e.printStackTrace();
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
