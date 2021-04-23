package com.example.mobileCw.carQuiz;


import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdvancedLevelFragment extends Fragment {


    public AdvancedLevelFragment() {
        // Required empty public constructor
    }
    Boolean isTimed=false;
    CountDownTimer countDownTimer;
    TextView txtTime,txtCorrect,textCar1,txtCar2,txtCar3,txtScore;
    Button btnNext,btnSubmit;
    ImageView carIm1,carImg2,carImg3;
    EditText editText1,editText2,editText3;
    int random1,random2,random3,countCorrect=0,round=0,totalScore=0;
    int resourceId1,resourceId2,resourceId3;
    ArrayList<CarsModel> carList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_advanced_level, container, false);
        carList = getArguments().getParcelableArrayList("carsArray");
        isTimed = getArguments().getBoolean("isTimed");
        txtTime = (TextView) view.findViewById(R.id.txtTime);
        txtCorrect = (TextView) view.findViewById(R.id.txtCorrect);
        carIm1 = (ImageView) view.findViewById(R.id.imageCar1);
        carImg2 = (ImageView) view.findViewById(R.id.imageCar2);
        carImg3 = (ImageView) view.findViewById(R.id.imageCar3);
        editText1 = (EditText) view.findViewById(R.id.editBrand1);
        editText2 = (EditText) view.findViewById(R.id.editBrand2);
        editText3 = (EditText) view.findViewById(R.id.editBrand3);
        textCar1 = (TextView) view.findViewById(R.id.txtcar1);
        txtCar2 = (TextView) view.findViewById(R.id.txtcar2);
        txtCar3 = (TextView) view.findViewById(R.id.txtcar3);
        txtScore = (TextView) view.findViewById(R.id.txtScore);
        btnNext = (Button) view.findViewById(R.id.btnNext);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        changeImg();
        txtScore.setText("Score: "+(totalScore+countCorrect));
        textCar1.setVisibility(View.GONE);
        txtCar2.setVisibility(View.GONE);
        txtCar3.setVisibility(View.GONE);
        txtCorrect.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);
        if(!isTimed){
            txtTime.setVisibility(View.GONE);
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitClicked();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImg();
                round=0;
                countCorrect=0;
                txtScore.setText("Score: "+(totalScore+countCorrect));
                textCar1.setVisibility(View.GONE);
                txtCar2.setVisibility(View.GONE);
                txtCar3.setVisibility(View.GONE);
                txtCorrect.setVisibility(View.GONE);
                editText1.setText("");
                editText1.setTextColor(getResources().getColor(R.color.colorBlack));
                editText2.setText("");
                editText2.setTextColor(getResources().getColor(R.color.colorBlack));
                editText3.setText("");
                editText3.setTextColor(getResources().getColor(R.color.colorBlack));
                btnSubmit.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.GONE);
                if(isTimed){
                    countDownTimer.start();
                }
            }
        });

        countDownTimer = new CountDownTimer(20000, 1000) {

            public void onTick(long millisUntilFinished) {
                txtTime.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                submitClicked();
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
        Resources resources = getResources();
        resourceId1 = resources.getIdentifier(carList.get(random1).getCarimg().toLowerCase(),"drawable", getActivity().getPackageName());
        resourceId2 = resources.getIdentifier(carList.get(random2).getCarimg().toLowerCase(),"drawable", getActivity().getPackageName());
        resourceId3 = resources.getIdentifier(carList.get(random3).getCarimg().toLowerCase(),"drawable", getActivity().getPackageName());

        carIm1.setImageResource(resourceId1);
        carImg2.setImageResource(resourceId2);
        carImg3.setImageResource(resourceId3);

        editText1.setEnabled(true);
        editText2.setEnabled(true);
        editText3.setEnabled(true);

    }
//this methode checks whether user enterd car name correct or not and the point will calculate
    private void submitClicked(){
        countCorrect=0;
        round++;
        if(editText1.getText().toString()==null)
        {
            if(round==3) {
                textCar1.setText(carList.get(random1).getCarbrand());
                textCar1.setTextColor(getResources().getColor(R.color.colorPrimary));
                textCar1.setVisibility(View.VISIBLE);
            }
            editText1.setTextColor(getResources().getColor(R.color.colorWrong));
            editText1.setEnabled(true);
        }else
        if(carList.get(random1).getCarbrand().toLowerCase().equals(editText1.getText().toString().toLowerCase())){
            countCorrect++;
            editText1.setTextColor(getResources().getColor(R.color.colorCorrect));
            textCar1.setVisibility(View.GONE);
            editText1.setEnabled(false);
        }else{
            if(round==3) {
                textCar1.setText(carList.get(random1).getCarbrand());
                textCar1.setTextColor(getResources().getColor(R.color.colorPrimary));
                textCar1.setVisibility(View.VISIBLE);
            }
            editText1.setTextColor(getResources().getColor(R.color.colorWrong));
            editText1.setEnabled(true);
        }

        if(editText2.getText().toString()==null)
        {
            if(round==3) {
                txtCar2.setText(carList.get(random2).getCarbrand());
                txtCar2.setTextColor(getResources().getColor(R.color.colorPrimary));
                txtCar2.setVisibility(View.VISIBLE);
            }
            editText2.setTextColor(getResources().getColor(R.color.colorWrong));
            editText2.setEnabled(true);
        }else
        if(carList.get(random2).getCarbrand().toLowerCase().equals(editText2.getText().toString().toLowerCase())){
            countCorrect++;
            editText2.setTextColor(getResources().getColor(R.color.colorCorrect));
            txtCar2.setVisibility(View.GONE);
            editText2.setEnabled(false);
        }else{
            if(round==3) {
                txtCar2.setText(carList.get(random2).getCarbrand());
                txtCar2.setTextColor(getResources().getColor(R.color.colorPrimary));
                txtCar2.setVisibility(View.VISIBLE);
            }
            editText2.setTextColor(getResources().getColor(R.color.colorWrong));
            editText2.setEnabled(true);
        }

        if(editText3.getText().toString()==null)
        {
            if(round==3) {
                txtCar3.setText(carList.get(random3).getCarbrand());
                txtCar3.setTextColor(getResources().getColor(R.color.colorPrimary));
                txtCar3.setVisibility(View.VISIBLE);
            }
            editText3.setTextColor(getResources().getColor(R.color.colorWrong));
            editText3.setEnabled(true);
        }else
        if(carList.get(random3).getCarbrand().toLowerCase().equals(editText3.getText().toString().toLowerCase())){
            countCorrect++;
            editText3.setTextColor(getResources().getColor(R.color.colorCorrect));
            txtCar3.setVisibility(View.GONE);
            editText3.setEnabled(false);
        }else{
            if(round==3) {
                txtCar3.setText(carList.get(random3).getCarbrand());
                txtCar3.setTextColor(getResources().getColor(R.color.colorPrimary));
                txtCar3.setVisibility(View.VISIBLE);
            }
            editText3.setTextColor(getResources().getColor(R.color.colorWrong));
            editText3.setEnabled(true);
        }
        txtScore.setText("Score: "+(totalScore+countCorrect));
        if(countCorrect==3){
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
        if(round==3) {
            totalScore=totalScore+countCorrect;
            btnNext.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.GONE);
            if(isTimed){
                countDownTimer.cancel();
            }
        }else{
            if(isTimed){
                countDownTimer.start();
            }
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
