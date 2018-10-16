package neo.vn.test365children.Presenter;

import java.util.List;

import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.GameTrieuPhuTriThuc;

public interface ImlGetGameTptt {
    interface Presenter {
        void api_get_game_tptt(String sUserMe, String sUserCon, String sDate);

        void api_start_tptt(String sUserMe, String sUserCon, String id_part);

        void api_submit_tptt(String sUserMe, String sUserCon, String sDate, String sTime, String sAward, String sMonney);
    }

    interface View {
        void show_get_game_tptt(List<GameTrieuPhuTriThuc> mLis);

        void show_error_api(List<ErrorApi> mLis);

        void show_start_tptt(List<ErrorApi> mLis);

        void show_submit_tptt(List<ErrorApi> mLis);
    }
}
