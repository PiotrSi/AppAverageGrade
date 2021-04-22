package com.example.myapplication1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Druga extends AppCompatActivity {

    ArrayList<ModelOceny> mDane;
    RecyclerView mListaOcen;
    View avg;
    int intLiczba;
    public static final String NAPIS="napis";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_ocen_rv);
           avg = findViewById(R.id.averageButton);
        String[] nazwyPrzedmiotow=getResources().getStringArray(R.array.tablica);

        Bundle pakunek=getIntent().getExtras();

        String liczba =pakunek.getString(MainActivity.NAPIS);
        intLiczba=Integer.parseInt(liczba);




        mDane = new ArrayList<ModelOceny>();


            if (savedInstanceState != null) {

                mDane = savedInstanceState.getParcelableArrayList("COS");

        }else {
            for (int i = 0; i < intLiczba; i++)
                mDane.add(new ModelOceny(nazwyPrzedmiotow[i], 2));
        }
        InteraktywnyAdapterTablicy adapter = new InteraktywnyAdapterTablicy(this, mDane);
        //znalezienie referencji do obiektu RecyclerView
        mListaOcen = findViewById(R.id.lista_ocen_rv);

        //połączenie listy z danymi
        mListaOcen.setAdapter(adapter);
        //ustawienie Layoutu rozmieszczjącego elemnty w RecyclerView
        mListaOcen.setLayoutManager(new LinearLayoutManager(this));

        avg.setOnClickListener(new View.OnClickListener()
                                  {
                                      @Override
                                      public void onClick(View v)
                                      {
                                          uruchomMainAktywnosc();
                                      }
                                  }
        );
    }
    private  double liczAVG()
    {
        double suma=0;
        for(int i= 0; i<intLiczba;i++) {
            ModelOceny value = mDane.get(i);
            suma += value.getOcena();
        }
        suma /=intLiczba;
        return suma;
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
    }

    private void uruchomMainAktywnosc()
    {
        double wartosc = liczAVG();
        Intent zamiar=new Intent();
        Bundle pakunek=new Bundle();
        pakunek.putDouble("avg",wartosc);
        int kod = 1010;
        zamiar.putExtras(pakunek);
        setResult(RESULT_OK,zamiar);
        finish();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelableArrayList("COS", mDane);

        super.onSaveInstanceState(outState);
    }

}
