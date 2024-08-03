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
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class ChoosePerson extends AppCompatActivity {


    public static final String PERSON1Exist = "prs1e";
    public static final String PERSON1Name = "prs1n";
    public static final String PERSON1Weight = "prs1w";
    public static final String PERSON1Height = "prs1h";
    public static final String PERSON1Age = "prs1a";
    public static final String PERSON1Sex = "prs1s";
    public static final String PERSON1Nr = "pr1nr";

    public static final String PERSON2Exist = "prs2ex";
    public static final String PERSON2Name = "prs2n";
    public static final String PERSON2Weight = "prs2w";
    public static final String PERSON2Height = "prs2h";
    public static final String PERSON2Age = "prs2a";
    public static final String PERSON2Sex = "prs2s";
    public static final String PERSON2Nr = "pr2nr";

    public static final String PERSON3Exist = "prs3ex";
    public static final String PERSON3Name = "prs3n";
    public static final String PERSON3Weight = "prs3w";
    public static final String PERSON3Height = "prs3h";
    public static final String PERSON3Age = "prs3a";
    public static final String PERSON3Sex = "prs3s";
    public static final String PERSON3Nr = "p31nr";

    public static final String PERSON4Exist = "prs4ex";
    public static final String PERSON4Name = "prs4n";
    public static final String PERSON4Weight = "prs4w";
    public static final String PERSON4Height = "prs4h";
    public static final String PERSON4Age = "prs4a";
    public static final String PERSON4Sex = "prs4s";
    public static final String PERSON4Nr = "pr4nr";

    public static final String PERSON5Exist = "prs5ex";
    public static final String PERSON5Name = "prs5n";
    public static final String PERSON5Weight = "prs5w";
    public static final String PERSON5Height = "prs5h";
    public static final String PERSON5Age = "prs5a";
    public static final String PERSON5Sex = "prs5s";
    public static final String PERSON5Nr = "pr5nr";

    public static Boolean LeadsToAlculator;
    public static Person selectedUser;

    private ArrayList<Person> usersList = new ArrayList<Person>();
    public static Person[] usersArray= {null, null, null, null, null};
    private boolean deleteMode = false;
    private Button buttons[] = {null,null,null,null,null};
    private TextView text;
    private String toastMessageTooMany;
    private String toastMessageTooLittle;
    private  String normalText;
    private  String deleteText;



   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_person);

        selectedUser = null;
        deleteMode = false;

        buttons[0] = findViewById(R.id.Person1);
        buttons[1] = findViewById(R.id.Person2);
        buttons[2] = findViewById(R.id.Person3);
        buttons[3] = findViewById(R.id.Person4);
        buttons[4] = findViewById(R.id.Person5);
        text = findViewById(R.id.Text1);
        ChangeLanguage();
        ChangeButtons();
        GetUsers();

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

        if(sharedPreferences.getBoolean(PERSON1Exist, false)){
            Person1 = new Person(sharedPreferences.getString(PERSON1Name,"Error"), sharedPreferences.getInt(PERSON1Weight, 0), sharedPreferences.getInt(PERSON1Height, 0), sharedPreferences.getString(PERSON1Sex, ""), sharedPreferences.getInt(PERSON1Age, 20), sharedPreferences.getInt(PERSON1Nr, 99));
        }else{
            Person1 = null;
        }
        if(sharedPreferences.getBoolean(PERSON2Exist, false))
        {
            Person2 = new Person(sharedPreferences.getString(PERSON2Name,"Error"), sharedPreferences.getInt(PERSON2Weight, 0), sharedPreferences.getInt(PERSON2Height, 0), sharedPreferences.getString(PERSON2Sex, ""), sharedPreferences.getInt(PERSON2Age, 20),sharedPreferences.getInt(PERSON2Nr, 99));
        }else{
            Person2 = null;
        }
        if(sharedPreferences.getBoolean(PERSON3Exist, false)){
            Person3 = new Person(sharedPreferences.getString(PERSON3Name,"Error"), sharedPreferences.getInt(PERSON3Weight, 0), sharedPreferences.getInt(PERSON3Height, 0), sharedPreferences.getString(PERSON3Sex, ""), sharedPreferences.getInt(PERSON3Age, 20),sharedPreferences.getInt(PERSON3Nr, 99));
        }else{
            Person3 = null;
        }
        if(sharedPreferences.getBoolean(PERSON4Exist, false)){
            Person4 = new Person(sharedPreferences.getString(PERSON4Name,"Error"), sharedPreferences.getInt(PERSON4Weight, 0), sharedPreferences.getInt(PERSON4Height, 0), sharedPreferences.getString(PERSON4Sex, ""), sharedPreferences.getInt(PERSON4Age, 20), sharedPreferences.getInt(PERSON4Nr, 99));
        }else{
            Person4 = null;
        }
        if(sharedPreferences.getBoolean(PERSON5Exist, false)){
            Person5 = new Person(sharedPreferences.getString(PERSON5Name,"Error"), sharedPreferences.getInt(PERSON5Weight, 0), sharedPreferences.getInt(PERSON5Height, 0), sharedPreferences.getString(PERSON5Sex, ""), sharedPreferences.getInt(PERSON5Age, 20), sharedPreferences.getInt(PERSON5Nr, 99));
        }else{
            Person5 = null;
        }

        usersArray[0] = Person1;
        usersArray[1] = Person2;
        usersArray[2] = Person3;
        usersArray[3] = Person4;
        usersArray[4] = Person5;

        for(int i = 0; i<5; i++)
        {
            if(usersArray[i] != null)
            {
                usersList.add(usersArray[i]);
            }
        }


        for(int i = 0; i < 5; i++)
        {
            if(i < usersList.size())
            {
                buttons[i].setVisibility(View.VISIBLE);
                buttons[i].setText(usersList.get(i).Name);
            }
            else
            {
                buttons[i].setVisibility(View.GONE);
            }
        }

    }

    public void DeleteButton(View view) {
        if(usersList.size() == 0)
        {
            Toast toast = Toast.makeText(getApplicationContext(), toastMessageTooLittle, Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            deleteMode = !deleteMode;
            ChangeButtons();
        }

    }

    private void ChangeButtons()
    {
        if(deleteMode == false)
        {

            for (Button btn:buttons)
            {
                btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
            }
            text.setText(normalText);

        }
        else
        {
            for (Button btn:buttons)
            {
                btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C7594B")));
            }
            text.setText(deleteText);
        }
    }


    public void AddButton(View view) {
       if(usersList.size() >= 5)
       {
           Toast toast = Toast.makeText(getApplicationContext(), toastMessageTooMany, Toast.LENGTH_SHORT);
           toast.show();
       }
       else
       {
           Intent intent = new Intent(this, PersonCreator.class);
           startActivity(intent);
       }
    }

    private void RemoveUser(int index)
    {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);

        switch(index)
        {
            case 1:
                sharedPreferences.edit().remove(PERSON1Exist).apply();
                sharedPreferences.edit().remove(PERSON1Weight).apply();
                sharedPreferences.edit().remove(PERSON1Height).apply();
                sharedPreferences.edit().remove(PERSON1Sex).apply();
                sharedPreferences.edit().remove(PERSON1Age).apply();
                sharedPreferences.edit().remove(PERSON1Name).apply();
                sharedPreferences.edit().remove(PERSON1Nr).apply();
                break;
            case 2:
                sharedPreferences.edit().remove(PERSON2Exist).apply();
                sharedPreferences.edit().remove(PERSON2Weight).apply();
                sharedPreferences.edit().remove(PERSON2Height).apply();
                sharedPreferences.edit().remove(PERSON2Sex).apply();
                sharedPreferences.edit().remove(PERSON2Age).apply();
                sharedPreferences.edit().remove(PERSON2Name).apply();
                sharedPreferences.edit().remove(PERSON2Nr).apply();
                break;
            case 3:
                sharedPreferences.edit().remove(PERSON3Exist).apply();
                sharedPreferences.edit().remove(PERSON3Weight).apply();
                sharedPreferences.edit().remove(PERSON3Height).apply();
                sharedPreferences.edit().remove(PERSON3Sex).apply();
                sharedPreferences.edit().remove(PERSON3Age).apply();
                sharedPreferences.edit().remove(PERSON3Name).apply();
                sharedPreferences.edit().remove(PERSON3Nr).apply();
                break;

            case 4:
                sharedPreferences.edit().remove(PERSON4Exist).apply();
                sharedPreferences.edit().remove(PERSON4Weight).apply();
                sharedPreferences.edit().remove(PERSON4Height).apply();
                sharedPreferences.edit().remove(PERSON4Sex).apply();
                sharedPreferences.edit().remove(PERSON4Age).apply();
                sharedPreferences.edit().remove(PERSON4Name).apply();
                sharedPreferences.edit().remove(PERSON4Nr).apply();
                break;

            case 5:
                sharedPreferences.edit().remove(PERSON5Exist).apply();
                sharedPreferences.edit().remove(PERSON5Weight).apply();
                sharedPreferences.edit().remove(PERSON5Height).apply();
                sharedPreferences.edit().remove(PERSON5Sex).apply();
                sharedPreferences.edit().remove(PERSON5Age).apply();
                sharedPreferences.edit().remove(PERSON5Name).apply();
                sharedPreferences.edit().remove(PERSON5Nr).apply();
                break;
        }
    }

    private void ChangeLanguage()
    {
        Button btnAdd = findViewById(R.id.add);
        Button btnDel = findViewById(R.id.delete);

        if(MainActivity.GetLanguage() == Languages.Polish)
        {
            toastMessageTooLittle = "Brak użytkowników do usunięcia!";
            toastMessageTooMany = "Za dużo użytkowników, usuń kogoś!";
            normalText = "Kto będzie pił?";
            deleteText = "Kogo chcesz usunąć?";
            btnAdd.setText("Dodaj");
            btnDel.setText("Usuń");
        }
        else
        {
            toastMessageTooLittle = "No users to delete!";
            toastMessageTooMany = "Too many users, delete someone!";
            normalText = "Who's gonna drink?";
            deleteText = "Who are you deleting?";
            btnAdd.setText("Add");
            btnDel.setText("Delete");
        }
    }

    public void Person1Click(View view)
    {
        if(deleteMode)
        {
            RemoveUser(usersList.get(0).Index);
            deleteMode = false;
            ChangeButtons();
            GetUsers();
        }
        else
        {
            AlcolatorSummary.user = usersArray[usersList.get(0).Index - 1];

            LeadToActivity();
        }
    }

    public void Person2Click(View view)
    {
        if(deleteMode)
        {
            RemoveUser(usersList.get(1).Index);
            deleteMode = false;
            ChangeButtons();
            GetUsers();
        }
        else
        {
            AlcolatorSummary.user = usersArray[usersList.get(1).Index - 1];

            LeadToActivity();
        }
    }

    public void Person3Click(View view)
    {
        if(deleteMode)
        {
            RemoveUser(usersList.get(2).Index);
            deleteMode = false;
            ChangeButtons();
            GetUsers();
        }
        else
        {
            AlcolatorSummary.user = usersArray[usersList.get(2).Index - 1];

            LeadToActivity();
        }
    }

    public void Person4Click(View view)
    {
        if(deleteMode)
        {
            RemoveUser(usersList.get(3).Index);
            deleteMode = false;
            ChangeButtons();
            GetUsers();
        }
        else
        {
            AlcolatorSummary.user = usersArray[usersList.get(3).Index - 1];

            LeadToActivity();
        }
    }

    public void Person5Click(View view)
    {
        if(deleteMode)
        {
            RemoveUser(usersList.get(4).Index);
            deleteMode = false;
            ChangeButtons();
            GetUsers();
        }
        else
        {
            AlcolatorSummary.user = usersArray[usersList.get(4).Index - 1];

            LeadToActivity();
        }
    }

    private void LeadToActivity()
    {
        Intent intent;
        if(LeadsToAlculator) intent = new Intent(this, ChooseDrinks.class);
        else intent = new Intent(this, SoberAtAdditionalInfo.class);

        startActivity(intent);
    }
}