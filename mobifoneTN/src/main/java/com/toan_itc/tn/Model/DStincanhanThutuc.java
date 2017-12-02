
package com.toan_itc.tn.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DStincanhanThutuc{
    @SerializedName("idHoaMang")
    @Expose
    private String idHoaMang;
    @SerializedName("idCaNhan")
    @Expose
    private String idCaNhan;
    @SerializedName("TenTin")
    @Expose
    private String TenTin;
    @SerializedName("idTin")
    @Expose
    private String idTin;
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
     *     The idHoaMang
     */
    public String getIdHoaMang() {
        return idHoaMang;
    }

    /**
     * 
     * @param idHoaMang
     *     The idHoaMang
     */
    public void setIdHoaMang(String idHoaMang) {
        this.idHoaMang = idHoaMang;
    }

    /**
     * 
     * @return
     *     The idCaNhan
     */
    public String getIdCaNhan() {
        return idCaNhan;
    }

    /**
     * 
     * @param idCaNhan
     *     The idCaNhan
     */
    public void setIdCaNhan(String idCaNhan) {
        this.idCaNhan = idCaNhan;
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
