package com.example.myapplication1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InteraktywnyAdapterTablicy extends
        RecyclerView.Adapter<InteraktywnyAdapterTablicy.OcenyViewHolder> {
    private List<ModelOceny> mListaOcen;
    private LayoutInflater mPompka;
    private int liczba;
    int nr;
    private Activity kontekst;

    public InteraktywnyAdapterTablicy(Activity kontekst, List<ModelOceny> listaOcen) {
        mPompka = kontekst.getLayoutInflater();
        this.mListaOcen = listaOcen;
    }

    @NonNull
    @Override
    public OcenyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //utworzenie layoutu wiersza na podstawie XML
        View wiersz = mPompka.inflate(R.layout.wiersz_oceny, null);
        //zwrócenie nowego obiektu holdera
        return new OcenyViewHolder(wiersz);
    }

    @Override
    public void onBindViewHolder(@NonNull OcenyViewHolder ocenyViewHolder, int numerWiersza) {

        //powiązenia grupy przyciwkoów radiowych z wierszem listy
        ocenyViewHolder.ocenaGroup.setTag(mListaOcen.get(numerWiersza));
        nr=numerWiersza;
        ModelOceny value = mListaOcen.get(numerWiersza);

        //ustawienie nazwy przedmiotu
        ocenyViewHolder.etykieta.setTag(numerWiersza);
        ocenyViewHolder.etykieta.setText(value.getNazwa());

        //zaznaczenie odpowiedniego przycisku radiowego

        switch (value.getOcena()){
            case 2:
                ocenyViewHolder.ocenaGroup.check(R.id.ocena2);
                break;
            case 3:
                ocenyViewHolder.ocenaGroup.check(R.id.ocena3);
                break;
            case 4:
                ocenyViewHolder.ocenaGroup.check(R.id.ocena4);
                break;
            case 5:
                ocenyViewHolder.ocenaGroup.check(R.id.ocena5);
                break;
        }

    }

    //zwraca liczbę elementów
    @Override
    public int getItemCount() {
        return mListaOcen.size();
    }

    //pojemnik przchowujący referecje do potrzebnych elementów wiersza
    //nadaje się teś jako obiekt implementujący listenery - każdy wiersz ma własny holder
    public class OcenyViewHolder extends RecyclerView.ViewHolder implements
            RadioGroup.OnCheckedChangeListener {
        //pola przechowujące referencje do elementów wiersza
        TextView etykieta;
        RadioGroup ocenaGroup;
        RadioButton ocenaButton;
        View glownyElement;
        public OcenyViewHolder(@NonNull View glownyElementWiersza) {
            super(glownyElementWiersza);
            this.glownyElement = glownyElementWiersza;
            //odczytanie referencji do elementow wiersza
            etykieta = glownyElementWiersza.findViewById(R.id.ocena);
            ocenaGroup = glownyElementWiersza.findViewById(R.id.grupaOceny);
            ocenaGroup.setOnCheckedChangeListener(this);
            //ustawienie obsługi zdarzeń w komponentach znajducjących się w wierszu
        }

        //implementacja interfejsow obsługujących zdarzenia



        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            ModelOceny value =(ModelOceny) group.getTag();

            liczba = checkedId;
            switch (checkedId){
                case R.id.ocena2:
                    liczba = 2;
                    break;
                case R.id.ocena3:
                    liczba = 3;
                    break;
                case R.id.ocena4:
                    liczba = 4;
                    break;
                case R.id.ocena5:
                    liczba = 5;
                    break;
            }
            value.setOcena(liczba);
        }
    }
}