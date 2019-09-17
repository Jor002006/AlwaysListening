package com.example.alwayslistening;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Anadir extends AppCompatActivity {

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir);
        //7
    }*/

    public void Atras (View view){
        Intent atras = new Intent(this, MainActivity.class);
        startActivity(atras);

    }
}
