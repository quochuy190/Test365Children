package neo.vn.test365children.Presenter;

import neo.vn.test365children.Models.ErrorApi;

public interface ImlFeedback {
    interface Presenter {
        void api_send_feetback(String sUserMother, String sUserKid, String sType_Rate_1, String Rating1,
                               String TYPE_RATE2, String RATING2, String TYPE_EXE, String ID_EXCERCISE);
    }

    interface View {
        void show_error_api(ErrorApi mLis);

        void show_send_feedback(ErrorApi objError);
    }
}
