package com.toan_itc.tn.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.toan_itc.tn.Network.ApiController;
import com.toan_itc.tn.R;


/**
 * Created by huynh on mobi10/29/2015.
 */
public class DrawerNavigation {
    private static final int PROFILE_SETTING = 1;
    private AccountHeader headerResult;
    public DrawerNavigation(final AppCompatActivity activity, Bundle savedInstanceState, final AccountHeader header, Drawer result, final Toolbar mToolbar) {
      try {
          SharedPreferences sharedPreferences = activity.getSharedPreferences(ApiController.APPTN, Context.MODE_PRIVATE);
          String avatar = sharedPreferences.getString(ApiController.Avatar, "");
          if (avatar.equalsIgnoreCase("")) {
              avatar = "https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xfa1/v/t1.0-1/p160x160/11751426_794373917343882_7256246783339720174_n.jpg?oh=f85bac5b4c6012a9d4e6011fc12cd9ff&oe=56D66317&__gda__=1460715166_7369822a7cade154d838c306a97e2f19";
          }
          IProfile profile = new ProfileDrawerItem().withName(sharedPreferences.getString(ApiController.Username, "Không rõ.")).withEmail(sharedPreferences.getString(ApiController.User, "Không rõ.")).withIcon(avatar).withIdentifier(100);
          this.headerResult = header;
          headerResult = new AccountHeaderBuilder()
                  .withActivity(activity)
                  .withHeaderBackground(R.drawable.material_bg_account)
                  .addProfiles(profile)
                  .withTranslucentStatusBar(true)
                  .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                      @Override
                      public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                          //sample usage of the onProfileChanged listener
                          //if the clicked item has the identifier 1 add a new profile ;)
                          if (profile instanceof IDrawerItem && profile.getIdentifier() == PROFILE_SETTING) {
                              int count = 100 + headerResult.getProfiles().size() + 1;
                              IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Batman" + count).withEmail("batman" + count + "@gmail.com").withIcon(R.mipmap.ic_launcher).withIdentifier(count);
                              if (headerResult.getProfiles() != null) {
                                  //we know that there are 2 setting elements. set the new profile above them ;)
                                  headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
                              } else {
                                  headerResult.addProfiles(newProfile);
                              }
                          }
                          //Intent i = new Intent(activity, About_Activity.class);
                         // activity.startActivity(i);
                          //false if you have not consumed the event and it should close the drawer
                          return false;
                      }
                  })
                  .withTranslucentStatusBar(true)
                  .withProfileImagesClickable(true)
                  .withSavedInstance(savedInstanceState)
                  .build();
         // new MenuTask(activity,result,savedInstanceState,headerResult,mToolbar).execute();
      }catch (Exception e){
          e.printStackTrace();
      }
    }
}
