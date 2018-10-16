package neo.vn.test365children.Activity.bangxephang;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import neo.vn.test365children.Base.BaseActivity;
import neo.vn.test365children.R;

public class ActivityBieuDo extends BaseActivity implements OnChartValueSelectedListener, View.OnClickListener {
    @BindView(R.id.combinedChart)
    CombinedChart mChart;
    List<String> mListTiengViet;
    List<String> mListToan;
    List<String> mListTiengAnh;
    @BindView(R.id.img_bxh)
    ImageView img_bxh;

    @Override
    public int setContentViewId() {
        return R.layout.activity_bieudo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initEvent();
    }

    private void initEvent() {
        img_bxh.setOnClickListener(this);
    }

    private void initData() {
        mListToan = new ArrayList<>();
        mListTiengViet = new ArrayList<>();
        mListTiengAnh = new ArrayList<>();
        mListToan.add("9");
        mListToan.add("8.5");
        mListToan.add("10");
        mListTiengViet.add("5");
        mListTiengViet.add("9");
        mListTiengViet.add("10");
        mListTiengAnh.add("6");
        mListTiengAnh.add("9");
        mListTiengAnh.add("8.5");
        initChart(mListToan, mListTiengViet, mListTiengAnh);
    }

    private void initChart(List<String> mLisPointToan, List<String> mListPointTV, List<String> mListPointTA) {
        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);
        mChart.setOnChartValueSelectedListener(this);
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);
        final List<String> xLabel = new ArrayList<>();
        xLabel.add("Tuần 1");
        xLabel.add("Tuần 2");
        xLabel.add("Tuần 3");
        xLabel.add("Tuần 4");
        xLabel.add("Tuần 5");
        xLabel.add("Tuần 6");
        xLabel.add("Tuần 7");
        xLabel.add("Tuần 8");
        xLabel.add("Tuần 9");
        xLabel.add("Tuần 10");
        xLabel.add("Tuần 11");
        xLabel.add("Tuần 12");
        xLabel.add("Tuần 13");
        xLabel.add("Tuần 14");
        xLabel.add("Tuần 15");
        xLabel.add("Tuần 16");
        xLabel.add("Tuần 17");
        xLabel.add("Tuần 18");
        xLabel.add("Tuần 19");
        xLabel.add("Tuần 20");
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabel.get((int) value % xLabel.size());
            }
        });
        CombinedData data = new CombinedData();
        LineData lineDatas = new LineData();
        if (mListPointTV != null && mListPointTV.size() > 0)
            lineDatas.addDataSet((ILineDataSet) dataChartTiengViet(mListPointTV));
        if (mLisPointToan != null && mLisPointToan.size() > 0)
            lineDatas.addDataSet((ILineDataSet) dataChartToan(mLisPointToan));
        if (mListPointTA != null && mListPointTA.size() > 0)
            lineDatas.addDataSet((ILineDataSet) dataChartTiengAnh(mListPointTA));

        data.setData(lineDatas);
        xAxis.setAxisMaximum(data.getXMax() + 0.25f);
        mChart.setData(data);
        mChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(this, "Value: "
                + e.getY()
                + ", index: "
                + h.getX()
                + ", DataSet index: "
                + h.getDataSetIndex(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }

    private static DataSet dataChartToan(List<String> mListData) {

        LineData d = new LineData();
        //  int[] data = new int[]{4, 6, 3, 6, 5, 7, 8, 9, 10, 9, 10, 9};

        ArrayList<Entry> entries = new ArrayList<Entry>();
        for (int index = 0; index < mListData.size(); index++) {
            if (mListData.get(index).length() > 0)
                entries.add(new Entry(index, Float.parseFloat(mListData.get(index))));
        }

        LineDataSet set = new LineDataSet(entries, "Toán");
        set.setColor(Color.RED);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.RED);
        set.setCircleRadius(5f);
        set.setFillColor(Color.RED);
        set.setMode(LineDataSet.Mode.LINEAR);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.RED);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return set;
    }

    private static DataSet dataChartTiengViet(List<String> mListData) {

        LineData d = new LineData();
        // int[] data = new int[]{1, 2, 2, 1, 1, 1, 2, 1, 1, 2, 1, 9};

        ArrayList<Entry> entries = new ArrayList<Entry>();
        for (int index = 0; index < mListData.size(); index++) {
            if (mListData.get(index).length() > 0)
                entries.add(new Entry(index, Float.parseFloat(mListData.get(index))));
        }
        LineDataSet set = new LineDataSet(entries, "Tiếng Việt");
        set.setColor(Color.GREEN);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.GREEN);
        set.setCircleRadius(5f);
        set.setFillColor(Color.GREEN);
        set.setMode(LineDataSet.Mode.LINEAR);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.GREEN);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return set;
    }

    private static DataSet dataChartTiengAnh(List<String> mListData) {

        LineData d = new LineData();
        // float[] data = new float[]{4, 7, 5, 10, 6, 9, 5, 4, 8, 8, 9, 10};

        ArrayList<Entry> entries = new ArrayList<Entry>();
        for (int index = 0; index < mListData.size(); index++) {
            if (mListData.get(index).length() > 0)
                entries.add(new Entry(index, Float.parseFloat(mListData.get(index))));
        }

        LineDataSet set = new LineDataSet(entries, "Điểm theo tuần");
        set.setColor(Color.CYAN);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.CYAN);
        set.setCircleRadius(5f);
        set.setFillColor(Color.CYAN);
        set.setMode(LineDataSet.Mode.LINEAR);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.CYAN);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return set;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_bxh:
                Intent intent = new Intent(ActivityBieuDo.this, ActivityBXH.class);
                startActivity(intent);
                break;
        }
    }
}
