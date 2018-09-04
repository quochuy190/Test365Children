package neo.vn.test365children.Presenter;

import java.util.List;

import neo.vn.test365children.Models.Baitap_Tuan;
import neo.vn.test365children.Models.Cauhoi;
import neo.vn.test365children.Models.ErrorApi;
import neo.vn.test365children.Models.TuanDamua;

public interface ImpBaitap {
    interface Presenter {
        void get_api_list_buy(String sUserMe, String sUserCon, String sIdMon, String sIdKhoi);

        void get_api_get_part(String sUserMe, String sUserCon, String sIdDebai);

        void get_api_get_excercise_needed(String sUserMe, String sUserCon, String sDay);

        void get_api_get_excercise_expired(String sUserMe, String sUserCon);
    }

    interface View {
        void show_list_list_buy(List<TuanDamua> mLis);

        void show_list_get_part(List<Cauhoi> mLis);

        void show_error_api(List<ErrorApi> mLis);

        void show_get_excercise_needed(List<Baitap_Tuan> mLis);

        void show_get_excercise_expired(List<Baitap_Tuan> mLis);
    }
}
