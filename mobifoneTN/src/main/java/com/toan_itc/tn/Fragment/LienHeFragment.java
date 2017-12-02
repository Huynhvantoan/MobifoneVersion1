package com.toan_itc.tn.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toan_itc.tn.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by toan.it on 12/30/15.
 */
@SuppressLint("ValidFragment")
public class LienHeFragment extends Fragment {
    public LienHeFragment() {
        super();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lienhe, container, false);
        ButterKnife.bind(this,view);
        return view;
    }
    @OnClick(R.id.txt_email) public void send_email(){
        Intent i = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"vienthongthanhnhan@gmail.com"});
        startActivity(Intent.createChooser(i, "Gửi mail..."));
    }
    @OnClick(R.id.txt_phone) public void call_phone(){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:0901067979"));
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);
    }
    @OnClick(R.id.txt_hotline) public void call_hotline(){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:0903669889"));
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);
    }
    @OnClick(R.id.txt_website) public void open_website(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://vienthongthanhnhan.com/"));
        startActivity(browserIntent);
    }
}
