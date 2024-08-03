package com.example.partycounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class AdjustTab extends AppCompatActivity {


    public static final String TOLERANCEMEMORY = "TOL";
    public static final String  BURNMEMORY = "BUR";
    public static final String  SPEEDMEMORY = "SPD";

    public static float TOLERANCE;
    public static float BURNSPEED;
    public static float DRUNKSPEED;


    TextView mainText;
    TextView toleranceText;
    TextView burnText;
    TextView speedText;
    Button saveBtn;
    SeekBar toleranceBar;
    SeekBar burnBar;
    SeekBar speedBar;


    TextView toleranceDipslay;
    TextView burnDipslay;
    TextView speedDipslay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_tab);

        mainText = findViewById(R.id.text3);
        toleranceText = findViewById(R.id.tolerance);
        burnText = findViewById(R.id.speedTxt);
        speedText = findViewById(R.id.speedTxt2);
        saveBtn = findViewById(R.id.savebtn);

        toleranceBar = findViewById(R.id.seekBar2);
        burnBar = findViewById(R.id.seekBar3);
        speedBar = findViewById(R.id.seekBar4);

        toleranceDipslay = findViewById(R.id.toleranceInt);
        burnDipslay = findViewById(R.id.burnSpeedInt);
        speedDipslay = findViewById(R.id.drunkSpeedInt);

        toleranceBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                toleranceDipslay.setText(String.valueOf(50 + progress * 10) + "%");
                TOLERANCE = (50 + (float)progress*10)/100;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        burnBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                burnDipslay.setText(String.valueOf(50 + progress * 10) + "%");
                BURNSPEED = (50 + (float)progress*10)/100;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        speedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                speedDipslay.setText(String.valueOf(50 + progress * 10) + "%");

                DRUNKSPEED = (50 + (float)progress*10)/100;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        ChangeLanguage();
        GetValues();
    }

    private void GetValues()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);

        if(sharedPreferences.getFloat(TOLERANCEMEMORY,99) == 99 || sharedPreferences.getFloat(SPEEDMEMORY,99) == 99|| sharedPreferences.getFloat(BURNMEMORY,99) == 99)
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat(TOLERANCEMEMORY, 1);
            editor.putFloat(BURNMEMORY, 1);
            editor.putFloat(SPEEDMEMORY, 1);
            editor.apply();
        }

        TOLERANCE = sharedPreferences.getFloat(TOLERANCEMEMORY,0);
        BURNSPEED = sharedPreferences.getFloat(BURNMEMORY,0);
        DRUNKSPEED = sharedPreferences.getFloat(SPEEDMEMORY,0);

        toleranceBar.setProgress((int)((TOLERANCE * 10f)-5f));
        burnBar.setProgress((int)((BURNSPEED * 10f)-5f));
        speedBar.setProgress((int)((DRUNKSPEED * 10f)-5f));
    }
    private void SetValues()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(TOLERANCEMEMORY, TOLERANCE);
        editor.putFloat(BURNMEMORY, BURNSPEED);
        editor.putFloat(SPEEDMEMORY, DRUNKSPEED);
        editor.apply();
    }

    private void ChangeLanguage()
    {
        switch(MainActivity.GetLanguage())
        {
            case Polish:
                mainText.setText("Ustawienia");
                toleranceText.setText("Twoja tolerancja akoholowa:");
                burnText.setText("Jak szybko spalasz alkohol:");
                speedText.setText("Jak szybko czujesz alkohol:");
                saveBtn.setText("zapisz");
                break;

            case English:
                mainText.setText("Settings");
                toleranceText.setText("Your alcohol tolerance:");
                burnText.setText("How fast do You burn alcohol:");
                speedText.setText("How fast do You feel alcohol:");
                saveBtn.setText("save");
        }
    }

    public void SaveBtn(View view) {
        SetValues();
         Intent intent = new Intent(this, MainActivity.class);
         startActivity(intent);
    }
}