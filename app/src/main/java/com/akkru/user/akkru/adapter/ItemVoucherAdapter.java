package com.akkru.user.akkru.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.akkru.user.akkru.R;
import com.akkru.user.akkru.VoucherPagerLayout;
import java.util.ArrayList;
import java.util.List;

public class ItemVoucherAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;

    private List<Integer> voucherlist = new ArrayList<Integer>(){{
        add(R.drawable.generali);
        add(R.drawable.generali);
        add(R.drawable.generali);
        add(R.drawable.generali);
    }};


    public ItemVoucherAdapter(FragmentManager fm) {
        super(fm);
        fragments = getFragments();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragmentList = new ArrayList<>();
        for(int voucherpromo : voucherlist){
            VoucherPagerLayout voucherPagerLayout = new VoucherPagerLayout();
            voucherPagerLayout.voucherId = voucherpromo;
            fragmentList.add(voucherPagerLayout);
        }
        return fragmentList;
    }

}
