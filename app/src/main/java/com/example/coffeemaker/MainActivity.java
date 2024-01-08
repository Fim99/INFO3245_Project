package com.example.coffeemaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button btnToLatte;
    Button btnToCapp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnToCapp= findViewById(R.id.btnToCapp);
        btnToLatte = findViewById(R.id.btnToLatte);

        /*
        btnToCapp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(MainActivity.this, ToCappSelect.class );
                startActivity(intent);

            }
        });
         */

        btnToLatte.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(MainActivity.this, ToLatteSelect.class);
                startActivity(intent);


            }
        });
    }
}