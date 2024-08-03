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
import android.widget.LinearLayout;
import android.widget.TextView;

enum Languages{
    Polish, // 1
    English // 2

}


public class MainActivity extends AppCompatActivity
{
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String LANGUAGE = "lng";
    public static final String WASWARNED = "wswrd";

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


        CheckIfPaid();
        GetAdjustments();
        ChangeLanguage();

    }


    public void GetAdjustments()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);

        if(sharedPreferences.getFloat(AdjustTab.TOLERANCEMEMORY,99) == 99 || sharedPreferences.getFloat(AdjustTab.SPEEDMEMORY,99) == 99|| sharedPreferences.getFloat(AdjustTab.BURNMEMORY,99) == 99)
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat(AdjustTab.TOLERANCEMEMORY, 1);
            editor.putFloat(AdjustTab.BURNMEMORY, 1);
            editor.putFloat(AdjustTab.SPEEDMEMORY, 1);
            editor.apply();
        }

        AdjustTab.TOLERANCE = sharedPreferences.getFloat(AdjustTab.TOLERANCEMEMORY,0);
        AdjustTab.BURNSPEED = sharedPreferences.getFloat(AdjustTab.BURNMEMORY,0);
        AdjustTab.DRUNKSPEED = sharedPreferences.getFloat(AdjustTab.SPEEDMEMORY,0);
    }

    public void CheckIfPaid()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        LinearLayout linear = findViewById(R.id.linear);
        if(sharedPreferences.getBoolean(DonateTab.DIDPAYMEMORY, true)) linear.setVisibility(View.VISIBLE);
        else linear.setVisibility(View.INVISIBLE);
    }

    public void AlcolatorClick(View view){
        if(WasWarned())
        {
            ChoosePerson.LeadsToAlculator = true;
            Intent intent = new Intent(this, ChoosePerson.class);
            startActivity(intent);
        }
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
        TextView donateTxt = findViewById(R.id.donationTxt);

        switch(currentLanguage)
        {
            case Polish:
                napiwek.setText("Napiwek");
                alcolator.setText("Rozpocznij!");
                kociolek.setText("Więcej");
                zapisane.setText("Dostosuj");
                donateTxt.setText("Dzięki za wsparcie!");
                break;

            case English:
                napiwek.setText("Tips");
                alcolator.setText("Start!");
                kociolek.setText("More");
                zapisane.setText("Adjust");
                donateTxt.setText("Thanks for support!");
                break;
        }
    }

    public void MoreClick(View view) {
        if(WasWarned())
        {
            Intent intent = new Intent(this, MoreOptions.class);
            startActivity(intent);
        }
    }

    public void AdjustButton(View view) {
        Intent intent = new Intent(this, AdjustTab.class);
        startActivity(intent);
    }

    public void DonateClick(View view) {
        DonateTab.mainClass = this;
        Intent intent = new Intent(this, DonateTab.class);
        startActivity(intent);
    }

    private boolean WasWarned()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        if(sharedPreferences.getBoolean(WASWARNED, false) == false)
        {
            Intent intent = new Intent(this, WarningTab.class);
            startActivity(intent);
            return false;
        }
        else
        {
            return true;
        }

    }
}