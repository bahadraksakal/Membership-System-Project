package com.usersPackage;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "userBean")
@SessionScoped
// bazı uygun olmayan static tanımlamalar yapılmıştır bunlar örnek temsil etmesi amacıyla kullanışmıştır.
public class userBean implements Serializable{
    private String ad;
    private String sifre;
    private String mail;
    private int ID;   
    private boolean renderFlag=false;
    private boolean kayit_acilidimi=false;
    private static List<userBean> users;
    private static final Locale local = new Locale("tr", "TR");
    
    public void uyeDuzenle(){
        DB_user.uyeyi_duzenle(ID, ad, sifre, mail);
    }
    public Locale getLocale(){
        return local;
    }
    public String kayit_Ac(){
        this.kayit_acilidimi=DB_user.set_DB_users(ad, sifre, mail);
        
        return "index";
    }
    public boolean isKayit_acilidimi() {
        return kayit_acilidimi;
    }

    public void setKayit_acilidimi(boolean kayit_acilidimi) {
        this.kayit_acilidimi = kayit_acilidimi;
    }
    public boolean isRenderFlag() {
        return DB_user.isFlagUyari();
    }

    public void setRenderFlag(boolean renderFlag) {
        this.renderFlag = renderFlag;
    }
    
    
    public List<userBean> getUsers() {
        this.users = DB_user.users_list();
        return users;
    }

    public void setUsers() {
        this.users = DB_user.users_list();
    }
    public userBean() {
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public String girisYap(){    
        System.out.println("ad:"+ad);
        String sonuc=DB_user.get_DB_users_validation(ad, sifre);
        if(sonuc.compareTo("uye_page")==0){
            
            try {
                ID=DB_user.getDB_USERS().getInt(1);
                ad=DB_user.getDB_USERS().getString(2);
        //            sifre=DB_user.getDB_USERS().getString(3);
                mail=DB_user.getDB_USERS().getString(4);
            } catch (SQLException ex) {
                DB_user.showError(ex);
            } 
        }
        
        return sonuc;
    }
    public String closeAll(){
        String komut = DB_user.kill();
        return komut;
    }
    public void uyesil(){
        DB_user.uye_delete(ID);
    }
    
    
}
