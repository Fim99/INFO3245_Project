package com.example.coffeemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ToLatteSelect extends AppCompatActivity
{
    Button btnToIng, btnRecProfile, btnDelProfile, btnRefresh;
    Spinner spinnerSize, spinnerMilk, spinnerSweet, spinnerFlav, spinnerProfile;
    EditText txtNumCup;
    RatingBar ratingBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_latte_select);

        txtNumCup = findViewById(R.id.txtNumCup);
        spinnerProfile = findViewById(R.id.spinnerProfile);
        spinnerSize = findViewById(R.id.spinnerSize);
        spinnerMilk = findViewById(R.id.spinnerMilk);
        spinnerSweet = findViewById(R.id.spinnerSweet);
        spinnerFlav = findViewById(R.id.spinnerFlav);
        btnToIng = findViewById(R.id.btnToIng);
        btnRecProfile = findViewById(R.id.btnRecProfile);
        btnRefresh = findViewById(R.id.btnRefresh);
        btnDelProfile = findViewById(R.id.btnDelProfile);
        ratingBar1 = findViewById(R.id.ratingBar1);

        // assign values to spinners
        String[] items1 = {"Small (8 oz)", "Medium (12 oz)", "Large (16 oz)"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.spinner_item, items1);
        spinnerSize.setAdapter(adapter1);

        String[] items2 = {"Milk", "Almond Milk", "Soy Milk"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.spinner_item, items2);
        spinnerMilk.setAdapter(adapter2);

        String[] items3 = {"Syrup", "Honey", "Brown Sugar"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, R.layout.spinner_item, items3);
        spinnerSweet.setAdapter(adapter3);

        String[] items4 = {"Vanilla", "Cinnamon", "Nutmeg"};
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, R.layout.spinner_item, items4);
        spinnerFlav.setAdapter(adapter4);

        SharedPreferences UserSelection = getSharedPreferences("myPrefs", MODE_PRIVATE);

        // Get a list of all saved profile names
        Map<String, ?> allPrefs = UserSelection.getAll();
        List<String> profileNames = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allPrefs.entrySet())
        {
            String key = entry.getKey();
            if (key.endsWith("_spinnerSizeVal"))
            {
                profileNames.add(key.substring(0, key.indexOf("_")));
            }
        }

        // Populate the spinner with the list of profile names
        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(this, R.layout.spinner_item, profileNames);
        spinnerProfile.setAdapter(adapter5);

        btnRecProfile.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                // Check if there are any saved profiles
                if (profileNames.isEmpty()) {
                    // Display an error message
                    Toast.makeText(ToLatteSelect.this, "No profiles to load", Toast.LENGTH_SHORT).show();
                    return;
                }

                String selectedProfile = spinnerProfile.getSelectedItem().toString();
                String savedSpinnerSizeVal = UserSelection.getString(selectedProfile + "_spinnerSizeVal", "");
                String savedSpinnerMilkVal = UserSelection.getString(selectedProfile + "_spinnerMilkVal", "");
                String savedSpinnerSweetVal = UserSelection.getString(selectedProfile + "_spinnerSweetVal", "");
                String savedSpinnerFlavVal = UserSelection.getString(selectedProfile + "_spinnerFlavVal", "");
                String savedTxtNumCupVal = UserSelection.getString(selectedProfile + "_txtNumCupVal", "");
                String savedRatingUser = UserSelection.getString(selectedProfile + "_ratingUser", "");

                spinnerSize.setSelection(getIndex(spinnerSize, savedSpinnerSizeVal));
                spinnerMilk.setSelection(getIndex(spinnerMilk, savedSpinnerMilkVal));
                spinnerSweet.setSelection(getIndex(spinnerSweet, savedSpinnerSweetVal));
                spinnerFlav.setSelection(getIndex(spinnerFlav, savedSpinnerFlavVal));
                txtNumCup.setText(savedTxtNumCupVal);
                ratingBar1.setRating(Float.parseFloat(savedRatingUser));

            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // refresh the spinner adapter
                Map<String, ?> allPrefs = UserSelection.getAll();
                List<String> profileNames = new ArrayList<>();
                for (Map.Entry<String, ?> entry : allPrefs.entrySet())
                {
                    String key = entry.getKey();
                    if (key.endsWith("_spinnerSizeVal"))
                    {
                        profileNames.add(key.substring(0, key.indexOf("_")));
                    }
                }

                // Update the adapter for the spinner
                adapter5.clear();
                adapter5.addAll(profileNames);
                adapter5.notifyDataSetChanged();
            }
        });

        btnDelProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Check if there are any saved profiles
                if (profileNames.isEmpty())
                {
                    // Display an error message
                    Toast.makeText(ToLatteSelect.this, "No profiles to delete", Toast.LENGTH_SHORT).show();
                    return;
                }

                String selectedProfile = spinnerProfile.getSelectedItem().toString();

                // remove the selected profile's values from SharedPreferences
                SharedPreferences.Editor editor = UserSelection.edit();
                editor.remove(selectedProfile + "_spinnerSizeVal");
                editor.remove(selectedProfile + "_spinnerMilkVal");
                editor.remove(selectedProfile + "_spinnerSweetVal");
                editor.remove(selectedProfile + "_spinnerFlavVal");
                editor.remove(selectedProfile + "_txtNumCupVal");
                editor.remove(selectedProfile + "_ratingUser");
                editor.apply();

                // refresh the spinner adapter
                Map<String, ?> allPrefs = UserSelection.getAll();
                List<String> profileNames = new ArrayList<>();
                for (Map.Entry<String, ?> entry : allPrefs.entrySet())
                {
                    String key = entry.getKey();
                    if (key.endsWith("_spinnerSizeVal"))
                    {
                        profileNames.add(key.substring(0, key.indexOf("_")));
                    }
                }
                adapter5.clear();
                adapter5.addAll(profileNames);
                adapter5.notifyDataSetChanged();
            }
        });

        btnToIng.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String stringNumCups = txtNumCup.getText().toString();
                int numCupsInt = 0;
                try
                {
                    numCupsInt = Integer.parseInt(stringNumCups);
                }
                catch (NumberFormatException e)
                {
                    txtNumCup.setError("Please enter a valid cup size");
                    return;
                }

                // convert spinner values to Strings
                String spinnerSizeVal = spinnerSize.getSelectedItem().toString();
                String spinnerMilkVal = spinnerMilk.getSelectedItem().toString();
                String spinnerSweetVal = spinnerSweet.getSelectedItem().toString();
                String spinnerFlavVal = spinnerFlav.getSelectedItem().toString();
                String txtNumCupVal = txtNumCup.getText().toString();

                // put spinner Strings into data storage to be passed to next activity
                Intent intentIngSend = new Intent(ToLatteSelect.this, ToLatteIng.class);
                intentIngSend.putExtra("spinnerSizeVal", spinnerSizeVal);
                intentIngSend.putExtra("spinnerMilkVal", spinnerMilkVal);
                intentIngSend.putExtra("spinnerSweetVal", spinnerSweetVal);
                intentIngSend.putExtra("spinnerFlavVal", spinnerFlavVal);
                intentIngSend.putExtra("txtNumCupVal", txtNumCupVal);
                startActivity(intentIngSend);
            }
        });
    }
    private int getIndex(Spinner spinner, String value)
    {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++)
        {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value))
            {
                index = i;
                break;
            }
        }
        return index;
    }
}