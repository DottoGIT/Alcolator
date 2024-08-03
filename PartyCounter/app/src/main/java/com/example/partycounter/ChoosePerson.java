package com.example.partycounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class ChoosePerson extends AppCompatActivity {


    public static final String PERSON1Exist = "prs1e";
    public static final String PERSON1Name = "prs1n";
    public static final String PERSON1Weight = "prs1w";
    public static final String PERSON1Height = "prs1h";
    public static final String PERSON1Sex = "prs1s";
    public static final String PERSON1Tolerance = "prs1t";

    public static final String PERSON2Exist = "prs2ex";
    public static final String PERSON2Name = "prs2n";
    public static final String PERSON2Weight = "prs2w";
    public static final String PERSON2Height = "prs2h";
    public static final String PERSON2Sex = "prs2s";
    public static final String PERSON2Tolerance = "prs2t";

    public static final String PERSON3Exist = "prs3ex";
    public static final String PERSON3Name = "prs3n";
    public static final String PERSON3Weight = "prs3w";
    public static final String PERSON3Height = "prs3h";
    public static final String PERSON3Sex = "prs3s";
    public static final String PERSON3Tolerance = "prs3t";

    public static final String PERSON4Exist = "prs4ex";
    public static final String PERSON4Name = "prs4n";
    public static final String PERSON4Weight = "prs4w";
    public static final String PERSON4Height = "prs4h";
    public static final String PERSON4Sex = "prs4s";
    public static final String PERSON4Tolerance = "prs4t";

    public static final String PERSON5Exist = "prs5ex";
    public static final String PERSON5Name = "prs5n";
    public static final String PERSON5Weight = "prs5w";
    public static final String PERSON5Height = "prs5h";
    public static final String PERSON5Sex = "prs5s";
    public static final String PERSON5Tolerance = "prs5t";

   public static ArrayList<Person> usersList = new ArrayList<Person>();
   private Person[] usersArray= {null, null, null, null, null};
   private boolean deleteMode = false;
   private Button buttons[] = {null,null,null,null,null};
   private TextView text;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_person);

        deleteMode = false;
        GetUsers();

        buttons[0] = findViewById(R.id.Person1);
        buttons[1] = findViewById(R.id.Person2);
        buttons[2] = findViewById(R.id.Person3);
        buttons[3] = findViewById(R.id.Person4);
        buttons[4] = findViewById(R.id.Person5);
        text = findViewById(R.id.Text1);

       ChangeButtons();

        for(int i = 0; i < 5; i++)
        {
            if(i < usersList.size())
            {
                buttons[i].setVisibility(View.VISIBLE);
                buttons[i].setText(usersList.get(0).Name);
            }
            else
            {
                buttons[i].setVisibility(View.GONE);
            }
        }


    }


    private void GetUsers()
    {
        usersList.clear();

        Person Person1;
        Person Person2;
        Person Person3;
        Person Person4;
        Person Person5;

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
        if(sharedPreferences.getBoolean(PERSON1Exist, false) == true){
            Person1 = new Person(sharedPreferences.getString(PERSON1Name,"Error"), sharedPreferences.getInt(PERSON1Weight, 0), sharedPreferences.getInt(PERSON1Height, 0), sharedPreferences.getString(PERSON1Sex, ""), sharedPreferences.getInt(PERSON1Tolerance, 0));
        }else{
            Person1 = null;
        }
        if(sharedPreferences.getBoolean(PERSON2Exist, false) == true){
            Person2 = new Person(sharedPreferences.getString(PERSON2Name,"Error"), sharedPreferences.getInt(PERSON2Weight, 0), sharedPreferences.getInt(PERSON2Height, 0), sharedPreferences.getString(PERSON2Sex, ""), sharedPreferences.getInt(PERSON2Tolerance, 0));
        }else{
            Person2 = null;
        }
        if(sharedPreferences.getBoolean(PERSON3Exist, false) == true){
            Person3 = new Person(sharedPreferences.getString(PERSON3Name,"Error"), sharedPreferences.getInt(PERSON3Weight, 0), sharedPreferences.getInt(PERSON3Height, 0), sharedPreferences.getString(PERSON3Sex, ""), sharedPreferences.getInt(PERSON3Tolerance, 0));
        }else{
            Person3 = null;
        }
        if(sharedPreferences.getBoolean(PERSON4Exist, false) == true){
            Person4 = new Person(sharedPreferences.getString(PERSON4Name,"Error"), sharedPreferences.getInt(PERSON4Weight, 0), sharedPreferences.getInt(PERSON4Height, 0), sharedPreferences.getString(PERSON4Sex, ""), sharedPreferences.getInt(PERSON4Tolerance, 0));
        }else{
            Person4 = null;
        }
        if(sharedPreferences.getBoolean(PERSON5Exist, true) == true){
            Person5 = new Person(sharedPreferences.getString(PERSON5Name,"Error"), sharedPreferences.getInt(PERSON5Weight, 0), sharedPreferences.getInt(PERSON5Height, 0), sharedPreferences.getString(PERSON5Sex, ""), sharedPreferences.getInt(PERSON5Tolerance, 0));
        }else{
            Person5 = null;
        }

        usersArray[0] = Person1;
        usersArray[1] = Person2;
        usersArray[2] = Person3;
        usersArray[3] = Person4;
        usersArray[4] = Person5;

        for(int i =0; i<5; i++)
        {
            if(usersArray[i] != null)
            {
                usersList.add(usersArray[i]);
            }
        }
    }

    public void DeleteButton(View view) {

        if(deleteMode == false)
        {
            deleteMode = true;
        }
        else
        {
            deleteMode = false;
        }

        ChangeButtons();
    }

    private void ChangeButtons()
    {
        if(deleteMode == false)
        {
            for (Button btn:buttons) {
                btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                if(MainActivity.GetLanguage() == Languages.Polish)
                {
                    text.setText("Kto będzie pił?");
                }
                else if(MainActivity.GetLanguage() == Languages.English)
                {
                    text.setText("Who's gonna drink?");
                }
            }
        }
        else
        {
            for (Button btn:buttons) {
                btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C7594B")));
                if(MainActivity.GetLanguage() == Languages.Polish)
                {
                    text.setText("Kogo chcesz usunąć?");
                }
                else if(MainActivity.GetLanguage() == Languages.English)
                {
                    text.setText("Who are You deleting?");
                }
            }
        }
    }


    public void AddButton(View view) {
        Intent intent = new Intent(this, PersonCreator.class);
        startActivity(intent);
    }
}