package com.example.partycounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AlculatorChoice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alculator_choice);
        ChangeLanguage();
    }


    private void ChangeLanguage()
    {
        Button willDrink = findViewById(R.id.WillDrink);
        Button nowDrinking = findViewById(R.id.NowDrinking);
        TextView text = findViewById(R.id.Text);

        switch(MainActivity.GetLanguage())
        {
            case Polish:
                willDrink.setText("Mam zamiar pić");
                nowDrinking.setText("Właśnie piję");
                text.setText("Wybierz tryb Alkolatora");
                break;

            case English:
                willDrink.setText("I'm gonna drink");
                nowDrinking.setText("I'm drinking now");
                text.setText("Choose Alcolator mode");
                break;
        }
    }

    public void GonnaDrinkClick(View view) {
        Intent intent = new Intent(this, ChoosePerson.class);
        startActivity(intent);
    }
}