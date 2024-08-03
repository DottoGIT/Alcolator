package com.example.partycounter;

public class Symptom {

    public Symptom (float _minIntox, float _maxIntox, String _name)
    {
        MinIntox = _minIntox;
        MaxIntox = _maxIntox;
        Name = _name;
    }

    public float MinIntox;
    public float MaxIntox;
    public String Name;
}
