
package com.toan_itc.tn.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@Generated("org.jsonschema2pojo")
public class DStinThecao extends RealmObject{
    @SerializedName("idTheCao")
    @Expose
    private int idTheCao;
    @PrimaryKey
    @SerializedName("idTin")
    @Expose
    private String idTin;
    @SerializedName("TenTin")
    @Expose
    private String TenTin;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("tomtat")
    @Expose
    private String tomtat;
    @SerializedName("link")
    @Expose
    private String link;

    /**
     * 
     * @return
     *     The idTheCao
     */
    public int getIdTheCao() {
        return idTheCao;
    }

    /**
     * 
     * @param idTheCao
     *     The idTheCao
     */
    public void setIdTheCao(int idTheCao) {
        this.idTheCao = idTheCao;
    }

    /**
     * 
     * @return
     *     The idTin
     */
    public String getIdTin() {
        return idTin;
    }

    /**
     * 
     * @param idTin
     *     The idTin
     */
    public void setIdTin(String idTin) {
        this.idTin = idTin;
    }

    /**
     * 
     * @return
     *     The TenTin
     */
    public String getTenTin() {
        return TenTin;
    }

    /**
     * 
     * @param TenTin
     *     The TenTin
     */
    public void setTenTin(String TenTin) {
        this.TenTin = TenTin;
    }

    /**
     * 
     * @return
     *     The thumb
     */
    public String getThumb() {
        return thumb;
    }

    /**
     * 
     * @param thumb
     *     The thumb
     */
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    /**
     * 
     * @return
     *     The tomtat
     */
    public String getTomtat() {
        return tomtat;
    }

    /**
     * 
     * @param tomtat
     *     The tomtat
     */
    public void setTomtat(String tomtat) {
        this.tomtat = tomtat;
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
