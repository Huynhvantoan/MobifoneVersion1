
package com.toan_itc.tn.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@Generated("org.jsonschema2pojo")
public class Menu extends RealmObject{
    @PrimaryKey
    @SerializedName("idMenu")
    @Expose
    private String idMenu;
    @SerializedName("TenMenu")
    @Expose
    private String TenMenu;
    @SerializedName("link")
    @Expose
    private String link;

    /**
     * 
     * @return
     *     The idMenu
     */
    public String getIdMenu() {
        return idMenu;
    }

    /**
     * 
     * @param idMenu
     *     The idMenu
     */
    public void setIdMenu(String idMenu) {
        this.idMenu = idMenu;
    }

    /**
     * 
     * @return
     *     The TenMenu
     */
    public String getTenMenu() {
        return TenMenu;
    }

    /**
     * 
     * @param TenMenu
     *     The TenMenu
     */
    public void setTenMenu(String TenMenu) {
        this.TenMenu = TenMenu;
    }

    /**
     * 
     * @return
     *     The link
     */
    public String getLink() {
        return link;
    }

    /**
     * 
     * @param link
     *     The link
     */
    public void setLink(String link) {
        this.link = link;
    }

}
