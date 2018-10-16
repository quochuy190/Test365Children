package neo.vn.test365children.Presenter;

import java.util.List;

import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.ObjLogin;

public interface ImlLogin {
    interface Presenter {
        void api_login(String sUserMe, String sUserCon, String sPass, String sVersion, String sDeviceModel,
                       String sDevice_type, String sOsVersion, String sTokenKey);
    }

    interface View {
        void show_api_login(List<ObjLogin> mLis);

        void show_error_api(List<ErrorApi> mLis);
    }
}
