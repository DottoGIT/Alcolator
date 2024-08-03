package com.example.partycounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

public class DonateTab extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    private String ToastError;
    private String ToastSucced;

    public static final String DIDPAYMEMORY = "ddpy";

    public static MainActivity mainClass;

    BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_tab);

        bp = new BillingProcessor(this,null,this);
        ChangeLanguage();
    }


    private void ChangeLanguage()
    {
        TextView Donation = findViewById(R.id.text21);
        TextView Main = findViewById(R.id.mainText);
        Button Btn = findViewById(R.id.alcolatorBtn2);

        switch(MainActivity.GetLanguage())
        {
            case Polish:
                Donation.setText("Dotacja");
                Main.setText("Aplikacja Alkolator jest w pełni darmowa i nie zawiera reklam. Przekaż swoje wsparcie wpłacając dobrowolną dotację!");
                Btn.setText("Wpłać 4 zł");
                ToastError = "Coś poszło nie tak, środki nie zostały pobrane";
                ToastSucced = "Otrzymaliśmy twoją dotację, dziękujemy za wsparcie <3";
                break;

            case English:
                Donation.setText("Donation");
                Main.setText("Alcolator is a free aplication without any advertisements. Show your support by paying volontary donation!");
                Btn.setText("Donate 1$");
                ToastError = "Something went wrong, nothing was charged";
                ToastSucced = "We've got Your donation, thanks for your support <3";
                break;
        }
    }


    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {

        Toast.makeText(this, "We've got Your donation, thanks for your support <3", Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(DIDPAYMEMORY, true);
        editor.apply();
        mainClass.CheckIfPaid();
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }

    public void DonateButton(View view) {
        bp.purchase(DonateTab.this,"android.test.purchased");
    }
}