package com.example.partycounter;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.IntArrayEvaluator;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PersonCreator extends AppCompatActivity {

    EditText name;
    Spinner weight;
    Spinner height;
    EditText weightInput;
    EditText heightInput;
    EditText ageInput;
    TextView main;
    TextView in1;
    TextView in2;
    RadioButton female;
    RadioButton male;
    Button save;
    String toastMessageFullFil;
    String toastMessageInvalidInput;
    String toastMessageInvalidAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_creator2);

        AssignLayout();
        SetSpinners();
        ChangeLanguage();
    }


    public void SaveButtonClick(View view) {
        if (name.getText().toString().matches("") || weightInput.getText().toString().matches("") || ageInput.getText().toString().matches("")|| heightInput.getText().toString().matches("") || (female.isChecked() == false && male.isChecked() == false))
        {
            Toast toast = Toast.makeText(getApplicationContext(), toastMessageFullFil, Toast.LENGTH_SHORT);
            toast.show();
        }
        else if(Integer.parseInt(ageInput.getText().toString()) < 17)
        {
            Toast toast = Toast.makeText(getApplicationContext(), toastMessageInvalidAge, Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {


            SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            int wagaKg = Integer.parseInt(weightInput.getText().toString());
            int wzrostCm = Integer.parseInt(heightInput.getText().toString());
            int wiek = Integer.parseInt(ageInput.getText().toString());

            switch(weight.getSelectedItem().toString())
            {
                case "lb":
                    wagaKg = Math.round(wagaKg * 0.453f);
                    break;

            }

            switch(height.getSelectedItem().toString())
            {
                case "inches":
                    wzrostCm = Math.round(wzrostCm / 2.54f);
                    break;

            }

            if(wagaKg > 0 && wzrostCm > 0)
            {
                int currentUser = 0;
                for (Person user : ChoosePerson.usersArray) {
                    if (user == null) {
                        switch (currentUser) {
                            case 0:
                                editor.putBoolean(ChoosePerson.PERSON1Exist, true);
                                editor.putString(ChoosePerson.PERSON1Name, name.getText().toString());
                                editor.putInt(ChoosePerson.PERSON1Height, wzrostCm);
                                editor.putInt(ChoosePerson.PERSON1Weight, wagaKg);
                                editor.putInt(ChoosePerson.PERSON1Age, wiek);
                                if (female.isChecked())
                                    editor.putString(ChoosePerson.PERSON1Sex, "Female");
                                else editor.putString(ChoosePerson.PERSON1Sex, "Male");
                                editor.putInt(ChoosePerson.PERSON1Nr, 1);
                                break;
                            case 1:
                                editor.putBoolean(ChoosePerson.PERSON2Exist, true);
                                editor.putString(ChoosePerson.PERSON2Name, name.getText().toString());
                                editor.putInt(ChoosePerson.PERSON2Height, wzrostCm);
                                editor.putInt(ChoosePerson.PERSON2Weight, wagaKg);
                                editor.putInt(ChoosePerson.PERSON2Age, wiek);
                                if (female.isChecked())
                                    editor.putString(ChoosePerson.PERSON2Sex, "Female");
                                else editor.putString(ChoosePerson.PERSON2Sex, "Male");
                                editor.putInt(ChoosePerson.PERSON2Nr, 2);
                                break;
                            case 2:
                                editor.putBoolean(ChoosePerson.PERSON3Exist, true);
                                editor.putString(ChoosePerson.PERSON3Name, name.getText().toString());
                                editor.putInt(ChoosePerson.PERSON3Height, wzrostCm);
                                editor.putInt(ChoosePerson.PERSON3Weight, wagaKg);
                                editor.putInt(ChoosePerson.PERSON3Age, wiek);
                                if (female.isChecked())
                                    editor.putString(ChoosePerson.PERSON3Sex, "Female");
                                else editor.putString(ChoosePerson.PERSON3Sex, "Male");
                                editor.putInt(ChoosePerson.PERSON3Nr, 3);
                                break;
                            case 3:
                                editor.putBoolean(ChoosePerson.PERSON4Exist, true);
                                editor.putString(ChoosePerson.PERSON4Name, name.getText().toString());
                                editor.putInt(ChoosePerson.PERSON4Height, wzrostCm);
                                editor.putInt(ChoosePerson.PERSON4Weight, wagaKg);
                                editor.putInt(ChoosePerson.PERSON4Age, wiek);
                                if (female.isChecked())
                                    editor.putString(ChoosePerson.PERSON4Sex, "Female");
                                else editor.putString(ChoosePerson.PERSON4Sex, "Male");
                                editor.putInt(ChoosePerson.PERSON4Nr, 4);
                                break;
                            case 4:
                                editor.putBoolean(ChoosePerson.PERSON5Exist, true);
                                editor.putString(ChoosePerson.PERSON5Name, name.getText().toString());
                                editor.putInt(ChoosePerson.PERSON5Height, wzrostCm);
                                editor.putInt(ChoosePerson.PERSON5Weight, wagaKg);
                                editor.putInt(ChoosePerson.PERSON5Age, wiek);
                                if (female.isChecked())
                                    editor.putString(ChoosePerson.PERSON5Sex, "Female");
                                else editor.putString(ChoosePerson.PERSON5Sex, "Male");
                                editor.putInt(ChoosePerson.PERSON5Nr, 5);
                                break;
                        }
                        editor.apply();
                        Intent intent = new Intent(this, ChoosePerson.class);
                        startActivity(intent);
                        break;
                    }
                    currentUser++;
                }
            }
            else
            {
                Toast toast = Toast.makeText(getApplicationContext(), toastMessageInvalidInput, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    private void SetSpinners()
    {
        ArrayAdapter<CharSequence> adapterW = ArrayAdapter.createFromResource(this, R.array.weights, R.layout.spinner_data);
        ArrayAdapter<CharSequence> adapterH = ArrayAdapter.createFromResource(this, R.array.heights, R.layout.spinner_data);
        adapterW.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        adapterH.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        weight.setAdapter(adapterW);
        height.setAdapter(adapterH);
    }


    private void AssignLayout()
    {
        name = findViewById(R.id.editTextTextPersonName2);
        weight = findViewById(R.id.weightSpinner);
        height = findViewById(R.id.heightSpinner);
        main = findViewById(R.id.Text1);
        in1 = findViewById(R.id.textView2);
        in2 = findViewById(R.id.textView3);
        female = findViewById(R.id.Woman);
        male = findViewById(R.id.Man);
        save = findViewById(R.id.Save);
        weightInput = findViewById(R.id.weight);
        heightInput = findViewById(R.id.editTextNumber4);
        ageInput = findViewById(R.id.editTextAge);
    }

    private void ChangeLanguage()
    {
        switch(MainActivity.GetLanguage())
        {
            case Polish:
                main.setText("Stwórz użytkownika");
                name.setHint("Imię");
                weightInput.setHint("Waga");
                heightInput.setHint("Wzrost");
                ageInput.setHint("Wiek");
                in1.setText("w");
                in2.setText("w");
                female.setText("Kobieta");
                male.setText("Mężczyzna");
                save.setText("Zapisz");
                toastMessageFullFil = "Wypełnij wszystkie pola!";
                toastMessageInvalidInput = "Niepoprawny format danych!";
                toastMessageInvalidAge = "Minimalny wiek to 16 lat!";
                break;

            case English:
                main.setText("Create a user");
                name.setHint("Name");
                weightInput.setHint("Weight");
                heightInput.setHint("Height");
                ageInput.setHint("Age");
                in1.setText("in");
                in2.setText("in");
                female.setText("Female");
                male.setText("Male");
                save.setText("Save");
                toastMessageFullFil = "Fill all fields!";
                toastMessageInvalidInput = "Invalid data format!";
                toastMessageInvalidAge = "Minimum age is 16!";
                break;
        }
    }

}