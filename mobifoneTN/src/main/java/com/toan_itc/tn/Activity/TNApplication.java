package com.toan_itc.tn.Activity;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.toan_itc.tn.R;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by ToanITC on 12/20/2015.
 */
public class TNApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    initRealm();
    initGlide();
  }
  private void initRealm(){
    Realm.init(this);
    RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
            //.name("mytn.realm")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build();
    Realm.setDefaultConfiguration(realmConfiguration);
  }
  private void initGlide(){
    //initialize and create the image loader logic
    DrawerImageLoader.init(new AbstractDrawerImageLoader() {
      @Override
      public void set(ImageView imageView, Uri uri, Drawable placeholder) {
        Glide.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
      }

      @Override
      public void cancel(ImageView imageView) {
        Glide.clear(imageView);
      }

      @Override
      public Drawable placeholder(Context ctx, String tag) {
        if (DrawerImageLoader.Tags.PROFILE.name().equals(tag)) {
          return DrawerUIUtils.getPlaceHolder(ctx);
        } else if (DrawerImageLoader.Tags.ACCOUNT_HEADER.name().equals(tag)) {
          return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(com.mikepenz.materialdrawer.R.color.primary).sizeDp(56);
        } else if ("customUrlItem".equals(tag)) {
          return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(R.color.md_red_500).sizeDp(56);
        }
        return super.placeholder(ctx, tag);
      }
    });
  }
}
