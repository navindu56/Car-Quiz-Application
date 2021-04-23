package com.example.mobileCw.carQuiz;


import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {
/* ---creating variables for all buttons---- */
    Button guessCarBtn,guessHintBtn,guessBrandBtn,advancedLevelBtn,aboutApp;
    /*---creating variables for all the Fragments ---- */
    Fragment guessTheCarFrag,guessHintsFrag,guessTheBrandrag,advancedLevelFrag,aboutUsFrag;
    FragmentTransaction transaction;
    /*---creating a variable for Timer---- */
    Switch timerSwitch;
    /*---Defining the ArrayList--- */
    ArrayList<CarsModel> carList;
    /*---Creating a Bundle Object--- */
    Bundle bundle = new Bundle();
    public MenuFragment() {
        // Required empty public constructor
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        /*---Assigning the created button variables for button IDs--- */

        guessCarBtn = (Button) view.findViewById(R.id.guessCar);
        guessBrandBtn = (Button) view.findViewById(R.id.guessBrand);
        guessHintBtn = (Button) view.findViewById(R.id.guessHint);
        advancedLevelBtn = (Button) view.findViewById(R.id.advancedLevel);
        aboutApp=(Button) view.findViewById(R.id.aboutApp);


        /*---Assigning the created Switch variables for Timer ID--- */
        timerSwitch = (Switch) view.findViewById(R.id.timerSwitch);

        /*---Assigning the created Fragment variables for Fragment classes--- */
        guessTheCarFrag = new GuessTheCarFragment();
        guessHintsFrag = new GuessHintsFragment();
        guessTheBrandrag = new GuessTheBrandFragment();
        advancedLevelFrag = new AdvancedLevelFragment();
        aboutUsFrag = new AboutUs();

        /*---Loading each Fragment when the specific button is clicked--- */
        guessCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(guessTheCarFrag);
            }
        });
        guessHintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(guessHintsFrag);
            }
        });
        guessBrandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(guessTheBrandrag);
            }
        });
        advancedLevelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(advancedLevelFrag);
            }
        });
        aboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(aboutUsFrag);
            }
        });


        carList = getArguments().getParcelableArrayList("carsArray");
        bundle.putParcelableArrayList("carsArray", carList);
        return view;
    }

//checking whether the timer switch is on or off
    private void changeFragment(Fragment fragment){
        if(!fragment.isAdded()) {
            bundle.putBoolean("isTimed",timerSwitch.isChecked() );
            fragment.setArguments(bundle);
            transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

}
