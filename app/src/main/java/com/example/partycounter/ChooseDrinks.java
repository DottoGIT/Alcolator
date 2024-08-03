package com.example.partycounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChooseDrinks extends AppCompatActivity {

    private String willPayString;
    private String toastMessageFullFil;
    private String toastMessageInvalidInput;
    private String toastMessageNotInRangeInput;
    private String toastMessageNotInRangeInputDOWN;

    private EditText ml;
    private EditText vol;
    private EditText quan;
    private EditText cost;
    private RecyclerView recycler;
    TextView DisplayML;
    TextView DisplayCOST;

    private float SummaryML = 0;
    private float SummaryCOST = 0;

    private ArrayList<Order> orders = new ArrayList<Order>();

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
    {
        ArrayList<Order> list;
        Context context;

        public MyAdapter(Context _ct, ArrayList<Order> _list)
        {
            context = _ct;
            list = _list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.my_row, parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
           holder.ml.setText(String.valueOf(orders.get(position).ml) + "ml");
           holder.quanTxt.setText(String.valueOf(orders.get(position).quan));
           holder.vol.setText(String.valueOf(orders.get(position).vol) + "%");
           if(MainActivity.GetLanguage() == Languages.English)
           holder.cost.setText(String.valueOf(orders.get(position).cost));
           else holder.cost.setText(String.valueOf(orders.get(position).cost) + "zł");
        }

        @Override
        public int getItemCount() {
            return orders.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView ml;
            TextView vol;
            TextView quanTxt;
            TextView cost;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                ml = itemView.findViewById(R.id.mlTxt);
                vol = itemView.findViewById(R.id.volTxt);
                quanTxt= itemView.findViewById(R.id.plusMinus);
                cost= itemView.findViewById(R.id.zlTxt);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_drinks);

        ml = findViewById(R.id.weight4);
        vol = findViewById(R.id.weight3);
        quan = findViewById(R.id.weight2);
        cost = findViewById(R.id.weight5);
        DisplayML = findViewById(R.id.Text3);
        DisplayCOST = findViewById(R.id.Text5);
        recycler = findViewById(R.id.recycler);

        ChangeLanguage();
        RefreshAdapter();
    }

    private void RefreshAdapter()
    {
        ml.setText("");
        vol.setText("");
        quan.setText("");
        cost.setText("");

        DisplayCOST.setText(willPayString + " " + String.valueOf(SummaryCOST));

        MyAdapter myAdapter = new MyAdapter(this,orders);
        recycler.setAdapter(myAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void ChangeLanguage()
    {
        Button btnAdd = findViewById(R.id.add);
        Button btnDel = findViewById(R.id.delete);
        Button sumbit = findViewById(R.id.Save);
        TextView mainTxt = findViewById(R.id.Text1);
        TextView razem = findViewById(R.id.Text2);


        if(MainActivity.GetLanguage() == Languages.Polish)
        {
            btnAdd.setText("Dodaj");
            btnDel.setText("Usuń");
            sumbit.setText("Dalej!");
            willPayString = "Zapłacisz:";
            mainTxt.setText("Co będziesz pić?");
            quan.setHint("ilość*");
            cost.setHint("cena*");
            toastMessageFullFil = "Wypełnij niezbędne pola!";
            toastMessageInvalidInput = "Niepoprawny format danych!";
            toastMessageNotInRangeInput = "Suma wypitego czystego alkoholu nie może przekraczać 3,6 litrów, a koszt 5000zł!";
            toastMessageNotInRangeInputDOWN = "Suma wypitego alkoholu, ani cena nie może być mniejsza od 0!";
        }
        else
        {
            btnAdd.setText("Add");
            btnDel.setText("Remove");
            sumbit.setText("Move on!");
            willPayString = "Cost:";
            mainTxt.setText("What will You drink?");
            quan.setHint("quantity*");
            cost.setHint("price*");
            toastMessageFullFil = "Fill all required fields!";
            toastMessageInvalidInput = "Invalid data format!";
            toastMessageNotInRangeInput = "The sum of pure alcohol drunk cannot be bigger than 3,6 liters, and cost more than 5000!";
            toastMessageNotInRangeInputDOWN = "The sum of drunk alcohol, nur the price cannot be less than 0!";

        }
    }


    private void HideKeyboard()
    {
        View view = this.getCurrentFocus();
        if(view != null)
        {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    public void AddButton(View view)
    {

        if(ml.getText().toString().equals("") || vol.getText().toString().equals(""))
        {
            Toast.makeText(this,  toastMessageFullFil, Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {


            try {

                int sub_quan;
                float sub_cost;

                if (quan.getText().toString().equals("")) {
                    sub_quan = 1;
                } else {
                    sub_quan = Integer.parseInt(quan.getText().toString());
                }

                if (cost.getText().toString().equals("")) {
                    sub_cost = 0;
                } else {
                    sub_cost = Float.parseFloat(cost.getText().toString().replace(",", "."));
                }


                int sub_ml = Integer.parseInt(ml.getText().toString());
                float sub_vol = Float.parseFloat(vol.getText().toString().replace(",", "."));

                if (sub_vol < 0 || sub_ml < 0 || sub_cost < 0 || sub_vol > 100 || sub_quan < 0) {
                    Toast.makeText(this, toastMessageInvalidInput, Toast.LENGTH_SHORT).show();
                    return;
                }


                int givenAlcohol = sub_ml * sub_quan;
                float pureAlcoholInGiven = givenAlcohol * (sub_vol / 100) / 0.4f;
                float finalCost = sub_cost * sub_quan;


                if (pureAlcoholInGiven + SummaryML <= 3600 && sub_cost + SummaryCOST < 5000) {
                    SummaryML += pureAlcoholInGiven;
                    SummaryCOST += finalCost;

                        Order incomingOrder = new Order(sub_quan, sub_vol, sub_ml, sub_cost);

                        int index = 0;
                        boolean addToList = true;

                        for (Order ord : orders) {

                            if (ord.ml == incomingOrder.ml && ord.vol == incomingOrder.vol && ord.cost == incomingOrder.cost) {
                                incomingOrder.quan = ord.quan + incomingOrder.quan;
                                orders.get(index).quan = incomingOrder.quan;
                                addToList = false;
                                break;
                            }
                            index++;
                        }

                        if (addToList) {
                            orders.add(incomingOrder);
                        }

                        RefreshAdapter();
                        HideKeyboard();
                    } else {
                        Toast.makeText(this, toastMessageNotInRangeInput, Toast.LENGTH_SHORT).show();
                        return;
                    }
            }
            catch (NumberFormatException nfe)
            {
                Toast.makeText(this, toastMessageInvalidInput, Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    public void DeleteButton(View view)
    {


        if(ml.getText().toString().equals("") || vol.getText().toString().equals(""))
        {
            Toast.makeText(this,  toastMessageFullFil, Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {


            try
            {

                int sub_quan;
                float sub_cost;

                if(quan.getText().toString().equals(""))
                {
                    sub_quan = 1;
                }
                else
                {
                    sub_quan = Integer.parseInt(quan.getText().toString());
                }

                if(cost.getText().toString().equals(""))
                {
                    sub_cost = 0;
                }
                else
                {
                    sub_cost = Float.parseFloat(cost.getText().toString().replace(",","."));
                }


                int sub_ml = Integer.parseInt(ml.getText().toString());
                float sub_vol = Float.parseFloat(vol.getText().toString().replace(",","."));

                if(sub_vol < 0 || sub_ml < 0 || sub_cost < 0 || sub_vol > 100 || sub_quan < 0)
                {
                    Toast.makeText(this, toastMessageInvalidInput, Toast.LENGTH_SHORT).show();
                    return;
                }


                float givenAlcohol = sub_ml * sub_quan;
                float pureAlcoholInGiven = givenAlcohol * (sub_vol/100);
                float finalAmount = pureAlcoholInGiven/0.4f;
                float finalCost = sub_cost * sub_quan;

                if(SummaryML - finalAmount  >= 0 && SummaryCOST - sub_cost  >= 0)
                {
                    Order incomingOrder = new Order(sub_quan,sub_vol,sub_ml,sub_cost);

                    int index = 0;
                    boolean addToList = true;

                    for (Order ord:orders) {

                        if(ord.ml == incomingOrder.ml && ord.vol == incomingOrder.vol && ord.cost == incomingOrder.cost)
                        {
                            incomingOrder.quan = ord.quan - incomingOrder.quan;

                            addToList = false;

                            if(incomingOrder.quan == 0)
                            {
                                orders.remove(index);
                                break;
                            }

                            orders.get(index).quan = incomingOrder.quan;
                            break;
                        }
                        index++;
                    }

                    if(addToList)
                    {
                        incomingOrder.quan = -incomingOrder.quan;
                        orders.add(incomingOrder);
                    }
                    SummaryML -= finalAmount;
                    SummaryCOST -= finalCost;

                    RefreshAdapter();
                    HideKeyboard();
                }
                else
                {
                    Toast.makeText(this, toastMessageNotInRangeInputDOWN, Toast.LENGTH_SHORT).show();
                    return;
                }

            }
            catch (NumberFormatException nfe)
            {
                Toast.makeText(this, toastMessageInvalidInput, Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    public void ResetClick(View view)
    {
        SummaryCOST = 0;
        SummaryML = 0;

        orders.clear();
        RefreshAdapter();
    }

    public void AlcolateClick(View view)
    {
        if(SummaryML > 0)
        {
            AlcolatorSummary.AlcoholCost = SummaryCOST;
            AlcolatorSummary.AlcoholMl = SummaryML;

            Intent intent = new Intent(this, AdditionalInfo.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, toastMessageNotInRangeInputDOWN, Toast.LENGTH_SHORT).show();
            return;
        }
    }

}