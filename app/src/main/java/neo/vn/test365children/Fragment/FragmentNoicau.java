package neo.vn.test365children.Fragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import butterknife.ButterKnife;
import neo.vn.test365children.Base.BaseFragment;
import neo.vn.test365children.Models.CauhoiDetail;
import neo.vn.test365children.R;


/**
 * @author Quốc Huy
 * @version 1.0.0
 * @description
 * @desc Developer NEO Company.
 * @created 8/6/2018
 * @updated 8/6/2018
 * @modified by
 * @updated on 8/6/2018
 * @since 1.0
 */
public class FragmentNoicau extends BaseFragment {
    private static final String TAG = "FragmentCauhoi";
    private CauhoiDetail mCauhoi;



    public static FragmentNoicau newInstance(CauhoiDetail restaurant) {
        FragmentNoicau restaurantDetailFragment = new FragmentNoicau();
        Bundle args = new Bundle();
        //args.putSerializable("cauhoi",restaurant);
        args.putParcelable("cauhoi", Parcels.wrap(restaurant));
        restaurantDetailFragment.setArguments(args);
        return restaurantDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCauhoi = Parcels.unwrap(getArguments().getParcelable("cauhoi"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_noicau, container, false);
        ButterKnife.bind(this, view);
        //   Log.i(TAG, "onCreateView: " + mCauhoi.getsQUESTION());
        init();
        initData();
        initEvent();
        return view;
    }

    private boolean isClickXemdiem = false;

    private void initEvent() {

    }

    private void initData() {

        /*txt_lable.setText("Bài: " + mCauhoi.getsNumberDe() + " " + mCauhoi.getsCauhoi_huongdan());
        if (mCauhoi.getsQUESTION().indexOf("//") > 0) {
            MathView mathView = new MathView(getContext());
            mathView.setClickable(true);
            mathView.setTextSize(17);
            mathView.setTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
            mathView.setDisplayText(StringUtil.StringFraction(mCauhoi.getsQUESTION()));
            mathView.setViewBackgroundColor(getContext().getResources().getColor(R.color.bg_item_dapan));
            ll_cauhoi.addView(mathView);
        } else if (mCauhoi.getsQUESTION().indexOf("image") > 0) {
            ImageView txt_dapan = new ImageView(getContext());
            int hight_image = (int) getContext().getResources().getDimension(R.dimen.item_dapan);
            txt_dapan.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    hight_image));
            Glide.with(getContext()).load(Config.URL_IMAGE + mCauhoi.getsQUESTION()).into(txt_dapan);
            ll_cauhoi.addView(txt_dapan);
        } else {
            TextView txt_dapan = new TextView(getContext());
            txt_dapan.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            txt_dapan.setTextSize(17);
            txt_dapan.setTextColor(getContext().getResources().getColor(R.color.black));
            txt_dapan.setText(Html.fromHtml(mCauhoi.getsQUESTION()));
            ll_cauhoi.addView(txt_dapan);
        }
*/
    }

    boolean isdouble_click = false;

    private void init() {
        Draw draw = new Draw(getContext(), 100, 300, 600, 800);


        // Draw a solid color on the canvas as background

        // Initialize a new Paint instance to draw the line
        Paint paint = new Paint();
        // Line color
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        // Line width in pixels
        paint.setStrokeWidth(8);
        paint.setAntiAlias(true);

        // Set a pixels value to offset the line from canvas edge
        int offset = 50;
    }

    public class Draw extends View {
        Paint paint;
        Path path;
        float left;
        float top;
        float right;
        float bottom;

        public Draw(Context context, float left, float top, float right, float bottom) {
            super(context);
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            init();
        }

        public Draw(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public Draw(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init();
        }

        public void init() {
            paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(10);
            paint.setStyle(Paint.Style.STROKE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            super.onDraw(canvas);
            canvas.drawRect(left, top, right, bottom, paint);
        }
    }
}
