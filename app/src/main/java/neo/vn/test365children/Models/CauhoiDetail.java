package neo.vn.test365children.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CauhoiDetail implements Parcelable, Serializable {
    @SerializedName("ERROR")
    String sERROR;
    @SerializedName("MESSAGE")
    String sMESSAGE;
    @SerializedName("RESULT")
    String sRESULT;
    @SerializedName("ID")
    String sID;
    @SerializedName("PART_ID")
    String sPART_ID;
    @SerializedName("QUESTION")
    String sQUESTION;
    @SerializedName("A")
    String sA;
    @SerializedName("B")
    String sB;
    @SerializedName("C")
    String sC;
    @SerializedName("D")
    String sD;
    @SerializedName("ANSWER")
    String sANSWER;
    @SerializedName("EXPLAIN")
    String sEXPLAIN;

    @SerializedName("UPDATETIME")
    String sUPDATETIME;
    @SerializedName("TYPE")
    String sTYPE;
    @SerializedName("ANSWER_CHILD")
    String sANSWER_CHILD;
    @SerializedName("RESULT_CHILD")
    String sRESULT_CHILD;
    @SerializedName("POINT_CHILD")
    String sPOINT_CHILD;
    @SerializedName("EGG_1")
    String sEGG_1;
    @SerializedName("EGG_2")
    String sEGG_2;
    @SerializedName("EGG_3")
    String sEGG_3;
    @SerializedName("EGG_4")
    String sEGG_4;
    @SerializedName("POINT")
    String sPOINT;
    @SerializedName("EGG_1_RESULT")
    String sEGG_1_RESULT;
    @SerializedName("EGG_2_RESULT")
    String sEGG_2_RESULT;
    @SerializedName("EGG_3_RESULT")
    String sEGG_3_RESULT;
    @SerializedName("EGG_4_RESULT")
    String sEGG_4_RESULT;

    String sImagePath;
    String sAudioPath;
    String sCauhoi_huongdan;
    String sNumberDe;
    String sSubNumberCau;
    String sTextDebai;
    boolean mLeft;
    boolean mRight;
    boolean isAnserTrue;
    boolean isDalam;
    public CauhoiDetail(String sERROR, String sMESSAGE, String sRESULT) {
        this.sERROR = sERROR;
        this.sMESSAGE = sMESSAGE;
        this.sRESULT = sRESULT;
    }

    public CauhoiDetail() {
    }

    protected CauhoiDetail(Parcel in) {
        sERROR = in.readString();
        sMESSAGE = in.readString();
        sRESULT = in.readString();
    }

    public static final Creator<CauhoiDetail> CREATOR = new Creator<CauhoiDetail>() {
        @Override
        public CauhoiDetail createFromParcel(Parcel in) {
            return new CauhoiDetail(in);
        }

        @Override
        public CauhoiDetail[] newArray(int size) {
            return new CauhoiDetail[size];
        }
    };

    private static CauhoiDetail getObject(JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toString(), CauhoiDetail.class);
    }

    public static ArrayList<CauhoiDetail> getList(String jsonArray) throws JSONException {
        ArrayList<CauhoiDetail> arrayList = new ArrayList<>();
        Type type = new TypeToken<List<CauhoiDetail>>() {
        }.getType();
        Gson gson = new Gson();
        arrayList = gson.fromJson(jsonArray, type);
        return arrayList;
    }

    public boolean isAnserTrue() {
        return isAnserTrue;
    }

    public void setAnserTrue(boolean anserTrue) {
        isAnserTrue = anserTrue;
    }

    public boolean isDalam() {
        return isDalam;
    }

    public void setDalam(boolean dalam) {
        isDalam = dalam;
    }

    public String getsImagePath() {
        return sImagePath;
    }

    public void setsImagePath(String sImagePath) {
        this.sImagePath = sImagePath;
    }

    public String getsAudioPath() {
        return sAudioPath;
    }

    public void setsAudioPath(String sAudioPath) {
        this.sAudioPath = sAudioPath;
    }

    public String getsSubNumberCau() {
        return sSubNumberCau;
    }

    public void setsSubNumberCau(String sSubNumberCau) {
        this.sSubNumberCau = sSubNumberCau;
    }

    public String getsTextDebai() {
        return sTextDebai;
    }

    public void setsTextDebai(String sTextDebai) {
        this.sTextDebai = sTextDebai;
    }

    public String getsNumberDe() {
        return sNumberDe;
    }

    public void setsNumberDe(String sNumberDe) {
        this.sNumberDe = sNumberDe;
    }

    public String getsCauhoi_huongdan() {
        return sCauhoi_huongdan;
    }

    public void setsCauhoi_huongdan(String sCauhoi_huongdan) {
        this.sCauhoi_huongdan = sCauhoi_huongdan;
    }

    public String getsEGG_1() {
        return sEGG_1;
    }

    public void setsEGG_1(String sEGG_1) {
        this.sEGG_1 = sEGG_1;
    }

    public String getsEGG_2() {
        return sEGG_2;
    }

    public void setsEGG_2(String sEGG_2) {
        this.sEGG_2 = sEGG_2;
    }

    public String getsEGG_3() {
        return sEGG_3;
    }

    public void setsEGG_3(String sEGG_3) {
        this.sEGG_3 = sEGG_3;
    }

    public String getsEGG_4() {
        return sEGG_4;
    }

    public void setsEGG_4(String sEGG_4) {
        this.sEGG_4 = sEGG_4;
    }

    public String getsPOINT() {
        return sPOINT;
    }

    public void setsPOINT(String sPOINT) {
        this.sPOINT = sPOINT;
    }

    public String getsEGG_1_RESULT() {
        return sEGG_1_RESULT;
    }

    public void setsEGG_1_RESULT(String sEGG_1_RESULT) {
        this.sEGG_1_RESULT = sEGG_1_RESULT;
    }

    public String getsEGG_2_RESULT() {
        return sEGG_2_RESULT;
    }

    public void setsEGG_2_RESULT(String sEGG_2_RESULT) {
        this.sEGG_2_RESULT = sEGG_2_RESULT;
    }

    public String getsEGG_3_RESULT() {
        return sEGG_3_RESULT;
    }

    public void setsEGG_3_RESULT(String sEGG_3_RESULT) {
        this.sEGG_3_RESULT = sEGG_3_RESULT;
    }

    public String getsEGG_4_RESULT() {
        return sEGG_4_RESULT;
    }

    public void setsEGG_4_RESULT(String sEGG_4_RESULT) {
        this.sEGG_4_RESULT = sEGG_4_RESULT;
    }

    public boolean ismLeft() {
        return mLeft;
    }

    public void setmLeft(boolean mLeft) {
        this.mLeft = mLeft;
    }

    public boolean ismRight() {
        return mRight;
    }

    public void setmRight(boolean mRight) {
        this.mRight = mRight;
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

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public String getsPART_ID() {
        return sPART_ID;
    }

    public void setsPART_ID(String sPART_ID) {
        this.sPART_ID = sPART_ID;
    }

    public String getsQUESTION() {
        return sQUESTION;
    }

    public void setsQUESTION(String sQUESTION) {
        this.sQUESTION = sQUESTION;
    }

    public String getsA() {
        return sA;
    }

    public void setsA(String sA) {
        this.sA = sA;
    }

    public String getsB() {
        return sB;
    }

    public void setsB(String sB) {
        this.sB = sB;
    }

    public String getsC() {
        return sC;
    }

    public void setsC(String sC) {
        this.sC = sC;
    }

    public String getsD() {
        return sD;
    }

    public void setsD(String sD) {
        this.sD = sD;
    }

    public String getsANSWER() {
        return sANSWER;
    }

    public void setsANSWER(String sANSWER) {
        this.sANSWER = sANSWER;
    }

    public String getsEXPLAIN() {
        return sEXPLAIN;
    }

    public void setsEXPLAIN(String sEXPLAIN) {
        this.sEXPLAIN = sEXPLAIN;
    }

    public String getsUPDATETIME() {
        return sUPDATETIME;
    }

    public void setsUPDATETIME(String sUPDATETIME) {
        this.sUPDATETIME = sUPDATETIME;
    }

    public String getsTYPE() {
        return sTYPE;
    }

    public void setsTYPE(String sTYPE) {
        this.sTYPE = sTYPE;
    }

    public String getsANSWER_CHILD() {
        return sANSWER_CHILD;
    }

    public void setsANSWER_CHILD(String sANSWER_CHILD) {
        this.sANSWER_CHILD = sANSWER_CHILD;
    }

    public String getsRESULT_CHILD() {
        return sRESULT_CHILD;
    }

    public void setsRESULT_CHILD(String sRESULT_CHILD) {
        this.sRESULT_CHILD = sRESULT_CHILD;
    }

    public String getsPOINT_CHILD() {
        return sPOINT_CHILD;
    }

    public void setsPOINT_CHILD(String sPOINT_CHILD) {
        this.sPOINT_CHILD = sPOINT_CHILD;
    }

    public static Creator<CauhoiDetail> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }


}
