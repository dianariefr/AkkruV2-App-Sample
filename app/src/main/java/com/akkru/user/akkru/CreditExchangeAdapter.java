package com.akkru.user.akkru;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CreditExchangeAdapter extends FragmentStatePagerAdapter{

    private List<Fragment> fragments;

    private List<Integer> creditPromo = new ArrayList<Integer>(){{
        add(R.drawable.generali);
        add(R.drawable.generali);
    }};


    public CreditExchangeAdapter(FragmentManager fm) {
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
        for(int creditPromo : creditPromo){
            CreditExchangePagerLayout creditExchangePagerLayout = new CreditExchangePagerLayout();
            creditExchangePagerLayout.creditPromoId = creditPromo;
            fragmentList.add(creditExchangePagerLayout);
        }
        return fragmentList;
    }
}
