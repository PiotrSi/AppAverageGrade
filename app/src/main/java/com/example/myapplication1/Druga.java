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

    //EditText imie;
    //EditText nazwisko;
    //EditText liczba;
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
        //setContentView(R.layout.constraint_layout);
        //mDane = savedInstanceState.getParcelableArrayList("COS");
        //imie = findViewById(R.id.imie);
        //odczyt danych z pierwszej
        Bundle pakunek=getIntent().getExtras();
        //imie.setText(pakunek.getString(MainActivity.NAPIS));
        String liczba =pakunek.getString(MainActivity.NAPIS);
        intLiczba=Integer.parseInt(liczba);




        mDane = new ArrayList<ModelOceny>();


            if (savedInstanceState != null) {
                int[] table = new int[intLiczba];
                table = savedInstanceState.getIntArray("COS");

            for (int i = 0; i < intLiczba; i++)
                mDane.add(new ModelOceny(nazwyPrzedmiotow[i], table[i]));



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
        //mDane = savedInstanceState.getParcelableArrayList("COS");
    }

    private void uruchomMainAktywnosc()
    {
        //EditText et=(EditText) findViewById(R.id.imie);
        double wartosc = liczAVG();
        Intent zamiar=new Intent();
        //zamiar.putExtra(NAPIS,imie.getText().toString());
        //String sLiczba= liczba.getText().toString();
        Bundle pakunek=new Bundle();
        pakunek.putDouble("avg",wartosc);
        int kod = 1010;
        zamiar.putExtras(pakunek);
        //startActivityForResult(zamiar, 100);
        setResult(RESULT_OK,zamiar);
        finish();
        //startActivityForResult(zamiar,kod);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        //outState.putString("kImie",imie.getText().toString());

        //outState.putIntArray("RADIO",mDane);
        //outState.putInt("COS",);

        /*
        int[] table = new int[intLiczba];
        ModelOceny model ;
        for(int i =0;i <intLiczba;i++) {
            model = mDane.get(i);
            table[i]= model.getOcena();
        }
        */
        outState.putParcelable("COS", (Parcelable) mDane);
        //outState.putParcelableArrayList("COS", (ArrayList<? extends Parcelable>) mDane);
        //outState.putIntArray("COS",table);
        super.onSaveInstanceState(outState);
    }

}
