package com.example.partycounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class SoberAtAdditionalInfo extends AppCompatActivity {

    private String ToastMessageNoInput;
    private String ToastMessageWrongHourFormat;
    private String ToastMessageWrongPermileFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sober_at_additional_info);

        ChangeLanguage();
    }


    private void ChangeLanguage()
    {
        TextView hoursText = findViewById(R.id.text14);
        TextView fromText = findViewById(R.id.text16);
        TextView toText = findViewById(R.id.text15);
        Button sumBtn = findViewById(R.id.save);
        TextView ileProm = findViewById(R.id.text17);
        TextView oKtorej = findViewById(R.id.text18);

        if(MainActivity.GetLanguage() == Languages.Polish)
        {
            hoursText.setText("Jak długo chesz pić?");
            sumBtn.setText("Podsumuj!");
            fromText.setText("Start");
            toText.setText("Koniec");
            ileProm.setText("Ile promili?");
            oKtorej.setText("O której?");
            ToastMessageNoInput = "Podaj wszystkie informacje!";
            ToastMessageWrongHourFormat = "Źle podana godzina!";
            ToastMessageWrongPermileFormat = "Zakres Promili to 0 - 10!";
        }
        else
        {
            hoursText.setText("How long will You drink?");
            sumBtn.setText("Summarize!");
            fromText.setText("Start");
            toText.setText("End");
            ileProm.setText("Wanted permile");
            oKtorej.setText("On hour");
            ToastMessageNoInput = "Provide all required information!";
            ToastMessageWrongHourFormat = "Wrong time format!";
            ToastMessageWrongPermileFormat = "Permile range is 0 - 10!";

        }
    }

    public void SummariseClick(View view)
    {
        RadioButton noOption = findViewById(R.id.WillBeFull);
        RadioButton midOption = findViewById(R.id.nie);
        RadioButton yesOption = findViewById(R.id.tak);
        EditText hourStart = findViewById(R.id.weight8);
        EditText minStart = findViewById(R.id.weight7);
        EditText hourEnd = findViewById(R.id.weight12);
        EditText minEnd = findViewById(R.id.weight11);

        EditText wantedPermil = findViewById(R.id.weight13);
        EditText wantedHour = findViewById(R.id.weight15);
        EditText wantedMin = findViewById(R.id.weight16);

        if(hourStart.getText().toString().equals("") || minStart.getText().toString().equals("") || hourEnd.getText().toString().equals("") || minEnd.getText().toString().equals("") || wantedPermil.getText().toString().equals("") || wantedMin.getText().toString().equals("") || wantedHour.getText().toString().equals(""))
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
            int hourWantedInt = Integer.parseInt(wantedHour.getText().toString());
            int minWantedInt= Integer.parseInt(wantedMin.getText().toString());
            float permileWantedInt = Float.parseFloat(wantedPermil.getText().toString().replace(",","."));

            if(hourStartInt >= 0 && hourStartInt <= 23 && hourEndInt >= 0 && hourEndInt <=23 && minStartInt >=0 && minStartInt <=59 && minEndInt >=0 && minEndInt <= 59 && hourWantedInt >= 0 && hourWantedInt <= 23 && minWantedInt <=59 && minWantedInt >=0)
            {
                if(permileWantedInt >= 0 && permileWantedInt <= 10) {
                    AlcolatorSummary.HourStart = hourStartInt;
                    AlcolatorSummary.HourEnd = hourEndInt;
                    AlcolatorSummary.MinStart = minStartInt;
                    AlcolatorSummary.MinEnd = minEndInt;

                    AlcolatorSummary.hourWanted = hourWantedInt;
                    AlcolatorSummary.minWanted = minWantedInt;
                    AlcolatorSummary.permileWanted = permileWantedInt;
                    AlcolatorSummary.isOnSoberAtMode = true;

                    Intent intent = new Intent(this, AlcolatorSummary.class);
                    startActivity(intent);
                    return;
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), ToastMessageWrongPermileFormat, Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
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
