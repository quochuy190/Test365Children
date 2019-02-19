package neo.vn.test365children.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import neo.vn.test365children.Base.BaseFragment;
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
public class FragmentShow_Guild_Show_user_One extends BaseFragment {
    @BindView(R.id.img_guild_1)
    ImageView img_guild_1;
    @BindView(R.id.img_guild_2)
    ImageView img_guild_3;
    public static FragmentShow_Guild_Show_user_One newInstance( ) {
        FragmentShow_Guild_Show_user_One restaurantDetailFragment = new FragmentShow_Guild_Show_user_One();
        Bundle args = new Bundle();
        //args.putSerializable("cauhoi",restaurant);
        restaurantDetailFragment.setArguments(args);
        return restaurantDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_guild_userkid, container, false);
        ButterKnife.bind(this, view);
        Glide.with(this).load(R.drawable.guild_1).into(img_guild_1);
        Glide.with(this).load(R.drawable.guild_2).into(img_guild_3);
        return view;
    }

}
