package com.example.partycounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AlcolatorSummary extends AppCompatActivity {

    private float alcoholGoneInHour = 10 * AdjustTab.BURNSPEED;

    public static Person user;
    public static float AlcoholMl;
    public static float AlcoholCost;
    public static int HungryLvl;
    public static int HourStart;
    public static int HourEnd;
    public static int MinStart;
    public static int MinEnd;

    public static int hourWanted;
    public static int minWanted;
    public static float permileWanted;

    public static boolean isOnSoberAtMode;

    private DecimalFormat df;
    private DecimalFormat cl;
    private DecimalFormat qn;
    private int minBMI;
    private int maxBMI;
    private float usersBMI;
    private float usersBodyFluids;
    private float pureAlcoholOverParty;
    private int drinkTimeInMIN;
    private float doseGoTime;
    private int howManyDoses;
    private float doseCapacity;
    private int timeToSoberUp;
    private float maxPermile=0;
    private int maxPermileHour=0;
    private int soberHour=0;
    private ArrayList<Symptom> symptoms = new ArrayList<Symptom>();
    private ArrayList<Symptom> tips = new ArrayList<Symptom>();
    private String HangoverState;



    private static final String TAG = "AlcoholSummary";
    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alcolator_summary);

        mChart = (LineChart) findViewById(R.id.chart);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);


        df = new DecimalFormat("##.##");
        df.setRoundingMode(RoundingMode.DOWN);
        cl = new DecimalFormat("##");
        cl.setRoundingMode(RoundingMode.DOWN);
        SetBMILevels();
        qn = new DecimalFormat("##.#");
        qn.setRoundingMode(RoundingMode.DOWN);
        SetBMILevels();

        if(HungryLvl == 0) doseGoTime = 90;
        else if(HungryLvl == 1) doseGoTime = 45;
        else doseGoTime = 15;

        doseGoTime *= (2-AdjustTab.DRUNKSPEED);

        drinkTimeInMIN = CalculateDrinkTime(HourEnd,HourStart,MinEnd,MinStart);
        usersBMI = CalculateBMI();
        usersBodyFluids = CalculateBodyFluids();

        if(isOnSoberAtMode)
        {
            SetUpForSoberAt();
        }

        pureAlcoholOverParty = Float.parseFloat(df.format(AlcoholMl * 0.4f).replace(",","."));

        timeToSoberUp = CalculateSoberingTime();



        howManyDoses = (int) Math.ceil(drinkTimeInMIN/15f) + 1;
        doseCapacity = pureAlcoholOverParty/howManyDoses;
        float doses[] = new float[howManyDoses];


        SetChart();
        GetSymptoms();
        GetHangoverInfo();


        TextView showMaxPermile = findViewById(R.id.maxPromile);
        TextView drunkAt = findViewById(R.id.drunkAt);
        TextView soberAt = findViewById(R.id.soberAt);
        TextView likeBeer = findViewById(R.id.likeBeer);
        TextView likeWine = findViewById(R.id.likeWine);
        TextView likeVodka = findViewById(R.id.likeVodka);
        TextView likeBeerCal = findViewById(R.id.likeBeerCalories);
        TextView likeWineCal = findViewById(R.id.likeWineCalories);
        TextView likeVodkaCal = findViewById(R.id.likeVodkaCalories);
        TextView likeYouDrunk = findViewById(R.id.likeYouDrunk);
        TextView mainPermile = findViewById(R.id.maxPromileTxt);
        TextView drunkAtTxt = findViewById(R.id.drunkAtTxt);
        TextView soberAtTxt = findViewById(R.id.soberAttxt);
        TextView wholeAmount = findViewById(R.id.wholeAmount);
        TextView wholeAmountTxt = findViewById(R.id.text69);

        showMaxPermile.setText(String.valueOf(maxPermile));

        if(maxPermile == 0) {
            drunkAt.setText("--:--");
            soberAt.setText("--:--");
        }
        else{
            drunkAt.setText(MinToHour(maxPermileHour));
            soberAt.setText(MinToHour(soberHour));
        }

        if(MainActivity.GetLanguage() == Languages.English){
            likeYouDrunk.setText("It's like You've drunk");
            wholeAmountTxt.setText("of vodka:");
            mainPermile.setText("Highest per mil: ");
            drunkAtTxt.setText("Most drunk at: ");
            soberAtTxt.setText("Should be sober at: ");
        }
        else{
            likeYouDrunk.setText("To tak jakbyś wypił");
            wholeAmountTxt.setText("wódki, czyli:");
            mainPermile.setText("Najwyższy promil: ");
            drunkAtTxt.setText("Najbardziej pijany o: ");
            soberAtTxt.setText("Wytrzeźwiejesz koło: ");

        }

        wholeAmount.setText(cl.format(AlcoholMl) + "ml");
        likeBeer.setText(qn.format(CalculateLikeAlco(500,0.05f)) + " x");
        likeWine.setText(qn.format(CalculateLikeAlco(150,0.13f)) + " x");
        likeVodka.setText(qn.format(CalculateLikeAlco(30,0.4f)) + " x");

        likeBeerCal.setText("("+cl.format(43 * CalculateLikeAlco(100,0.05f)) + " kcal)");
        likeWineCal.setText("("+cl.format(82 * CalculateLikeAlco(100,0.13f)) + " kcal)");
        likeVodkaCal.setText("("+cl.format(231 * CalculateLikeAlco(100,0.4f)) + " kcal)");



    }

    private void SetUpForSoberAt()
    {
         AlcoholMl = (((CalculateDrinkTime(hourWanted,HourStart,minWanted,MinStart) / 15)-1) * (alcoholGoneInHour/4)/0.4f) + ((permileWanted * usersBodyFluids)/0.4f);
    }

    private void GetHangoverInfo()
    {
        tips.clear();
        Symptom[] allStates = new Symptom[6];
        Symptom[] allTips = new Symptom[10];

        if(MainActivity.GetLanguage() == Languages.Polish) {
            allStates[0] = new Symptom(0, 0.39f, "Zerowe szanse na kaca");
            allStates[1] = new Symptom(0.4f, 0.69f, "Raczej bez kaca");
            allStates[2] = new Symptom(0.7f, 0.99f, "Możliwy kac");
            allStates[3] = new Symptom(1f, 1.45f, "Duże prawdopodobieństwo kaca");
            allStates[4] = new Symptom(1.5f, 1.99f, "Pewny kac");
            allStates[5] = new Symptom(2f, 99f, "Kac morderca");

            allTips[0] = new Symptom(0.4f, 3f, "Picie wody między drinkami pomoże");
            allTips[1] = new Symptom(0.4f, 3f, "Jedz podczas picia");
            allTips[2] = new Symptom(1.5f, 99f, "Brak fazy REM snu");
            allTips[3] = new Symptom(1.5f, 99f, "Uczenie się będzie wyzwaniem");
            allTips[4] = new Symptom(1.5f, 2f, "Lekki ból głowy");
            allTips[5] = new Symptom(2.01f, 99f, "Poważne bóle głowy");
            allTips[6] = new Symptom(2.01f, 99f, "Nudności");
            allTips[7] = new Symptom(2.01f, 3f, "Zerowa produktywność do późnego popołudnia");
            allTips[8] = new Symptom(3.01f, 99f, "Zerowa produktywność do następnego dnia");
            allTips[9] = new Symptom(0f, 0.39f, "Czeka Cię produktywny dzień!");
        }
        else{
            allStates[0] = new Symptom(0, 0.39f, "Zero chance of hangover");
            allStates[1] = new Symptom(0.4f, 0.69f, "Rather no hangover");
            allStates[2] = new Symptom(0.7f, 0.99f, "Possible hangover");
            allStates[3] = new Symptom(1f, 1.45f, "Hangover likely");
            allStates[4] = new Symptom(1.5f, 1.99f, "Certain Hangover");
            allStates[5] = new Symptom(2f, 99f, "Hangover agony");

            allTips[0] = new Symptom(0.4f, 2f, "Hydrating between drinks will help");
            allTips[1] = new Symptom(1f, 3f, "Eat while drinking");
            allTips[2] = new Symptom(1.5f, 99f, "Decreased REM sleep");
            allTips[3] = new Symptom(1.5f, 99f, "Studying will be a struggle");
            allTips[4] = new Symptom(1.5f, 2f, "Headachy");
            allTips[5] = new Symptom(2.01f, 99f, "Headache");
            allTips[6] = new Symptom(2.01f, 99f, "Nausea");
            allTips[7] = new Symptom(2.01f, 3f, "Unlikely productivity until late-afternoon");
            allTips[8] = new Symptom(3.01f, 99f, "Unlikely productivity until next day");
            allTips[9] = new Symptom(0f, 0.39f, "Enjoy your next day!");
        }

        TextView hangover = findViewById(R.id.Hangover);
        TextView TipsList = findViewById(R.id.TipsList);
        String TipsString = "";

        for (Symptom state: allStates) {
            if(state.MinIntox <= maxPermile && state.MaxIntox >= maxPermile)
            {
                hangover.setText(state.Name);
                break;
            }
        }

        for (Symptom tip: allTips) {
            if(tip.MinIntox <= maxPermile && tip.MaxIntox>= maxPermile)
            {
                tips.add(tip);
                TipsString +=tip.Name + ", ";
            }
        }

        TipsList.setText(TipsString.substring(0,TipsString.length()-2));
    }


    private void GetSymptoms()
    {
        symptoms.clear();
        Symptom[] allSymptoms = new Symptom[37];
        Symptom[] allStates = new Symptom[8];

        if(MainActivity.GetLanguage() == Languages.Polish)
        {
            allStates[7] = new Symptom(0, 0.01f,"Trzeźwość");
            allStates[0] = new Symptom(0.02f, 0.3f,"Faza 1: W miarę trzeźwy");
            allStates[1] = new Symptom(0.31f, 1.29f,"Faza 2: Euforia");
            allStates[2] = new Symptom(1.3f, 2f,"Faza 3: Pobudzenie");
            allStates[3] = new Symptom(2.01f, 2.7f,"Faca 4: Dezorientacja");
            allStates[4] = new Symptom(2.71f, 4f,"Faza 5: Otępienie");
            allStates[5] = new Symptom(4, 4.5f,"Faza 6: Letarg");
            allStates[6] = new Symptom(4.51f, 99f,"Faza 7: Śmiertelna");

            allSymptoms[36] = new Symptom(0, 0.01f,"Brak zmian w zachowaniu");
            allSymptoms[0] = new Symptom(0.02f, 0.3f,"Brak większych zmian w zachowaniu");
            allSymptoms[1] = new Symptom(0.2f, 0.59f,"Relaksacja");
            allSymptoms[2] = new Symptom(0.3f, 0.59f,"Radość");
            allSymptoms[3] = new Symptom(0.3f, 0.59f,"Gadatliwość");
            allSymptoms[4] = new Symptom(0.3f, 0.59f,"Zmniejszenie zahamowań społecznych");

            allSymptoms[5] = new Symptom(0.6f, 1.5f,"Odczucie gorąca");
            allSymptoms[6] = new Symptom(0.6f, 2f,"Zawroty głowy");
            allSymptoms[7] = new Symptom(0.6f, 5f,"Brak zahamowań");
            allSymptoms[8] = new Symptom(0.6f, 1.2f,"Euforia");
            allSymptoms[9] = new Symptom(0.6f, 3f,"Ekstrawersja");
            allSymptoms[10] = new Symptom(0.6f, 0.99f,"Zwiększenie tolerancji na ból");

            allSymptoms[11] = new Symptom(1f, 5f,"Znieczulenie");
            allSymptoms[12] = new Symptom(1f, 1.99f,"Gwałtowność");
            allSymptoms[13] = new Symptom(1f, 2.5f,"Nadmiernie wyrażane uczucia");
            allSymptoms[14] = new Symptom(1f, 1.99f,"Możliwe nudności");
            allSymptoms[15] = new Symptom(1f, 5f,"Zaburzenia ruchu");
            allSymptoms[16] = new Symptom(3f, 5f,"Problemy ze wzrokiem");
            allSymptoms[17] = new Symptom(1f, 2.5f,"Senność");

            allSymptoms[18] = new Symptom(2f, 2.99f,"Złość");
            allSymptoms[19] = new Symptom(2f, 2.99f,"Smutek");
            allSymptoms[20] = new Symptom(2f, 2.99f,"Amnezja");
            allSymptoms[21] = new Symptom(2f, 5f,"Upośledzenie odczuć");
            allSymptoms[22] = new Symptom(2f, 2.99f,"Popęd seksualny");
            allSymptoms[23] = new Symptom(2f, 2.99f,"Wahania nastroju");
            allSymptoms[24] = new Symptom(2f, 2.99f,"Nudności");
            allSymptoms[25] = new Symptom(2f, 5f,"Gorsze rozumienie");
            allSymptoms[26] = new Symptom(2f, 5f,"Otępienie");
            allSymptoms[27] = new Symptom(2f, 5f,"Wymioty");

            allSymptoms[28] = new Symptom(3f, 5f,"Depresja");
            allSymptoms[29] = new Symptom(3f, 5f,"Utraty świadomości");
            allSymptoms[30] = new Symptom(3f, 3.99f,"Małe ryzyko śmierci");

            allSymptoms[31] = new Symptom(4f, 5f,"Śpiączka");
            allSymptoms[32] = new Symptom(4f, 5f,"Ryzyko śmierci");
            allSymptoms[33] = new Symptom(4f, 99f,"Zatrucie alkoholowe powoduje 88 000 śmierci rocznie, pij odpowiedzialnie!");

            allSymptoms[34] = new Symptom(5.1f, 6.99f,"Duże prawdopodobieństwo śmierci");

            allSymptoms[35] = new Symptom(7f, 99f,"Śmierć");
        }
        else{
            allStates[7] = new Symptom(0, 0.01f,"Sober");
            allStates[0] = new Symptom(0.02f, 0.3f,"Phase 1: Soberity");
            allStates[1] = new Symptom(0.31f, 1.29f,"Phase 2: Euphoria");
            allStates[2] = new Symptom(1.3f, 2f,"Phase 3: Excitement");
            allStates[3] = new Symptom(2.01f, 2.7f,"Phase 4: Confusion");
            allStates[4] = new Symptom(2.71f, 4f,"Phase 5: Stupor");
            allStates[5] = new Symptom(4, 4.5f,"Phase 6: Coma");
            allStates[6] = new Symptom(4.51f, 99f,"Phase 7: Death dose");

            allSymptoms[36] = new Symptom(0, 0.01f,"No changes in behavior");
            allSymptoms[0] = new Symptom(0.02f, 0.3f,"No big changes in behavior");
            allSymptoms[1] = new Symptom(0.2f, 0.59f,"Relaxation");
            allSymptoms[2] = new Symptom(0.3f, 0.59f,"Joyousness");
            allSymptoms[3] = new Symptom(0.3f, 0.59f,"Increased verbosity");
            allSymptoms[4] = new Symptom(0.3f, 0.59f,"Decresed social inhibition");

            allSymptoms[5] = new Symptom(0.6f, 1.5f,"Overheating sensation");
            allSymptoms[6] = new Symptom(0.6f, 2f,"Dizziness");
            allSymptoms[7] = new Symptom(0.6f, 5f,"Disinhibition");
            allSymptoms[8] = new Symptom(0.6f, 1.2f,"Euphoria");
            allSymptoms[9] = new Symptom(0.6f, 3f,"Extraversion");
            allSymptoms[10] = new Symptom(0.6f, 0.99f,"Increased pain tolerance");

            allSymptoms[11] = new Symptom(1f, 5f,"Analgesia");
            allSymptoms[12] = new Symptom(1f, 1.99f,"Violence");
            allSymptoms[13] = new Symptom(1f, 2.5f,"Over-expressed emotions");
            allSymptoms[14] = new Symptom(1f, 1.99f,"Possibility of nausea");
            allSymptoms[15] = new Symptom(1f, 5f,"Movement disorder");
            allSymptoms[16] = new Symptom(3f, 5f,"Vision problems");
            allSymptoms[17] = new Symptom(1f, 2.5f,"Drowsiness");

            allSymptoms[18] = new Symptom(2f, 2.99f,"Anger");
            allSymptoms[19] = new Symptom(2f, 2.99f,"Sadness");
            allSymptoms[20] = new Symptom(2f, 2.99f,"Amnesia");
            allSymptoms[21] = new Symptom(2f, 5f,"Imparied sensations");
            allSymptoms[22] = new Symptom(2f, 2.99f,"Sexual desire");
            allSymptoms[23] = new Symptom(2f, 2.99f,"Mood swings");
            allSymptoms[24] = new Symptom(2f, 2.99f,"Nausea");
            allSymptoms[25] = new Symptom(2f, 5f,"Loss of understanding");
            allSymptoms[26] = new Symptom(2f, 5f,"Stupor");
            allSymptoms[27] = new Symptom(2f, 5f,"Vomiting");

            allSymptoms[28] = new Symptom(3f, 5f,"Depression");
            allSymptoms[29] = new Symptom(3f, 5f,"Loss of consciousness");
            allSymptoms[30] = new Symptom(3f, 3.99f,"Low possibility of death");

            allSymptoms[31] = new Symptom(4f, 5f,"Coma");
            allSymptoms[32] = new Symptom(4f, 5f,"Possibility of death");
            allSymptoms[33] = new Symptom(4f, 99f,"Alcohol intoxication causes 88 000 deaths annually, drink responsibly!");

            allSymptoms[34] = new Symptom(5.01f, 6.99f,"High possibility of death");

            allSymptoms[35] = new Symptom(7f, 99f,"Death");
        }

        TextView effects = findViewById(R.id.effects);
        TextView effectsList = findViewById(R.id.effectsList);
        String allEffectsString = "";

        for (Symptom state:allStates) {

            if(state.MinIntox <= maxPermile && state.MaxIntox>= maxPermile)
            {
                effects.setText(state.Name);
            }
        }

        for (Symptom symp:allSymptoms) {

            if(symp.MinIntox <= maxPermile && symp.MaxIntox>= maxPermile)
            {
                symptoms.add(symp);
                allEffectsString += symp.Name + ", ";
            }
        }

        effectsList.setText(allEffectsString.substring(0,allEffectsString.length()-2));

    }

    private float CalculateLikeAlco(float ml, float alc)
    {
        return Float.parseFloat(df.format((pureAlcoholOverParty / alc) / ml).replace(",","."));
    }

    private int CalculateDrinkTime(int _hE, int _hS, int _mE, int _mS)
    {
        int finalHours;
        int finalMinutes;

        if(_hS == _hE && _mS == _mE)
        {
            return 1440;
        }

        if(_hS <= _hE)
        {
            finalHours = _hE - _hS;
        }
        else
        {
            finalHours = 24 - _hS + _hE;
        }

        finalMinutes = _mE - _mS;
        return (finalHours * 60) + finalMinutes;
    }

    private void SetChart()
    {
        ArrayList<Entry> Values = new ArrayList<>();

            int currentMinute = 0;
            float permile = 0;
            int minuteIncrement = 15;

        do{
            permile = CalculateAlcoholPermile(currentMinute);
            if(permile > maxPermile)
            {
                maxPermile = permile;
                maxPermileHour = currentMinute;
            }
            Values.add(new Entry(currentMinute, permile));
            currentMinute+=minuteIncrement;
        }while(currentMinute <= howManyDoses*15 || permile != 0);

        soberHour = currentMinute;

        LineDataSet set = new LineDataSet(Values, "Promile");
        set.setFillAlpha(110);

        ArrayList<ILineDataSet> dataSet = new ArrayList<>();
        dataSet.add(set);

        LineData data = new LineData(dataSet);

        set.setLineWidth(3);
        set.setDrawCircles(false);
        set.setColor(Color.parseColor("#FF9800"));
        set.setValueTextSize(0);

        LimitLine lethalDose = new LimitLine(5f, "Lethal Dose");
        lethalDose.setLineWidth(3);
        lethalDose.enableDashedLine(10,10,0);
        lethalDose.setTextSize(15);
        lethalDose.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);

        YAxis leftAxis = mChart.getAxisLeft();
        YAxis rightAxis = mChart.getAxisRight();
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(lethalDose);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setTextSize(12);
        rightAxis.setTextColor(Color.WHITE);
        rightAxis.setTextSize(12);

        XAxis valAxis = mChart.getXAxis();
        valAxis.setValueFormatter(new MyAxisFormatter());
        valAxis.setTextColor(Color.WHITE);
        valAxis.setTextSize(12);
        valAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        mChart.getLegend().setEnabled(false);
        mChart.getDescription().setEnabled(false);
        mChart.setData(data);
    }


    private String MinToHour(int minPassed)
    {
        int finalHour = HourStart;
        int finalMin = MinStart;

        finalMin += minPassed % 60;
        finalHour += (minPassed - finalMin)/60;

        while(finalHour >= 24){
            finalHour -= 24;
        }

        if(finalMin > 9)
        return String.valueOf(finalHour) + ":" + String.valueOf(finalMin);
        else return String.valueOf(finalHour) + ":0" + String.valueOf(finalMin);
    }

    private int CalculateSoberingTime()
    {

        int currentMinute = 0;
        float permile = 0;
        int minuteIncrement = 15;


        do{

            permile = CalculateAlcoholPermile(currentMinute);
            currentMinute+=minuteIncrement;

        }while(currentMinute <= howManyDoses*15 || permile != 0);

        return currentMinute;
    }

    private float CalculateAlcoholPermile(int minute)
    {

        if(minute % 15 != 0)
        {
            Toast.makeText(this, "Provided time is not devidable by 15!!!", Toast.LENGTH_SHORT).show();
            return 0;
        }

        float alcoholInBlood = 0;
        float alcoholGone = (minute/15) * (alcoholGoneInHour/4);


        for(int i = 0; i < howManyDoses; i++)
        {
            alcoholInBlood += CalculateDoseIntox(minute, i);
        }

        alcoholInBlood -= alcoholGone;

        alcoholInBlood/=usersBodyFluids;

        if(alcoholInBlood <= 0) {
            return 0;
        }

        return Float.parseFloat(df.format(alcoholInBlood).replace(",","."));
    }

    private float CalculateDoseIntox(int minute, int index)
    {
        int wasDrunkInMIN = index * 15;
        float isInStomachForMIN = minute-wasDrunkInMIN;

        if(isInStomachForMIN>=doseGoTime)
        {
            return doseCapacity;
        }
        else if(isInStomachForMIN <= 0)
        {
            return 0;
        }

        float devider = isInStomachForMIN/(float)doseGoTime;



        return doseCapacity*devider;
    }

    private float CalculateBodyFluids()
    {

        float fluidWeight = user.Weight * AdjustTab.TOLERANCE;

        if(user.Plec.equals("Female"))
        {
            fluidWeight *= 0.6f;
        }
        else if(user.Plec.equals("Male"))
        {
            fluidWeight *= 0.7f;
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Plec " + user.Plec + " nie istnieje", Toast.LENGTH_LONG).show();
        }

        float BMIDiff = 0;
        float KGDiff = 0;

        if(usersBMI > maxBMI) BMIDiff = usersBMI - maxBMI;
        else if(usersBMI < minBMI) BMIDiff = usersBMI - minBMI;
        else BMIDiff = 0;

        KGDiff = (Float.parseFloat(df.format(BMIDiff * Math.pow(user.Height, 2)/10000).replace(",","."))) * 0.15f;

        return Float.parseFloat(df.format(fluidWeight += KGDiff).replace(",","."));
    }

    private void SetBMILevels()
    {

        if(user.Plec.equals("Female"))
        {
            if(user.Age <= 24) { minBMI = 20; maxBMI = 25;}
            else if(user.Age <= 34) { minBMI = 21; maxBMI = 26;}
            else if(user.Age <= 44) { minBMI = 22; maxBMI = 27;}
            else if(user.Age <= 54) { minBMI = 23; maxBMI = 28;}
            else if(user.Age <= 64) { minBMI = 24; maxBMI = 29;}
            else { minBMI = 25; maxBMI = 30;}
        }
        else if(user.Plec.equals("Male"))
        {
            if(user.Age <= 24) { minBMI = 19; maxBMI = 24;}
            else if(user.Age <= 34) { minBMI = 20; maxBMI = 25;}
            else if(user.Age <= 44) { minBMI = 21; maxBMI = 26;}
            else if(user.Age <= 54) { minBMI = 22; maxBMI = 27;}
            else if(user.Age <= 64) { minBMI = 23; maxBMI = 28;}
            else { minBMI = 24; maxBMI = 29;}
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Plec " + user.Plec + " nie istnieje", Toast.LENGTH_LONG).show();
        }
    }

    private float CalculateBMI()
    {
        return Float.parseFloat(df.format(user.Weight / Math.pow(user.Height,2) * 10000).replace(",","."));
    }

    private class MyAxisFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            return MinToHour((int)value);
        }
    }
}

