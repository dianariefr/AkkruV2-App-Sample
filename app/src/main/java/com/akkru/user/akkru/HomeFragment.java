package com.akkru.user.akkru;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akkru.user.akkru.adapter.ItemHomePageAdapter;
import com.akkru.user.akkru.adapter.ItemMonthAdapter;
import com.akkru.user.akkru.api.model.ExpenseResponse;
import com.akkru.user.akkru.api.model.ExpensesItem;
import com.akkru.user.akkru.api.model.IncomeItem;
import com.akkru.user.akkru.api.model.IncomeResponse;
import com.akkru.user.akkru.api.model.UserResponse;
import com.akkru.user.akkru.api.service.ApiClient;
import com.akkru.user.akkru.api.service.UserClient;
import com.akkru.user.akkru.model.ItemHompageModel;

import com.akkru.user.akkru.model.ItemMonthModel;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import com.akkru.user.akkru.utils.PrefManager;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FloatingActionMenu fab;
    private FloatingActionButton fabIncome, fabExpanse;
    private RecyclerView recyclerView, recyclerViewMonth;
    private ItemHomePageAdapter itemHomePageAdapter;
    private ItemMonthAdapter itemMonthAdapter;
    private List<ItemHompageModel> hompageModelList;
    private List<ItemMonthModel> itemMonthModels;
    TextView userhello;
    String user;
    PrefManager prefManager;
    String token;
    private List<IncomeItem> listIncome1;
    private List<ExpensesItem> listExpenses1;
    String result;
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        fab = rootView.findViewById(R.id.fab);

        fabIncome = rootView.findViewById(R.id.fabIncome);
        fabExpanse = rootView.findViewById(R.id.fabExpanse);

        userhello = (TextView) rootView.findViewById(R.id.userhello);

        listExpenses1 = new ArrayList<ExpensesItem>();
        listIncome1 = new ArrayList<IncomeItem>();
        hompageModelList = new ArrayList<ItemHompageModel>();
//        //listIncome(rootView, token);
        listExpenses1 = new ArrayList<ExpensesItem>();
        listIncome1 = new ArrayList<IncomeItem>();
        hompageModelList = new ArrayList<ItemHompageModel>();
        itemMonthModels = new ArrayList<ItemMonthModel>();
        recyclerView = rootView.findViewById(R.id.rcv_itemhompage);
        recyclerViewMonth = rootView.findViewById(R.id.rcv_month);

        LinearLayoutManager linearLayoutManagerMonth = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        itemMonthAdapter = new ItemMonthAdapter(itemMonthModels);
        recyclerViewMonth.setLayoutManager(linearLayoutManagerMonth);
        recyclerViewMonth.setAdapter(itemMonthAdapter);
        loadMonth();
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        itemHomePageAdapter = new ItemHomePageAdapter(hompageModelList);

//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(itemHomePageAdapter);


        //loadDummy();
        //loadData(listIncome(token), listExpense(token));
        listIncome(token);
        listExpense(token);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        itemHomePageAdapter = new ItemHomePageAdapter(hompageModelList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(itemHomePageAdapter);

        UserClient userClient = ApiClient.getClient().create(UserClient.class);

        Call<UserResponse> call = userClient.userinfo(token);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {

                    user = response.body().getUser().getName();
                    userhello.setText("Hi, " + user);

                } else {

                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });

        fabExpanse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ExpanseActivity.class));
            }
        });

        fabIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), IncomeActivity.class));
            }
        });
        return rootView;
    }


    private void listIncome(String token) {
        UserClient userClient = ApiClient.getClient().create(UserClient.class);
        Call<IncomeResponse> call = userClient.getIncome(token);
        call.enqueue(new Callback<IncomeResponse>() {
            @Override
            public void onResponse(Call<IncomeResponse> call, Response<IncomeResponse> response) {
                if (response.isSuccessful()) {
                    listIncome1 = response.body().getIncome();
                    loadDataIncome(listIncome1);
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//                    itemHomePageAdapter = new ItemHomePageAdapter(dataList);
//                    recyclerView.setLayoutManager(linearLayoutManager);
//                    recyclerView.setAdapter(itemHomePageAdapter);
                } else {

                }

            }

            @Override
            public void onFailure(Call<IncomeResponse> call, Throwable t) {

            }
        });
    }

    private void listExpense(String token) {
        UserClient userClient = ApiClient.getClient().create(UserClient.class);
        Call<ExpenseResponse> call = userClient.getExpense(token);
        call.enqueue(new Callback<ExpenseResponse>() {
            @Override
            public void onResponse(Call<ExpenseResponse> call, Response<ExpenseResponse> response) {
                if (response.isSuccessful()) {
                    listExpenses1 = response.body().getExpenses();
                    loadDataExpense(listExpenses1);
                } else {

                }

            }

            @Override
            public void onFailure(Call<ExpenseResponse> call, Throwable t) {

            }
        });
    }

    private void loadMonth() {
        for (int i = 0; i < 1; i++) {
            ItemMonthModel itemMonthModel = new ItemMonthModel("September");
            itemMonthModels.add(itemMonthModel);
            itemMonthAdapter.notifyDataSetChanged();
        }
    }
    String a;
    SimpleDateFormat parseFormat;
    SimpleDateFormat displayFormat;
    private void loadDataIncome(List<IncomeItem> incomeItems) {
        int sizeIncome = incomeItems.size();
        for (int i = 0; i < sizeIncome; i++) {
            ItemHompageModel itemHompageModel = new ItemHompageModel(String.valueOf(i + 1), String.valueOf(incomeItems.get(i).getTotal()), incomeItems.get(i).getName(), 1);
            hompageModelList.add(itemHompageModel);
            a = incomeItems.get(i).getCreatedAt();
            String str = a;

            String regex = "\\-\\d{2}-\\d{2}";
            Matcher matcher = Pattern.compile(regex).matcher(str);
            while (matcher.find()) {
                result = matcher.group();
                Log.i("Horizontal", String.valueOf(result));
            }
            itemHomePageAdapter.notifyDataSetChanged();
            if (result=="\\-\\09"){
                ItemMonthModel itemMonthModel = new ItemMonthModel("September");
                Log.d("Horizontal", "September");
            }
        }
    }

    private void loadDataExpense(List<ExpensesItem> expensesItems) {
        int sizeExpense = expensesItems.size();
        for (int i = 0; i < sizeExpense; i++) {
            ItemHompageModel itemHompageModel = new ItemHompageModel(String.valueOf(i + 1), String.valueOf(expensesItems.get(i).getTotal()), expensesItems.get(i).getName(),0);
            hompageModelList.add(itemHompageModel);
            itemHomePageAdapter.notifyDataSetChanged();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
