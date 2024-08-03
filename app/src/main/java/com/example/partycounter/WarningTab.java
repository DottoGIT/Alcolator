package com.example.partycounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class WarningTab extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning_tab);

        ChangeLanguage();
    }

    private void ChangeLanguage()
    {
        TextView warning = findViewById(R.id.textView1);
        TextView main = findViewById(R.id.textView2);
        TextView freepik = findViewById(R.id.textView3);
        Button btn = findViewById(R.id.alcolatorBtn4);

        if(MainActivity.GetLanguage() == Languages.English)
        {
            warning.setText("Before you start!");
            main.setText("None per mil calculator is able to give exact results, they are just speculations based on certain standards. Your intoxication depends on enormous amount of small factors, which no one can predict. Remember that displayed results can be incorrect. If You see that program is notoriously gives bigger or lower per mil use 'Adjust' tab!");
            freepik.setText("Icons made by 'Freepik' from flaticon.com");
            btn.setText("Got it!");
        }
        else
        {
            warning.setText("Zanim rozpoczniesz!");
            main.setText("Żaden dostępny kalulator promili nie jest w stanie podać dokładnych wyników, a jedynie spekulacje określane na podstawie pewnych norm. Twoje upojenie jest zależne od ogromnej liczby czynników, których nikt nie jest w stanie przewidzieć. Pamiętaj więc, że wyświetlane wyniki mogą mijać się z prawdą. Jeżeli zauważysz że program notorycznie je zawyża lub zaniża, skorzystaj z zakładki 'Dostosuj'!");
            freepik.setText("Ikony stworzył 'Freepik' z ze strony flaticon.com");
            btn.setText("Rozumiem!");
        }
    }

    public void GotItClick(View view) {

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(MainActivity.WASWARNED,true).apply();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}