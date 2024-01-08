package com.example.coffeemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ToDirection extends AppCompatActivity
{
    TextView txtDirections;
    ImageView imgDrections;
    Button btnToFinish;
    ImageButton btnPrev, btnNext;

    // arrays for text direction and image direction
    int steps = 0; // steps for current instruction
    int[] directionsImage = new int[13];
    String[] directionsTxt = new String[13];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_direction);

        txtDirections = findViewById(R.id.txtDirections);
        imgDrections = findViewById(R.id.imgDirections);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        btnToFinish = findViewById(R.id.btnToFinish);

        //retrieve spinner Strings from previous activity
        Intent intentIngRec = getIntent();
        String spinnerSizeVal = intentIngRec.getStringExtra("spinnerSizeVal");
        String spinnerMilkVal = intentIngRec.getStringExtra("spinnerMilkVal");
        String spinnerSweetVal = intentIngRec.getStringExtra("spinnerSweetVal");
        String spinnerFlavVal = intentIngRec.getStringExtra("spinnerFlavVal");
        String txtNumCupVal = intentIngRec.getStringExtra("txtNumCupVal");

        //retrieve ingredient amounts for one cup
        String stringWaterAmount = intentIngRec.getStringExtra("stringWaterAmount");
        String stringCoffeeAmount = intentIngRec.getStringExtra("stringCoffeeAmount");
        String stringMilkAmount = intentIngRec.getStringExtra("stringMilkAmount");
        String stringSweetenerAmount = intentIngRec.getStringExtra("stringSweetenerAmount");
        String stringFlavoringAmount = intentIngRec.getStringExtra("stringFlavoringAmount");

        Intent intentIngSend = new Intent(ToDirection.this, ToRating.class);
        intentIngSend.putExtra("spinnerSizeVal", spinnerSizeVal);
        intentIngSend.putExtra("spinnerMilkVal", spinnerMilkVal);
        intentIngSend.putExtra("spinnerSweetVal", spinnerSweetVal);
        intentIngSend.putExtra("spinnerFlavVal", spinnerFlavVal);
        intentIngSend.putExtra("txtNumCupVal", txtNumCupVal);

        // assign direction steps in array
        directionsTxt[0] = "These steps will be for making a single " + spinnerSizeVal + " cup. Repeat these steps till you've have made your " + txtNumCupVal + " cups!";
        directionsTxt[1] = "Put " + stringWaterAmount + " tablespoons of water in the espresso machine";
        directionsTxt[2] = "Put " + stringCoffeeAmount + " tablespoons of coffee into the portafilter and press it down.";
        directionsTxt[3] = "Place the portafilter into the machine and lock it.";
        directionsTxt[4] = "Place your cup under the machine and gather the shot.";
        directionsTxt[5] = "Once the shot is pulled, add " + stringSweetenerAmount + " teaspoons of " + spinnerSweetVal + " and " + stringFlavoringAmount + " teaspoons of " + spinnerFlavVal;
        directionsTxt[6] = "Foam the milk by placing " + stringMilkAmount + " ounces of " + spinnerMilkVal + " in a measuring cup and insert the steam wand into the container with the " + spinnerMilkVal;
        directionsTxt[7] = "Engage the steam wand on your espresso machine and make sure to keep the tip of the wand towards the bottom of the container";
        directionsTxt[8] = "Move the container higher, lower, closer, then further away so that the steam wand can incorporate the air into the " + spinnerMilkVal + " making the foam.";
        directionsTxt[9] = "Once the " + spinnerMilkVal + " has foamed to double its size, turn off the steam wand.";
        directionsTxt[10] = "Assemble the Latte by using a spoon to retain the microbubbles on top of the steamed " + spinnerMilkVal + " and pour the bottom 2/3 of the steamed " + spinnerMilkVal + " into the cup.";
        directionsTxt[11] = "Top the latte with the remaining bubbles by pouring or spooning them onto the drink.";
        directionsTxt[12] = "Enjoy your latte!";

        // assign direction images in array
        directionsImage[0] = R.drawable.step_1;
        directionsImage[1] = R.drawable.step_2;
        directionsImage[2] = R.drawable.step_3;
        directionsImage[3] = R.drawable.step_4;
        directionsImage[4] = R.drawable.step_5;
        directionsImage[5] = R.drawable.step_6;
        directionsImage[6] = R.drawable.step_7;
        directionsImage[7] = R.drawable.step_8;
        directionsImage[8] = R.drawable.step_9;
        directionsImage[9] = R.drawable.step_10;
        directionsImage[10] = R.drawable.step_11;
        directionsImage[11] = R.drawable.step_12;
        directionsImage[12] = R.drawable.step_13;

        updateInstruction(steps);

        btnPrev.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Decrement the current step and update the direction and image
                if (steps > 0)
                {
                    steps--;
                    updateInstruction(steps);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Increment the current step and update the direction and image
                if (steps < directionsImage.length - 1)
                {
                    steps++;
                    updateInstruction(steps);
                }
            }
        });

        btnToFinish.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(intentIngSend);
            }
        });

        imgDrections.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v) {
                imgDrections.setImageResource(R.drawable.icecap);
                txtDirections.setText("You found the secret ice cap :)");
                return true; // Return true to indicate that you have handled the long click event
            }
        });
    }
    // Update the instruction and image based on the current step
    private void updateInstruction(int step)
    {
        imgDrections.setImageResource(directionsImage[step]);
        txtDirections.setText(directionsTxt[step]);
    }
}