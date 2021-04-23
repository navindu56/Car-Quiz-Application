package com.example.mobileCw.carQuiz;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Home extends Activity {
    Fragment menuFragment;
    /*---creating a variable for SplashScreen---- */
    Fragment splashScreen;
    FragmentTransaction transaction;
    ArrayList<CarsModel> carList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle bundle = new Bundle();
//        map data to arreylist
        try {
        JSONObject data = new JSONObject(loadJSONFromAsset());
        JSONArray jsonArray = data.getJSONArray("carlist");
            carList = new ArrayList<CarsModel>();
        CarsModel carModel;
        for(int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject object = jsonArray.getJSONObject(i);
            carModel = new CarsModel();
            carModel.setId(i);
            carModel.setCarimg(object.getString("image"));
            carModel.setCarbrand(object.getString("car"));
            carList.add(carModel);
        }
            bundle.putParcelableArrayList("carsArray", carList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//   assign menu fragment after the splash screen fragment

//        menuFragment = new MenuFragment();
        splashScreen = new SplashScreenFragment();
        splashScreen.setArguments(bundle);
//        menuFragment.setArguments(bundle);
        transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,splashScreen);
//        transaction.addToBackStack(nu;ll);
        transaction.commit();

    }

    public String loadJSONFromAsset() {//read json file into a string
        String json = null;
        try {
            InputStream is = this.getAssets().open("cars.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
