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
import java.util.Arrays;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class GuessHintsFragment extends Fragment {


    public GuessHintsFragment() {
        // Required empty public constructor
    }
    Boolean isTimed=false;
    CountDownTimer countDownTimer;
    TextView txtTime,txtCorrectCar,txtCorrect,txtCar;
    Button btnSubmit,btnNext;
    ImageView carImg;
    EditText editGuessChar;
    int random,wrongCount=0;
    String carName;
    StringBuilder temp;
    ArrayList<CarsModel> carList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_guess_hints, container, false);
        carList = getArguments().getParcelableArrayList("carsArray");
        isTimed = getArguments().getBoolean("isTimed");
        txtTime = (TextView) view.findViewById(R.id.txtTime);
        txtCorrectCar = (TextView) view.findViewById(R.id.txCorrectCar);
        txtCorrect = (TextView) view.findViewById(R.id.txtCorrect);
        txtCar = (TextView) view.findViewById(R.id.txtBrand);
        carImg = (ImageView) view.findViewById(R.id.carImg);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        btnNext = (Button) view.findViewById(R.id.btnNext);
        editGuessChar = (EditText)  view.findViewById(R.id.editGuessChar);
        txtCorrect.setVisibility(View.GONE);
        txtCorrectCar.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);
        if(!isTimed){
            txtTime.setVisibility(View.GONE);
        }
        changeImg();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImg();
                wrongCount=0;
                txtCorrect.setVisibility(View.GONE);
                txtCorrectCar.setVisibility(View.GONE);
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
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                submit();
            }

        };
        if(isTimed){
            countDownTimer.start();
        }
        return  view;
    }
    // //this method will genarate a random car image from arraylist using Random
    private void changeImg(){
        Random randomGenerator = new Random();
        random =randomGenerator.nextInt(carList.size());
//        random = new Random().nextInt((28 - 0) + 0) + 0;
//        random = 1;
        Resources resources = getResources();
        final int resourceId = resources.getIdentifier(carList.get(random).getCarimg().toLowerCase(),"drawable", getActivity().getPackageName());
        carImg.setImageResource(resourceId);
        setBrand();
    }

    private void  setBrand() {
        carName = carList.get(random).getCarbrand().toUpperCase(); //select a random car brand from arraylist
        if (carName.length() > 0) {
            char[] array = new char[carName.length()];
            Arrays.fill(array, '-');//fill the chars as "_" up to the length of the brand name
            temp = new StringBuilder(new String(array));
        } else {
            temp = new StringBuilder();
        }
        txtCar.setText(temp.toString());
            String guess=" ";
            char c = guess.charAt(0);
            for (int i = carName.indexOf(c); i >= 0; i = carName.indexOf(c, i + 1)) {
                temp.setCharAt(i, c);
            }
        txtCar.setText(temp.toString());

    }
    //    when the user type character and click the submit button ,this method will get invoked
    private void submit(){
        String guess = editGuessChar.getText().toString();
        if(guess!=null && !guess.isEmpty()){
            char c = guess.charAt(0);
            for (int i = carName.indexOf(c); i >= 0; i = carName.indexOf(c, i + 1)) {
                temp.setCharAt(i, c);
            }
            if(txtCar.getText().toString().equals(temp.toString())){
                txtCorrect.setText("Incorrect Character!");
                txtCorrect.setTextColor(getResources().getColor(R.color.colorYellow));
                txtCorrect.setVisibility(View.VISIBLE);
                wrongCount++;
            }else{
                txtCorrect.setVisibility(View.GONE);
            }

            if (carName.equals(temp.toString())) {
                txtCorrect.setText("CORRECT!");
                txtCorrect.setTextColor(getResources().getColor(R.color.colorCorrect));
                txtCorrect.setVisibility(View.VISIBLE);

                if(isTimed){
                    countDownTimer.cancel();
                }
            }
            txtCar.setText(temp.toString());

        }else {
            txtCorrect.setText("Incorrect Character!");
            txtCorrect.setTextColor(getResources().getColor(R.color.colorYellow));
            txtCorrect.setVisibility(View.VISIBLE);
            wrongCount++;
        }
        editGuessChar.setText("");

        if(wrongCount==3){
            txtCorrect.setText("WRONG!");
            txtCorrect.setTextColor(getResources().getColor(R.color.colorWrong));
            txtCorrect.setVisibility(View.VISIBLE);
            txtCorrectCar.setText(carList.get(random).getCarbrand());
            txtCorrectCar.setTextColor(getResources().getColor(R.color.colorPrimary));
            txtCorrectCar.setVisibility(View.VISIBLE);
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
        btnNext.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        if(isTimed){
            countDownTimer.cancel();
        }
    }

}
