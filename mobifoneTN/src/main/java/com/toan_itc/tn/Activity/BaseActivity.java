package com.toan_itc.tn.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.toan_itc.tn.Api.RestClient;
import com.toan_itc.tn.Fragment.DichvukhacFragment;
import com.toan_itc.tn.Fragment.GopYFragment;
import com.toan_itc.tn.Fragment.KhoSoFragment;
import com.toan_itc.tn.Fragment.LienHeFragment;
import com.toan_itc.tn.Fragment.MainFragment;
import com.toan_itc.tn.Fragment.TheCaoFragment;
import com.toan_itc.tn.Fragment.ThongtinKHFragment;
import com.toan_itc.tn.Fragment.ThuCuocFragment;
import com.toan_itc.tn.Fragment.UpAnhFragment;
import com.toan_itc.tn.Lib.ripeffect.SystemBarTintManager;
import com.toan_itc.tn.Model.Menu;
import com.toan_itc.tn.Network.ApiController;
import com.toan_itc.tn.R;
import com.toan_itc.tn.Service.MenuService;
import com.toan_itc.tn.Utils.Utils;

import java.util.List;


/**
 * Created by toan.it on 1/23/16.
 */
public class BaseActivity extends AppCompatActivity{
    private static final int PROFILE_SETTING = 1;
    private AccountHeader mAccountHeader = null;
    private Drawer mDrawer = null;
    private Toolbar mToolbar;
    private static long back_pressed;
    private MenuService mMenuService;
    private List<Menu> mMenuList;
    private int sreen=1;
    @SuppressLint("CommitTransaction")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.add(R.id.layout_container, new MainFragment(this), MainFragment.class.getName());
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
            SystemBarTintManager tintManager=new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(Color.parseColor("#009688"));
        }
        //toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeButtonEnabled(true);
                actionBar.setTitle(R.string.toolbar_title);
            }
        }
        RestClient restClient=new RestClient(this);
        mMenuService = new MenuService(this,restClient);
        mMenuService.getmenu()
                .subscribe(menus -> this.mMenuList=menus);
        setUpDrawerLayout(savedInstanceState);
        if(this.mMenuList.size()>0)
        add_menu(this.mMenuList,savedInstanceState);
    }
    private void setUpDrawerLayout(Bundle savedInstanceState){
        try{
            SharedPreferences sharedPreferences = this.getSharedPreferences(ApiController.APPTN, Context.MODE_PRIVATE);
            String avatar = sharedPreferences.getString(ApiController.Avatar, "");
            if (avatar.equalsIgnoreCase("")) {
                avatar = "http://res.vtc.vn/media/vtcnews/2014/07/27/y-nghia-logo-Mobifone.jpg";
            }
            IProfile profile = new ProfileDrawerItem().withName(sharedPreferences.getString(ApiController.Phone, "Không rõ.")).withEmail(sharedPreferences.getString(ApiController.Username, "Không rõ.")).withIcon(avatar).withIdentifier(100);
            mAccountHeader = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withHeaderBackground(R.drawable.material_bg_account)
                    .addProfiles(profile)
                    .withTranslucentStatusBar(true)
                    .withOnAccountHeaderListener((view, profile1, current) -> {
                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
                        if (profile1 instanceof IDrawerItem && profile1.getIdentifier() == PROFILE_SETTING) {
                            int count = 100 + mAccountHeader.getProfiles().size() + 1;
                            IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Batman" + count).withEmail("batman" + count + "@gmail.com").withIcon(R.mipmap.ic_launcher).withIdentifier(count);
                            if (mAccountHeader.getProfiles() != null) {
                                //we know that there are 2 setting elements. set the new profile above them ;)
                                mAccountHeader.addProfile(newProfile, mAccountHeader.getProfiles().size() - 2);
                            } else {
                                mAccountHeader.addProfiles(newProfile);
                            }
                        }
                        //Intent i = new Intent(activity, About_Activity.class);
                        // activity.startActivity(i);
                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    })
                    .withTranslucentStatusBar(true)
                    .withProfileImagesClickable(true)
                    .withSavedInstance(savedInstanceState)
                    .build();
            menu_add(savedInstanceState);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private  void menu_add(Bundle bundle){
        mMenuService.updatemenu()
                .subscribe(menus ->{
            this.mMenuList = menus;
            add_menu(this.mMenuList,bundle);
        }, this::unableToUpdateMessage);
    }
    private void unableToUpdateMessage(Throwable throwable) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.toolbar), R.string.error_connect, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        TextView snackbarTextView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        if(Utils.isAndroid6())
            snackbarTextView.setTextColor(ContextCompat.getColor(this,R.color.white));
        else
            snackbarTextView.setTextColor(getResources().getColor(R.color.white));
        snackbar.show();
    }
    private void add_menu(final List<Menu> menuList,Bundle bundle){
         mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withHasStableIds(true)
                .withAccountHeader(mAccountHeader)
                .withFullscreen(true)
                .withTranslucentStatusBar(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(menuList.get(0).getTenMenu()).withIcon(R.drawable.hoamang).withIdentifier(1),
                        new SecondaryDrawerItem().withName("Cá Nhân").withLevel(2).withIcon(R.drawable.hoamang).withIdentifier(2000),
                        new SecondaryDrawerItem().withName("Doanh Nghiệp").withLevel(2).withIcon(R.drawable.hoamang).withIdentifier(2001),
                        new PrimaryDrawerItem().withName(menuList.get(1).getTenMenu()).withIcon(R.drawable.thucuoc).withIdentifier(2),
                        new PrimaryDrawerItem().withName(menuList.get(2).getTenMenu()).withIcon(R.drawable.thecao).withIdentifier(3),
                        new PrimaryDrawerItem().withName(menuList.get(3).getTenMenu()).withIcon(R.drawable.dichvukhac).withIdentifier(4),
                        new PrimaryDrawerItem().withName(menuList.get(4).getTenMenu()).withIcon(R.drawable.thongtinkh).withIdentifier(5),
                        new PrimaryDrawerItem().withName(menuList.get(5).getTenMenu()).withIcon(R.drawable.gopy).withIdentifier(6),
                        new PrimaryDrawerItem().withName("Liên Hệ").withIcon(R.drawable.lienhe).withIdentifier(7))
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    //check if the drawerItem is set.
                    //there are different reasons for the drawerItem to be null
                    //--> click on the header
                    //--> click on the footer
                    //those items don't contain a drawerItem
                    FragmentManager fragmentManager=getSupportFragmentManager();
                    FragmentTransaction transaction=fragmentManager.beginTransaction();
                    if (drawerItem != null) {
                        if (drawerItem.getIdentifier() == 1) {
                            sreen=ApiController.MENU1;
                            transaction.replace(R.id.layout_container, new MainFragment(this),MainFragment.class.getName());
                        } else if (drawerItem.getIdentifier() == 2) {
                            sreen=ApiController.MENU2;
                            transaction.replace(R.id.layout_container, new ThuCuocFragment(this,sreen),ThuCuocFragment.class.getName());
                        } else if (drawerItem.getIdentifier() == 3) {
                            sreen=ApiController.MENU3;
                            transaction.replace(R.id.layout_container, new TheCaoFragment(this,sreen), TheCaoFragment.class.getName());
                        } else if (drawerItem.getIdentifier() == 4) {
                            sreen=ApiController.MENU4;
                            transaction.replace(R.id.layout_container, new DichvukhacFragment(this,sreen), DichvukhacFragment.class.getName());
                        } else if (drawerItem.getIdentifier() == 5) {
                            sreen=0;
                            transaction.replace(R.id.layout_container, new ThongtinKHFragment(this), ThongtinKHFragment.class.getName());
                        } else if (drawerItem.getIdentifier() == 6) {
                            sreen=0;
                            transaction.replace(R.id.layout_container, new GopYFragment(this), GopYFragment.class.getName());
                        } else if (drawerItem.getIdentifier() == 7) {
                            sreen=0;
                           // transaction.replace(R.id.layout_container, new LienHeFragment(), LienHeFragment.class.getName());
                            transaction.replace(R.id.layout_container, new UpAnhFragment(), UpAnhFragment.class.getName());
                        }else if (drawerItem.getIdentifier() == 2000) {
                            sreen=ApiController.MENU1;
                            transaction.replace(R.id.layout_container, new KhoSoFragment(this,sreen),KhoSoFragment.class.getName());
                        } else if (drawerItem.getIdentifier() == 2001) {
                            sreen=ApiController.MENU5;
                            transaction.replace(R.id.layout_container, new KhoSoFragment(this,sreen),KhoSoFragment.class.getName());
                        }
                        if (transaction != null) {
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    }

                    return false;
                })
                .withSavedInstance(bundle)
                .withShowDrawerOnFirstLaunch(true)
                .build();
    }
    protected boolean isNavDrawerOpen() {
        return mDrawer != null && mDrawer.isDrawerOpen();
    }

    protected void closeNavDrawer() {
        if (mDrawer != null && mDrawer.isDrawerOpen()) {
            mDrawer.closeDrawer();
        }
    }

    protected void openDrawer() {
        if (mDrawer != null)
            mDrawer.openDrawer();
    }
    @Override
    public void onBackPressed() {
        _removeWorkerFragments();
        if (mDrawer != null && mDrawer.isDrawerOpen()) {
            mDrawer.closeDrawer();
        }else{
            Exit();
        }
    }
    private void _removeWorkerFragments() {
        Fragment frag = getSupportFragmentManager()//
                .findFragmentByTag(KhoSoFragment.class.getName());
        if (frag != null) {
            getSupportFragmentManager().beginTransaction().remove(frag).commit();
        }
        frag = getSupportFragmentManager()//
                .findFragmentByTag(ThuCuocFragment.class.getName());
        if (frag != null) {
            getSupportFragmentManager().beginTransaction().remove(frag).commit();
        }
        frag = getSupportFragmentManager()//
                .findFragmentByTag(TheCaoFragment.class.getName());
        if (frag != null) {
            getSupportFragmentManager().beginTransaction().remove(frag).commit();
        }
        frag = getSupportFragmentManager()//
                .findFragmentByTag(DichvukhacFragment.class.getName());
        if (frag != null) {
            getSupportFragmentManager().beginTransaction().remove(frag).commit();
        }
        frag = getSupportFragmentManager()//
                .findFragmentByTag(ThongtinKHFragment.class.getName());
        if (frag != null) {
            getSupportFragmentManager().beginTransaction().remove(frag).commit();
        }
        frag = getSupportFragmentManager()//
                .findFragmentByTag(GopYFragment.class.getName());
        if (frag != null) {
            getSupportFragmentManager().beginTransaction().remove(frag).commit();
        }
        frag = getSupportFragmentManager()//
                .findFragmentByTag(LienHeFragment.class.getName());
        if (frag != null) {
            getSupportFragmentManager().beginTransaction().remove(frag).commit();
        }
    }
    protected void Exit(){
        FragmentManager fm = getSupportFragmentManager();
        int count=fm.getBackStackEntryCount();
        if (count > 1) {
            fm.popBackStack();
        }else {
            if (back_pressed + 3000 > System.currentTimeMillis()) {
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            } else {
                Snackbar.make(mToolbar, "Bấm Back lần nữa để thoát ứng dụng!", Snackbar.LENGTH_LONG).show();
                back_pressed = System.currentTimeMillis();
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(mDrawer!=null&&mAccountHeader!=null) {
            //add the values which need to be saved from the drawer to the bundle
            outState = mDrawer.saveInstanceState(outState);
            //add the values which need to be saved from the accountHeader to the bundle
            outState = mAccountHeader.saveInstanceState(outState);
        }
        super.onSaveInstanceState(outState);
    }
  /*  @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            if(sreen!=0)
            EventBus.getDefault().post(ApiController.Refresh);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
    protected void setToolbarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    protected void setToolbarTitle(@StringRes int titleId) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(titleId);
        }
    }

    protected void setToolbarSubtitle(String subtitle) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle(subtitle);
        }
    }

    protected void setToolbarSubtitle(@StringRes int subtitleId) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle(subtitleId);
        }
    }
}
