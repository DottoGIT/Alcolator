package com.example.partycounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class AdditionalInfo extends AppCompatActivity {

    private String ToastMessageNoInput;
    private String ToastMessageWrongHourFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_info);

        ChangeLanguage();


    }

    private void ChangeLanguage()
    {
        TextView hoursText = findViewById(R.id.Text1);
        TextView fromText = findViewById(R.id.text10);
        TextView toText = findViewById(R.id.text11);
        EditText HourInput = findViewById(R.id.weight5);
        EditText MinInput = findViewById(R.id.weight6);
        TextView willBeHungry = findViewById(R.id.text7);
        TextView firstComment = findViewById(R.id.text6);
        Button sumBtn = findViewById(R.id.Save);
        RadioButton noOption = findViewById(R.id.WillBeFull);
        RadioButton midOption = findViewById(R.id.nie);
        RadioButton yesOption = findViewById(R.id.tak);

        if(MainActivity.GetLanguage() == Languages.Polish)
        {
            hoursText.setText("Jak długo będziesz pił?");
            willBeHungry.setText("Czy będziesz na czczo?");
            firstComment.setText("*Podaj okres czasu od rozpoczęcia picia do ostatniego łyku, program uznaje, że będziesz spożywał alkohol równomiernie przez  dany czas.");
            sumBtn.setText("Podsumuj!");
            noOption.setText("Będę pełny");
            midOption.setText("Nie");
            yesOption.setText("Tak");
            fromText.setText("Start");
            toText.setText("Koniec");
            ToastMessageNoInput = "Podaj wszystkie informacje!";
            ToastMessageWrongHourFormat = "Źle podana godzina!";
        }
        else
        {
            hoursText.setText("Provide drinking hours");
            willBeHungry.setText("Will You be on empty stomach?");
            firstComment.setText("*The program assumes that You will drink alcohol evenly throughout provided time, so input only this period You will drink in!");
            sumBtn.setText("Summarize!");
            noOption.setText("will be full");
            midOption.setText("No");
            yesOption.setText("Yes");
            fromText.setText("Start");
            toText.setText("End");
            ToastMessageNoInput = "Provide all required information!";
            ToastMessageWrongHourFormat = "Wrong time format!";

        }
    }

    public void SummariseClick(View view)
    {
        RadioButton noOption = findViewById(R.id.WillBeFull);
        RadioButton midOption = findViewById(R.id.nie);
        RadioButton yesOption = findViewById(R.id.tak);
        EditText hourStart = findViewById(R.id.weight5);
        EditText minStart = findViewById(R.id.weight6);
        EditText hourEnd = findViewById(R.id.weight9);
        EditText minEnd = findViewById(R.id.weight10);

        if(hourStart.getText().toString().equals("") || minStart.getText().toString().equals("") || hourEnd.getText().toString().equals("") || minEnd.getText().toString().equals("") || (!noOption.isChecked() && !midOption.isChecked() && !yesOption.isChecked()))
        {
            Toast toast = Toast.makeText(getApplicationContext(), ToastMessageNoInput, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        try
        {
            int hourStartInt = Integer.parseInt(hourStart.getText().toString());
            int minStartInt = Integer.parseInt(minStart.getText().toString());
            int hourEndInt = Integer.parseInt(hourEnd.getText().toString());
            int minEndInt = Integer.parseInt(minEnd.getText().toString());

            if(hourStartInt >= 0 && hourStartInt <= 23 && hourEndInt >= 0 && hourEndInt <=23 && minStartInt >=0 && minStartInt <=59 && minEndInt >=0 && minEndInt <= 59)
            {
                AlcolatorSummary.HourStart = hourStartInt;
                AlcolatorSummary.HourEnd = hourEndInt;
                AlcolatorSummary.MinStart = minStartInt;
                AlcolatorSummary.MinEnd = minEndInt;
                AlcolatorSummary.isOnSoberAtMode = false;

                Intent intent = new Intent(this, AlcolatorSummary.class);
                startActivity(intent);
                return;
            }
            else
            {
                Toast toast = Toast.makeText(getApplicationContext(), ToastMessageWrongHourFormat, Toast.LENGTH_SHORT);
                toast.show();
                return;
            }


        }catch (NumberFormatException nfe)
        {
            Toast toast = Toast.makeText(getApplicationContext(), ToastMessageWrongHourFormat, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

    }
}