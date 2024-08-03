package com.example.partycounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MoreOptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_options);

        ChangeLanguage();
    }

    private void ChangeLanguage()
    {
        TextView maintxt = findViewById(R.id.Text1);
        Button btn1 = findViewById(R.id.soberAtBtn);
        Button btn2 = findViewById(R.id.punchBtn);


        if(MainActivity.GetLanguage() == Languages.English)
        {
            maintxt.setText("Choose mode");
            btn1.setText("SoberAtLator");
            btn2.setText("PunchLator");
        }
        else
        {
            maintxt.setText("Wybierz tryb");
            btn1.setText("Ile mogę wypić");
            btn2.setText("Kociołek");
        }
    }

    public void PunchClick(View view) {

        Intent intent = new Intent(this, PunchLator.class);
        startActivity(intent);
    }

    public void SoberAtClick(View view) {

        Intent intent = new Intent(this, ChoosePerson.class);
        ChoosePerson.LeadsToAlculator = false;
        startActivity(intent);
    }
}