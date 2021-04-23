package com.example.mobileCw.carQuiz;


import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class GuessTheBrandFragment extends Fragment {

    TextView txtTime,txtCorrect,txtCar;
    Boolean isTimed=false;
    Button btnNext;
    CountDownTimer countDownTimer;
    ImageView carImg1,carIm2,carImg3;
    int random1,random2,random3,randomImage;
    int resourceId1,resourceId2,resourceId3;
    public GuessTheBrandFragment() {
        // Required empty public constructor
    }
    ArrayList<CarsModel> carList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guess_the_brand, container, false);
        carList = getArguments().getParcelableArrayList("carsArray");
        isTimed = getArguments().getBoolean("isTimed");
        txtTime = (TextView) view.findViewById(R.id.txtTime);
        txtCorrect = (TextView) view.findViewById(R.id.txtCorrect);
        txtCar = (TextView) view.findViewById(R.id.txBrand);
        carImg1 = (ImageView) view.findViewById(R.id.imgcar1);
        carIm2 = (ImageView) view.findViewById(R.id.imgcar2);
        carImg3 = (ImageView) view.findViewById(R.id.imgcar3);
        btnNext = (Button) view.findViewById(R.id.btnNext);

        txtCorrect.setVisibility(View.GONE);
        if(!isTimed){
            txtTime.setVisibility(View.GONE);
        }
        changeImg();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImg();
                txtCorrect.setVisibility(View.GONE);
                if(isTimed){
                    countDownTimer.start();
                }
            }
        });

        carImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClicked(1);
            }
        });

        carIm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClicked(2);
            }
        });

        carImg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClicked(3);
            }
        });

        countDownTimer = new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                txtTime.setText("" + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                onClicked(0);
            }

        };
        if(isTimed){
            countDownTimer.start();
        }

        return view;
    }
    // //this method will genarate three random car images from arraylist using Random
    private void changeImg(){
        Random randomGenerator = new Random();

        random1 =randomGenerator.nextInt(carList.size());;
        random2 =randomGenerator.nextInt(carList.size());;
        random3 = randomGenerator.nextInt(carList.size());;
        randomImage = new Random().nextInt((3 - 1) + 1) + 1;
        Resources resources = getResources();
        resourceId1 = resources.getIdentifier(carList.get(random1).getCarimg().toLowerCase(),"drawable", getActivity().getPackageName());
        resourceId2 = resources.getIdentifier(carList.get(random2).getCarimg().toLowerCase(),"drawable", getActivity().getPackageName());
        resourceId3 = resources.getIdentifier(carList.get(random3).getCarimg().toLowerCase(),"drawable", getActivity().getPackageName());

        switch(randomImage){
            case 1:
                carImg1.setImageResource(resourceId1);
                carIm2.setImageResource(resourceId2);
                carImg3.setImageResource(resourceId3);
                break;
            case 2:
                carImg1.setImageResource(resourceId2);
                carIm2.setImageResource(resourceId1);
                carImg3.setImageResource(resourceId3);
                break;
            case 3:
                carImg1.setImageResource(resourceId3);
                carIm2.setImageResource(resourceId2);
                carImg3.setImageResource(resourceId1);
                break;
        }

        txtCar.setText(carList.get(random1).getCarbrand());
    }

    private void onClicked(int no){
        if(randomImage == no){
            txtCorrect.setText("CORRECT!");
            txtCorrect.setTextColor(getResources().getColor(R.color.colorCorrect));
            txtCorrect.setVisibility(View.VISIBLE);
            if(isTimed){
                countDownTimer.cancel();
            }
        }else{
            txtCorrect.setText("WRONG!");
            txtCorrect.setTextColor(getResources().getColor(R.color.colorWrong));
            txtCorrect.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // put your code here...
        if(isTimed){
            countDownTimer.cancel();
        }
    }
}
