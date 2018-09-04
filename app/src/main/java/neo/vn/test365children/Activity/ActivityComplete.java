package neo.vn.test365children.Activity;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import neo.vn.test365children.App;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.Models.Cauhoi;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.R;

public class ActivityComplete extends BaseActivity {
    @BindView(R.id.txt_pointlambai)
    TextView txt_pointlambai;
    @Override
    public int setContentViewId() {
        return R.layout.activity_comple_baitap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }
    private float fPoint = 0;
    private void initData() {

        for (int j = 0; j < App.mLisCauhoi.size(); j++) {
            Cauhoi obj = App.mLisCauhoi.get(j);
            if (obj.getLisInfo() != null) {
                for (int i = 0; i < obj.getLisInfo().size(); i++) {
                    CauhoiDetail objCauhoiDetail = App.mLisCauhoi.get(j).getLisInfo().get(i);
                    if (objCauhoiDetail.isAnserTrue()){
                        fPoint = fPoint+ Float.parseFloat(objCauhoiDetail.getsPOINT());
                    }
                }
            }
        }
        txt_pointlambai.setText("Điểm con đạt được: "+fPoint);
    }
}
