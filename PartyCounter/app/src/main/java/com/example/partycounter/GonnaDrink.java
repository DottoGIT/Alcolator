package com.example.partycounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GonnaDrink extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gonna_drink);
        ChangeLanguage();
    }


    private void ChangeLanguage()
    {

        switch(MainActivity.GetLanguage())
        {
            case Polish:
                break;

            case English:
                break;
        }
    }
}