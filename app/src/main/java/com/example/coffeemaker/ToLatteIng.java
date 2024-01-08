package com.example.coffeemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ToLatteIng extends AppCompatActivity
{
    TextView txtIng;
    TextView txtWater;
    TextView txtMilk;
    TextView txtCoffee;
    TextView txtSweet;
    TextView txtFlav;
    Button btnToDirection;

    TextView txtTest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_latte_ing);

        txtIng = findViewById(R.id.txtIng);
        txtWater = findViewById(R.id.txtWater);
        txtMilk = findViewById(R.id.txtMilk);
        txtCoffee =  findViewById(R.id.txtCoffee);
        txtSweet = findViewById(R.id.txtSweet);
        txtFlav = findViewById(R.id.txtFlav);
        btnToDirection = findViewById(R.id.btnToDirection);

        //retrieve spinner Strings from previous activity
        Intent intentIngRec = getIntent();
        String spinnerSizeVal = intentIngRec.getStringExtra("spinnerSizeVal");
        String spinnerMilkVal = intentIngRec.getStringExtra("spinnerMilkVal");
        String spinnerSweetVal = intentIngRec.getStringExtra("spinnerSweetVal");
        String spinnerFlavVal = intentIngRec.getStringExtra("spinnerFlavVal");
        String txtNumCupVal = intentIngRec.getStringExtra("txtNumCupVal");

        // declare variables for ingredient calculations
        // define ingredients per ounce
        double milkPerOunce = 0.75; // in ounces
        double sweetenerPerOunce = 0.020833375; // in ounces
        double flavoringPerOunce = 0.0104166875; // in ounces

        double milkAmount = 0;
        double sweetenerAmount = 0;
        double flavoringAmount = 0;
        double waterAmount = 0;
        double coffeeAmount = 0;

        //calculate various ingredients based on user selection
        switch(spinnerSizeVal)
        {
            case "Small (8 oz)":
                milkAmount = 8 * milkPerOunce;
                sweetenerAmount = 8 * sweetenerPerOunce;
                flavoringAmount = 8 * flavoringPerOunce;
                waterAmount = 1;
                coffeeAmount = 1;
                break;
            case "Medium (12 oz)":
                milkAmount = 12 * milkPerOunce;
                sweetenerAmount = 12 * sweetenerPerOunce;
                flavoringAmount = 12 * flavoringPerOunce;
                waterAmount = 1 * 1.25;
                coffeeAmount = 1 * 1.25;
                break;
            case "Large (16 oz)":
                milkAmount = 16 * milkPerOunce;
                sweetenerAmount = 16 * sweetenerPerOunce;
                flavoringAmount = 16 * flavoringPerOunce;
                waterAmount = 1 * 1.50;
                coffeeAmount = 1 * 1.50;
                break;
        }

        switch(spinnerMilkVal)
        {
            case "Milk":
                // no adjustment needed
                break;
            case "Almond Milk":
                // no adjustment needed
                break;
            case "Soy Milk":
                // no adjustment needed
                break;
        }

        switch(spinnerSweetVal)
        {
            case "Syrup":
                // no adjustment needed
                break;
            case "Honey":
                // no adjustment needed
                break;
            case "Brown Sugar":
                // no adjustment needed
                break;
        }

        switch(spinnerFlavVal)
        {
            case "Vanilla":
                // no adjustment needed
                break;
            case "Cinnamon":
                flavoringAmount *= 0.125;
                break;
            case "Nutmeg":
                flavoringAmount *= 0.125;
                break;
        }

        int intNumCups = Integer.parseInt(txtNumCupVal);

        // calculate amounts by multiplying by number of cups
        milkAmount *= intNumCups;
        sweetenerAmount *= intNumCups;
        flavoringAmount *= intNumCups;
        waterAmount *= intNumCups;
        coffeeAmount *= intNumCups;

        // convert oz to tsp and round number
        sweetenerAmount =  Math.round(sweetenerAmount * 1000.0 * 6.0) / 1000.0;
        flavoringAmount = Math.round(flavoringAmount * 1000.0 * 6.0) / 1000.0;

        // put outputs into Strings
        String totalIng = "To make " + txtNumCupVal + " " + spinnerSizeVal + " " + spinnerMilkVal + " latte with " +
                           spinnerSweetVal + " and " + spinnerFlavVal + "\n\nyou will need:";
        String totalWater = "• " + waterAmount + " tablespoons of Water";
        String totalMilk = "• " +milkAmount + " ounces of " + spinnerMilkVal;
        String totalCoffee = "• " +coffeeAmount + " tablespoons of Coffee";
        String totalSweet = "• " +sweetenerAmount + " teaspoons of " + spinnerSweetVal;
        String totalFlav = "• " +flavoringAmount + " teaspoons of " + spinnerFlavVal;

        // setText the outputs to be displayed
        txtIng.setText(totalIng);
        txtWater.setText(totalWater);
        txtMilk.setText(totalMilk);
        txtCoffee.setText(totalCoffee);
        txtSweet.setText(totalSweet);
        txtFlav.setText(totalFlav);

        // convert to string to be sent to directions
        String stringWaterAmount = String.valueOf(waterAmount / intNumCups);
        String stringCoffeeAmount = String.valueOf(coffeeAmount / intNumCups);
        String stringMilkAmount = String.valueOf(milkAmount / intNumCups);
        String stringSweetenerAmount = String.valueOf(Math.round((sweetenerAmount / intNumCups) * 1000.0) / 1000.0);
        String stringFlavoringAmount = String.valueOf(Math.round((flavoringAmount / intNumCups) * 1000.0) / 1000.0);

        Intent intentIngSend = new Intent(ToLatteIng.this, ToDirection.class);
        intentIngSend.putExtra("spinnerSizeVal", spinnerSizeVal);
        intentIngSend.putExtra("spinnerMilkVal", spinnerMilkVal);
        intentIngSend.putExtra("spinnerSweetVal", spinnerSweetVal);
        intentIngSend.putExtra("spinnerFlavVal", spinnerFlavVal);
        intentIngSend.putExtra("txtNumCupVal", txtNumCupVal);

        intentIngSend.putExtra("stringWaterAmount", stringWaterAmount);
        intentIngSend.putExtra("stringCoffeeAmount", stringCoffeeAmount);
        intentIngSend.putExtra("stringMilkAmount", stringMilkAmount);
        intentIngSend.putExtra("stringSweetenerAmount", stringSweetenerAmount);
        intentIngSend.putExtra("stringFlavoringAmount", stringFlavoringAmount);

        btnToDirection.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(intentIngSend);
            }
        });
    }
}