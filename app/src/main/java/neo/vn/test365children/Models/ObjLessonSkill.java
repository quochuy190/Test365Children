package neo.vn.test365children.Models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ObjLessonSkill implements Serializable {
    @SerializedName("ERROR")
    String sERROR;
    @SerializedName("MESSAGE")
    String sMESSAGE;
    @SerializedName("MESSGE")
    String MESSGE;
    @SerializedName("RESULT")
    String sRESULT;
    @SerializedName("ID")
    String ID;
    @SerializedName("NAME")
    String NAME;
    @SerializedName("IMAGES")
    String IMAGES;
    @SerializedName("DESCRIPTION")
    String DESCRIPTION;
    @SerializedName("KIND_ID")
    String KIND_ID;
    @SerializedName("CONDUCT")
    String CONDUCT;
    @SerializedName("URLONLINE")
    String URLONLINE;
    @SerializedName("URL1")
    String URL1;
    @SerializedName("URL2")
    String URL2;
    @SerializedName("URL_USER")
    String URL_USER;
    @SerializedName("URL_PASS")
    String URL_PASS;
    @SerializedName("STATE")
    String STATE;


    public ObjLessonSkill() {
    }

    private static ObjLessonSkill getObject(JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toString(), ObjLessonSkill.class);
    }

    public static ArrayList<ObjLessonSkill> getList(String jsonArray) throws JSONException {
        ArrayList<ObjLessonSkill> arrayList = new ArrayList<>();
        Type type = new TypeToken<List<ObjLessonSkill>>() {
        }.getType();
        Gson gson = new Gson();
        arrayList = gson.fromJson(jsonArray, type);
        return arrayList;
    }

    public String getMESSGE() {
        return MESSGE;
    }

    public void setMESSGE(String MESSGE) {
        this.MESSGE = MESSGE;
    }

    public String getsERROR() {
        return sERROR;
    }

    public void setsERROR(String sERROR) {
        this.sERROR = sERROR;
    }

    public String getsMESSAGE() {
        return sMESSAGE;
    }

    public void setsMESSAGE(String sMESSAGE) {
        this.sMESSAGE = sMESSAGE;
    }

    public String getsRESULT() {
        return sRESULT;
    }

    public void setsRESULT(String sRESULT) {
        this.sRESULT = sRESULT;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getIMAGES() {
        return IMAGES;
    }

    public void setIMAGES(String IMAGES) {
        this.IMAGES = IMAGES;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getKIND_ID() {
        return KIND_ID;
    }

    public void setKIND_ID(String KIND_ID) {
        this.KIND_ID = KIND_ID;
    }

    public String getCONDUCT() {
        return CONDUCT;
    }

    public void setCONDUCT(String CONDUCT) {
        this.CONDUCT = CONDUCT;
    }

    public String getURLONLINE() {
        return URLONLINE;
    }

    public void setURLONLINE(String URLONLINE) {
        this.URLONLINE = URLONLINE;
    }

    public String getURL1() {
        return URL1;
    }

    public void setURL1(String URL1) {
        this.URL1 = URL1;
    }

    public String getURL2() {
        return URL2;
    }

    public void setURL2(String URL2) {
        this.URL2 = URL2;
    }

    public String getURL_USER() {
        return URL_USER;
    }

    public void setURL_USER(String URL_USER) {
        this.URL_USER = URL_USER;
    }

    public String getURL_PASS() {
        return URL_PASS;
    }

    public void setURL_PASS(String URL_PASS) {
        this.URL_PASS = URL_PASS;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }
}
