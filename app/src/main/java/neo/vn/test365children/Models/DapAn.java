package neo.vn.test365children.Models;

public class DapAn  {
    private String sName;
    private String sContent;
    private String sDapan_Traloi;
    private String sDapan_Dung;
    private boolean isClick;
    private String sImage;
    private int res_Image;

    public DapAn(String sName, String sContent, String sDapan_Traloi, String sDapan_Dung, boolean isClick, String sImage) {
        this.sName = sName;
        this.sContent = sContent;
        this.sDapan_Traloi = sDapan_Traloi;
        this.sDapan_Dung = sDapan_Dung;
        this.isClick = isClick;
        this.sImage = sImage;
    }

    public DapAn(String sName, String sContent, String sDapan_Traloi, String sDapan_Dung, boolean isClick, int res_Image) {
        this.sName = sName;
        this.sContent = sContent;
        this.sDapan_Traloi = sDapan_Traloi;
        this.sDapan_Dung = sDapan_Dung;
        this.isClick = isClick;
        this.res_Image = res_Image;
    }

    public int getRes_Image() {
        return res_Image;
    }

    public void setRes_Image(int res_Image) {
        this.res_Image = res_Image;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsContent() {
        return sContent;
    }

    public void setsContent(String sContent) {
        this.sContent = sContent;
    }

    public String getsDapan_Traloi() {
        return sDapan_Traloi;
    }

    public void setsDapan_Traloi(String sDapan_Traloi) {
        this.sDapan_Traloi = sDapan_Traloi;
    }

    public String getsDapan_Dung() {
        return sDapan_Dung;
    }

    public void setsDapan_Dung(String sDapan_Dung) {
        this.sDapan_Dung = sDapan_Dung;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public String getsImage() {
        return sImage;
    }

    public void setsImage(String sImage) {
        this.sImage = sImage;
    }
}
