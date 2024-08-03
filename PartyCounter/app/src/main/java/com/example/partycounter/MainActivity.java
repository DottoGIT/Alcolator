package com.example.partycounter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

enum Languages{
    Polish, // 1
    English // 2

}


public class MainActivity extends AppCompatActivity
{
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String LANGUAGE = "lng";

    private static Languages currentLanguage;
    public static Languages GetLanguage()
    {
        return currentLanguage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(sharedPreferences.getInt(LANGUAGE, 0) == 0)
        {
            editor.putInt(LANGUAGE, 1);
            editor.apply();
        }
        switch (sharedPreferences.getInt(LANGUAGE,0))
        {
            case 1: currentLanguage = Languages.Polish;
            break;
            case 2: currentLanguage = Languages.English;
            break;
        }


        ChangeLanguage();

    }

    public void AlcolatorClick(View view){
        Intent intent = new Intent(this, AlculatorChoice.class);
        startActivity(intent);
    }

    public void LanguageButtonClick(View view)
    {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Button lngBtn = findViewById(R.id.languageButton);

        if(currentLanguage == Languages.English)
        {
            editor.putInt(LANGUAGE, 1);
            currentLanguage = Languages.Polish;
            lngBtn.setText("pl");
            Drawable drawable = getDrawable(R.drawable.ic_poland);
            lngBtn.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        }
        else if (currentLanguage == Languages.Polish)
        {
            editor.putInt(LANGUAGE, 2);
            currentLanguage = Languages.English;
            lngBtn.setText("eng");
            Drawable drawable = getDrawable(R.drawable.ic_united_kingdom);
            lngBtn.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null,null);
        }
        editor.apply();


        ChangeLanguage();
    }

    private void ChangeLanguage()
    {

        Button lngBtn = findViewById(R.id.languageButton);

        if(GetLanguage() == Languages.Polish)
        {
            lngBtn.setText("pl");
            Drawable drawable = getDrawable(R.drawable.ic_poland);
            lngBtn.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        }
        else if (GetLanguage() == Languages.English)
        {
            lngBtn.setText("eng");
            Drawable drawable = getDrawable(R.drawable.ic_united_kingdom);
            lngBtn.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null,null);
        }



        Button napiwek = findViewById(R.id.tipBtn);
        Button alcolator = findViewById(R.id.alcolatorBtn);
        Button kociolek = findViewById(R.id.kociolBtn);
        Button zapisane = findViewById(R.id.zapisaneBtn);

        switch(currentLanguage)
        {
            case Polish:
                napiwek.setText("Napiwek");
                alcolator.setText("Alkolator");
                kociolek.setText("Kociołek");
                zapisane.setText("Twoje Trunki");
                break;

            case English:
                napiwek.setText("Tips");
                alcolator.setText("Alcolator");
                kociolek.setText("Punch");
                zapisane.setText("Your Drinks");
                break;
        }
    }
}