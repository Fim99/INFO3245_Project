package com.example.coffeemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class ToRating extends AppCompatActivity
{
    TextView txtDataRec;
    EditText txtProfile;
    Button btnGetStars, btnToMain;
    RatingBar ratingBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_rating);

        txtDataRec = findViewById(R.id.txtDataRec);
        ratingBar1 = findViewById(R.id.ratingBar1);
        txtProfile = findViewById(R.id.txtProfile);
        btnGetStars = findViewById(R.id.btnGetStars);
        btnToMain = findViewById(R.id.btnToMain);

        //retrieve spinner Strings from previous activity
        Intent intentIngRec = getIntent();
        String spinnerSizeVal = intentIngRec.getStringExtra("spinnerSizeVal");
        String spinnerMilkVal = intentIngRec.getStringExtra("spinnerMilkVal");
        String spinnerSweetVal = intentIngRec.getStringExtra("spinnerSweetVal");
        String spinnerFlavVal = intentIngRec.getStringExtra("spinnerFlavVal");
        String txtNumCupVal = intentIngRec.getStringExtra("txtNumCupVal");

        SharedPreferences userSelection = getSharedPreferences("myPrefs", MODE_PRIVATE);

        btnGetStars.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // get rating and profile name from user
                String ratingUser = String.valueOf(ratingBar1.getRating());
                String userProfileName = String.valueOf(txtProfile.getText());

                // use SharedPreferences to save user options with profile name as key
                SharedPreferences.Editor editor = userSelection.edit();
                editor.putString(userProfileName + "_spinnerSizeVal", spinnerSizeVal);
                editor.putString(userProfileName + "_spinnerMilkVal", spinnerMilkVal);
                editor.putString(userProfileName + "_spinnerSweetVal", spinnerSweetVal);
                editor.putString(userProfileName + "_spinnerFlavVal", spinnerFlavVal);
                editor.putString(userProfileName + "_txtNumCupVal", txtNumCupVal);
                editor.putString(userProfileName + "_ratingUser", ratingUser);
                editor.commit();

                int numOfStars = ratingBar1.getNumStars();
                float getrating = ratingBar1.getRating();
                txtDataRec.setText("Thanks for using our app! you rated your beverage: "+getrating+"/"+numOfStars + " stars, your profile will now show in the selection screen once you refresh!" );
            }
        });

        btnToMain.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ToRating.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}