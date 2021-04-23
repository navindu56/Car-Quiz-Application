package com.example.mobileCw.carQuiz;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SplashScreenFragment extends Fragment {


    FragmentTransaction transaction;
    Fragment menuFragment;
    ArrayList<CarsModel> carList;
    Bundle bundle = new Bundle();
    public SplashScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash_screen, container, false);
        menuFragment = new MenuFragment();
        carList = getArguments().getParcelableArrayList("carsArray");
        bundle.putParcelableArrayList("carsArray", carList);
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //Second fragment after 5 seconds appears
                transaction = getFragmentManager().beginTransaction();
                menuFragment.setArguments(bundle);
                transaction.replace(R.id.fragment_container,menuFragment);
//        transaction.addToBackStack(nu;ll);
                transaction.commit();
            }
        };

        handler.postDelayed(runnable, 5000);

        return view;
    }

}
