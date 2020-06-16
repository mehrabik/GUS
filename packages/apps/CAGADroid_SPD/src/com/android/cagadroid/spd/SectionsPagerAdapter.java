package com.android.cagadroid.spd;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.android.cagadroid.spd.fragments.CompoundFragment;
import com.android.cagadroid.spd.fragments.PolicyFragment;
import com.android.cagadroid.spd.fragments.PrimitiveFragment;
import com.android.cagadroid.spd.fragments.SettingsFragment;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{
        R.string.tab_text_s,
        R.string.tab_text_0,
        R.string.tab_text_1,
        R.string.tab_text_2,
        R.string.tab_text_3,
        R.string.tab_text_4,
        R.string.tab_text_5,
        R.string.tab_text_6,
        R.string.tab_text_7,
        R.string.tab_text_8,
        R.string.tab_text_9,
        R.string.tab_text_10,
        R.string.tab_text_11,
        R.string.tab_text_12,
        R.string.tab_text_13,
        R.string.tab_text_14};

    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            return SettingsFragment.newInstance();
        }
        else if (position == 1) {
            return PolicyFragment.newInstance();
        }
        else if (position > 1 && position < 12)
        {
            return PrimitiveFragment.newInstance(mContext.getResources().getString(TAB_TITLES[position]));
        }
        else {
            return CompoundFragment.newInstance(mContext.getResources().getString(TAB_TITLES[position]));
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}
