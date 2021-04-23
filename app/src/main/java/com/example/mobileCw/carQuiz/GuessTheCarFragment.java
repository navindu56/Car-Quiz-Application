package com.example.mobileCw.carQuiz;


import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class GuessTheCarFragment extends Fragment {


    public GuessTheCarFragment() {
        // Required empty public constructor
    }
    Boolean isTimed=false;
    CountDownTimer countDownTimer;
    TextView txtTime,txtCorrectCar,txtCorrect;
    Button btnSubmit,btnNext;
    Spinner  spinnerCar;
    ImageView imageCar;
    int random;
    ArrayList<CarsModel> carList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guess_the_car, container, false);
        carList = getArguments().getParcelableArrayList("carsArray");
        isTimed = getArguments().getBoolean("isTimed");
        txtTime = (TextView) view.findViewById(R.id.txtTime);
        txtCorrectCar = (TextView) view.findViewById(R.id.txtCorrectCar);
        txtCorrect = (TextView) view.findViewById(R.id.txtCorrect);
        spinnerCar = (Spinner) view.findViewById(R.id.spinnerCar);
        imageCar = (ImageView) view.findViewById(R.id.imageCar);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        btnNext = (Button) view.findViewById(R.id.btnNext);

        txtCorrect.setVisibility(View.GONE);
        txtCorrectCar.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);
        if(!isTimed){
            txtTime.setVisibility(View.GONE);

        }
        ArrayAdapter<CarsModel> arrayAdapter = new ArrayAdapter<CarsModel>(getActivity(),android.R.layout.simple_spinner_item,carList);
        spinnerCar.setAdapter(arrayAdapter);
        changeImg();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtCorrect.setVisibility(View.GONE);
                txtCorrectCar.setVisibility(View.GONE);
                changeImg();
                spinnerCar.setEnabled(true);
                btnSubmit.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.GONE);
                if(isTimed){
                    countDownTimer.start();
                }
//                carList.remove(random);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitClicked();
            }
        });

        countDownTimer = new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                txtTime.setText("" + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                submitClicked();
            }

        };
//        start timer when switch is on
        if(isTimed){
            countDownTimer.start();
        }
        return view;
    }

// //this method will genarate a random car image from arraylist using Random
    private void changeImg(){
        Random randomGenerator = new Random();
        random =randomGenerator.nextInt(carList.size());
//        random = 1;
        Resources resources = getResources();
        final int resourceId = resources.getIdentifier(carList.get(random).getCarimg().toLowerCase(),"drawable", getActivity().getPackageName());
        imageCar.setImageResource(resourceId);

    }

//    when the user select the car brand from the drop down and click the submit button ,this method will get invoked
    private void submitClicked(){
        if(carList.get(random).getCarbrand().equals(spinnerCar.getSelectedItem().toString())){
            txtCorrect.setText("CORRECT!");
            txtCorrect.setTextColor(getResources().getColor(R.color.colorCorrect));
            txtCorrect.setVisibility(View.VISIBLE);
            if(isTimed){
                countDownTimer.cancel();
            }
//            carList.remove(random);
        }else{
            txtCorrect.setText("WRONG!");
            txtCorrect.setTextColor(getResources().getColor(R.color.colorWrong));
            txtCorrect.setVisibility(View.VISIBLE);
            txtCorrectCar.setText(carList.get(random).getCarbrand());
            txtCorrectCar.setTextColor(getResources().getColor(R.color.colorPrimary));
            txtCorrectCar.setVisibility(View.VISIBLE);
            if(isTimed){
                countDownTimer.cancel();
            }
//            carList.remove(random);
        }

        btnNext.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.GONE);
        spinnerCar.setEnabled(false);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        if(isTimed){
            countDownTimer.cancel();
        }
    }
}
