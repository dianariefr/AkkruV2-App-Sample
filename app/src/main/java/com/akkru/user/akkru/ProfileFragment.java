package com.akkru.user.akkru;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.akkru.user.akkru.adapter.ItemHomePageAdapter;
import com.akkru.user.akkru.adapter.ItemVoucherAdapter;
import com.akkru.user.akkru.api.model.Login2;
import com.akkru.user.akkru.api.model.User;
import com.akkru.user.akkru.api.model.UserInfo;
import com.akkru.user.akkru.api.model.UserResponse;
import com.akkru.user.akkru.api.service.ApiClient;
import com.akkru.user.akkru.api.service.UserClient;
import com.akkru.user.akkru.utils.PrefManager;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import com.akkru.user.akkru.LoginActivity;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Header;

import static android.support.constraint.Constraints.TAG;


public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Toolbar toolbar;
    private Button btn_edit_profile;
    private String token , emailx,fullname,city,voucher;
    private Object points;
    PrefManager prefManager;
    private ImageView next,prev;;

    private TextView email , fullnameTv,pointsTv,voucherTv;
    private ViewPager viewPager;
    PieChart pieChart;



    private float[] yData ={100000f,200000f,500000f};
    private String[] xData = { "Sony", "Huawei", "LG" };



    private OnFragmentInteractionListener mListener;

    private CallbackListener callbackListener;
    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefManager = new PrefManager(getContext());
        token = prefManager.getString(PrefManager.PREF_TOKEN);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile,container,false);


        pieChart = (PieChart) view.findViewById(R.id.pie_chart);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(50f);

        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(400000,"Pengeluaran"));
        yValues.add(new PieEntry(300000,"Pemasukan"));
        yValues.add(new PieEntry(1000000,"Jajan"));

        PieDataSet pieDataSet = new PieDataSet(yValues,"Finance");

        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(5f);
        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);


        PieData data = new PieData((pieDataSet));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);


        pieChart.setData(data);

        return view;

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        email = (TextView) view.findViewById(R.id.email);
        fullnameTv = (TextView)view.findViewById(R.id.username);
        pointsTv = (TextView) view.findViewById(R.id.points);
        voucherTv = (TextView) view.findViewById(R.id.voucher);

        viewPager = (ViewPager) view.findViewById(R.id.voucherVP) ;
        viewPager.setAdapter(new ItemVoucherAdapter(getFragmentManager()));

        prev = view.findViewById(R.id.back_voucher);
        next = view.findViewById(R.id.next_voucher);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=viewPager.getCurrentItem();
                viewPager.setCurrentItem(a+1);
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=viewPager.getCurrentItem();
                viewPager.setCurrentItem(a-1,true);
            }
        });

        UserClient userClient = ApiClient.getClient().create(UserClient.class);

        Call<UserResponse> call = userClient.userinfo(token);
       call.enqueue(new Callback<UserResponse>() {
           @Override
           public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
               fullname = response.body().getUser().getName();
               emailx = response.body().getUser().getEmail();
               points = response.body().getUser().getPoint();

               fullnameTv.setText(fullname);
               email.setText(emailx);

           }

           @Override
           public void onFailure(Call<UserResponse> call, Throwable t) {

           }
       });

        btn_edit_profile = (Button) view.findViewById(R.id.edit_profile);
        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),EditProfileActivity.class);
                startActivity(i);
            }
        });






    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof CallbackListener)
            callbackListener = (CallbackListener) getActivity();
        callbackListener.onCallBack("Profile");





    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }





}