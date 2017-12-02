package com.toan_itc.tn.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings.Secure;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.toan_itc.tn.Api.RestClient;
import com.toan_itc.tn.Fragment.RecyclerView2Fragment;
import com.toan_itc.tn.Fragment.RecyclerView3Fragment;
import com.toan_itc.tn.Fragment.RecyclerViewFragment;
import com.toan_itc.tn.Fragment.WebviewFragment;
import com.toan_itc.tn.Network.ApiController;
import com.toan_itc.tn.Pushnotification.RegistrationIntentService;
import com.toan_itc.tn.R;
import com.toan_itc.tn.Service.PostDataService;

public class MainActivity extends BaseActivity implements RecyclerViewFragment.FragmentCallback, RecyclerView2Fragment.FragmentCallback2, RecyclerView3Fragment.FragmentCallback3 {
    private BaseActivity activity = this;
    private boolean twoPane;
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static MainActivity instance;
    private static PostDataService mPostDataService;
    private static String android_id;
    private SharedPreferences sharedPref;
    private static SharedPreferences.Editor editor;
    private BroadcastReceiver mBroadcastReceiver;
    private MaterialDialog mMaterialDialog=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
            instance=this;
            sharedPref = this.getSharedPreferences(ApiController.APPTN, Context.MODE_PRIVATE);
            editor=sharedPref.edit();
            boolean check_token=sharedPref.getBoolean(ApiController.PUSH_NOTIFICATION,false);
            if (isEnablePlayService()&&!check_token) {
                // Register GCM with this app
                Intent mIntent = new Intent(this, RegistrationIntentService.class);
                startService(mIntent);
                RestClient api = RestClient.with(activity);
                mPostDataService = new PostDataService(activity, api);
                android_id = Secure.getString(getContentResolver(),Secure.ANDROID_ID);
            }
            Receiver();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (findViewById(R.id.article_detail_container) != null) {
            twoPane = true;
        }
    }
    public void call_click(View view) {
        try {
            if (mMaterialDialog != null) {
                mMaterialDialog.dismiss();
            }
            mMaterialDialog = new MaterialDialog.Builder(activity)
                    .title("Hot line")
                    .content("Bạn có muốn gọi cho Hotline: 0903669889 không!")
                    .autoDismiss(true)
                    .cancelable(false)
                    .positiveText("Gọi")
                    .negativeText("Không")
                    .onPositive((dialog, which) -> {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:0903669889"));
                        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(activity, "Bạn chưa cấp quyền cho chức năng này!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        startActivity(callIntent);
                    })
                    .onNegative((materialDialog1, dialogAction) -> materialDialog1.dismiss())
                    .buttonRippleColorRes(R.color.primary_light)
                    .positiveColorRes(R.color.primary_dark)
                    .negativeColorRes(R.color.md_black_1000)
                    .iconRes(R.mipmap.ic_launcher)
                    .build();
            mMaterialDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private boolean isEnablePlayService() {
        GoogleApiAvailability mGoogleAPI = GoogleApiAvailability.getInstance();
        int mResultCodeAPI = mGoogleAPI.isGooglePlayServicesAvailable(this);
        if (mResultCodeAPI != ConnectionResult.SUCCESS) {
            if (mGoogleAPI.isUserResolvableError(mResultCodeAPI)) {
                mGoogleAPI.getErrorDialog(this, mResultCodeAPI, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Snackbar.make(findViewById(R.id.toolbar),"Vui lòng cài đặt google service để sử dụng được Push!",Snackbar.LENGTH_LONG).show();
            }
            return false;
        }

        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        if (isNavDrawerOpen()) {
            closeNavDrawer();
        } else {
            Exit();
        }
    }

    @Override
    public void onItemSelected(String link) {
        if (twoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(WebviewFragment.EXTRA_URL, link);
            WebviewFragment fragment = new WebviewFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.article_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(activity, DetailsActivity.class);
            intent.putExtra(ApiController.EXTRA_LINK, link);
            startActivity(intent);
        }
    }

    @Override
    public void onItemSelected2(String link) {
        if (twoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(WebviewFragment.EXTRA_URL, link);
            WebviewFragment fragment = new WebviewFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.article_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(activity, DetailsActivity.class);
            intent.putExtra(ApiController.EXTRA_LINK, link);
            startActivity(intent);
        }
    }

    @Override
    public boolean isTwoPane2() {
        return twoPane;
    }

    @Override
    public boolean isTwoPane() {
        return twoPane;
    }

    @Override
    public void onItemSelected3(String link) {
        if (twoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(WebviewFragment.EXTRA_URL, link);
            WebviewFragment fragment = new WebviewFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.article_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(activity, DetailsActivity.class);
            intent.putExtra(ApiController.EXTRA_LINK, link);
            startActivity(intent);
        }
    }

    @Override
    public boolean isTwoPane3() {
        return twoPane;
    }
    public static void updateCredentials(final String mRegisterId) {
        try {
            if (instance !=null) {
                instance.runOnUiThread(() -> mPostDataService.posttoken(android_id, mRegisterId)
                        .subscribe(postData -> { postData.getMessage();
                            Log.wtf("android_id="+android_id,"mRegisterId="+mRegisterId);
                            editor.putBoolean(ApiController.PUSH_NOTIFICATION, true);
                            editor.commit();
                        },Throwable::printStackTrace));
            }
        } catch (Exception e) {
            Log.d("MainActivity", "Failed to update UI Thread on MainActivity: " + e.getMessage());
        }
    }
    private void Receiver() {
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    if (intent.getAction().equalsIgnoreCase(ApiController.PUSH_NOTIFICATION)) {
                        acquireWakeLock(getApplicationContext());
                        String title = intent.getStringExtra(ApiController.PUSH_TITLE);
                        String message = intent.getStringExtra(ApiController.PUSH_MESSAGE);
                        String data = intent.getStringExtra(ApiController.PUSH_DATA);
                        showNotification(title, message, data);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
    private void showNotification(String title,String mesage, String data) {
        if (mMaterialDialog != null) {
            mMaterialDialog.dismiss();
        }
        mMaterialDialog = new MaterialDialog.Builder(activity)
                .title(title)
                .content(mesage)
                .autoDismiss(true)
                .cancelable(false)
                .positiveText("Xem")
                .negativeText("Không")
                .onPositive((dialog, which) -> {
                    Intent intent = new Intent(activity, DetailsActivity.class);
                    intent.putExtra(ApiController.EXTRA_LINK, data);
                    startActivity(intent);
                })
                .onNegative((materialDialog1, dialogAction) -> materialDialog1.dismiss())
                .buttonRippleColorRes(R.color.primary_light)
                .positiveColorRes(R.color.primary_dark)
                .negativeColorRes(R.color.md_black_1000)
                .iconRes(R.mipmap.ic_launcher)
                .build();
        mMaterialDialog.show();
    }
    private PowerManager.WakeLock wakeLock;
    @SuppressWarnings("deprecation")
    @SuppressLint("Wakelock")
    public void acquireWakeLock(Context context) {
        if (wakeLock != null)
            wakeLock.release();
        PowerManager pm = (PowerManager) context
                .getSystemService(Context.POWER_SERVICE);

        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, "WakeLock");

        wakeLock.acquire();
    }
    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mBroadcastReceiver, new IntentFilter(ApiController.PUSH_NOTIFICATION));
        try {
            Intent intent = getIntent();
            String message = intent.getStringExtra(ApiController.PUSH_MESSAGE);
            String title1 = sharedPref.getString(ApiController.PUSH_MESSAGE, null);
            String message1 = sharedPref.getString(ApiController.PUSH_MESSAGE, null);
            String data1 = sharedPref.getString(ApiController.PUSH_DATA, null);
            if (!message.equalsIgnoreCase("") && data1 != null) {
                showNotification(title1, message1,data1);
                editor.putString(ApiController.PUSH_DATA, null);
                editor.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public void onDestroy() {
        try {
            NotificationManager notificationManager = (NotificationManager) activity
                    .getSystemService(NOTIFICATION_SERVICE);
            notificationManager.cancelAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
