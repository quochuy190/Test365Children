package neo.vn.test365children.Presenter;

import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.ObjLogin;

public interface ImgGetUserTry {
    interface Presenter {
        void api_create_user_try(String sLevel, String sUUID);
    }

    interface View {
        void show_create_user_try(ObjLogin obj);

        void show_error_api(ErrorApi objError);
    }
}
