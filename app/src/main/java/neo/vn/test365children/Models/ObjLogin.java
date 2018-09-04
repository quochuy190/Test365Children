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

/**
 * @author Quốc Huy
 * @version 1.0.0
 * @description
 * @desc Developer NEO Company.
 * @created 7/31/2018
 * @updated 7/31/2018
 * @modified by
 * @updated on 7/31/2018
 * @since 1.0
 */
public class ObjLogin implements Serializable {
    @SerializedName("ERROR")
    private String sERROR;
    @SerializedName("MESSAGE")
    private String sMESSAGE;
    @SerializedName("RESULT")
    private String sRESULT;
    @SerializedName("FULLNAME")
    private String sFULLNAME;
    @SerializedName("PHONENUMBER")
    private String sPHONENUMBER;
    @SerializedName("EMAIL")
    private String sEMAIL;
    @SerializedName("AVARTAR")
    private String sAVARTAR;
    @SerializedName("STARTTIME")
    private String sSTARTTIME;
    @SerializedName("APPVERSION")
    private String sAPPVERSION;
    @SerializedName("DEVICEMODEL")
    private String sDEVICEMODEL;
    @SerializedName("API_SERVER")
    private String sAPI_SERVER;
    @SerializedName("MEDIA_SERVER")
    private String sMEDIA_SERVER;
    @SerializedName("USERNAME")
    private String sUSERNAME;
    @SerializedName("STATE")
    private String sSTATE;
    @SerializedName("UPDATETIME")
    private String sUPDATETIME;

    @SerializedName("DEVICE_TYPE")
    private String sDEVICE_TYPE;
    @SerializedName("SCHOOL_ID")
    private String sSCHOOL_ID;
    @SerializedName("SCHOOL_NAME")
    private String sSCHOOL_NAME;
    @SerializedName("DISTRICT_NAME")
    private String sDISTRICT_NAME;
    @SerializedName("PROVINCE_NAME")
    private String sPROVINCE_NAME;
    @SerializedName("LEVEL_ID")
    private String sLEVEL_ID;

    public ObjLogin() {
    }

    private static ObjLogin getObject(JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toString(), ObjLogin.class);
    }

    public static ArrayList<ObjLogin> getList(String jsonArray) throws JSONException {
        ArrayList<ObjLogin> arrayList = new ArrayList<>();
        Type type = new TypeToken<List<ObjLogin>>() {
        }.getType();

        Gson gson = new Gson();
        arrayList = gson.fromJson(jsonArray, type);

        return arrayList;
    }

    public String getsLEVEL_ID() {
        return sLEVEL_ID;
    }

    public void setsLEVEL_ID(String sLEVEL_ID) {
        this.sLEVEL_ID = sLEVEL_ID;
    }

    public String getsDEVICE_TYPE() {
        return sDEVICE_TYPE;
    }

    public void setsDEVICE_TYPE(String sDEVICE_TYPE) {
        this.sDEVICE_TYPE = sDEVICE_TYPE;
    }

    public String getsSCHOOL_ID() {
        return sSCHOOL_ID;
    }

    public void setsSCHOOL_ID(String sSCHOOL_ID) {
        this.sSCHOOL_ID = sSCHOOL_ID;
    }

    public String getsSCHOOL_NAME() {
        return sSCHOOL_NAME;
    }

    public void setsSCHOOL_NAME(String sSCHOOL_NAME) {
        this.sSCHOOL_NAME = sSCHOOL_NAME;
    }

    public String getsDISTRICT_NAME() {
        return sDISTRICT_NAME;
    }

    public void setsDISTRICT_NAME(String sDISTRICT_NAME) {
        this.sDISTRICT_NAME = sDISTRICT_NAME;
    }

    public String getsPROVINCE_NAME() {
        return sPROVINCE_NAME;
    }

    public void setsPROVINCE_NAME(String sPROVINCE_NAME) {
        this.sPROVINCE_NAME = sPROVINCE_NAME;
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

    public String getsFULLNAME() {
        return sFULLNAME;
    }

    public void setsFULLNAME(String sFULLNAME) {
        this.sFULLNAME = sFULLNAME;
    }

    public String getsPHONENUMBER() {
        return sPHONENUMBER;
    }

    public void setsPHONENUMBER(String sPHONENUMBER) {
        this.sPHONENUMBER = sPHONENUMBER;
    }

    public String getsEMAIL() {
        return sEMAIL;
    }

    public void setsEMAIL(String sEMAIL) {
        this.sEMAIL = sEMAIL;
    }

    public String getsAVARTAR() {
        return sAVARTAR;
    }

    public void setsAVARTAR(String sAVARTAR) {
        this.sAVARTAR = sAVARTAR;
    }

    public String getsSTARTTIME() {
        return sSTARTTIME;
    }

    public void setsSTARTTIME(String sSTARTTIME) {
        this.sSTARTTIME = sSTARTTIME;
    }

    public String getsAPPVERSION() {
        return sAPPVERSION;
    }

    public void setsAPPVERSION(String sAPPVERSION) {
        this.sAPPVERSION = sAPPVERSION;
    }

    public String getsDEVICEMODEL() {
        return sDEVICEMODEL;
    }

    public void setsDEVICEMODEL(String sDEVICEMODEL) {
        this.sDEVICEMODEL = sDEVICEMODEL;
    }

    public String getsAPI_SERVER() {
        return sAPI_SERVER;
    }

    public void setsAPI_SERVER(String sAPI_SERVER) {
        this.sAPI_SERVER = sAPI_SERVER;
    }

    public String getsMEDIA_SERVER() {
        return sMEDIA_SERVER;
    }

    public void setsMEDIA_SERVER(String sMEDIA_SERVER) {
        this.sMEDIA_SERVER = sMEDIA_SERVER;
    }

    public String getsUSERNAME() {
        return sUSERNAME;
    }

    public void setsUSERNAME(String sUSERNAME) {
        this.sUSERNAME = sUSERNAME;
    }

    public String getsSTATE() {
        return sSTATE;
    }

    public void setsSTATE(String sSTATE) {
        this.sSTATE = sSTATE;
    }

    public String getsUPDATETIME() {
        return sUPDATETIME;
    }

    public void setsUPDATETIME(String sUPDATETIME) {
        this.sUPDATETIME = sUPDATETIME;
    }
}
