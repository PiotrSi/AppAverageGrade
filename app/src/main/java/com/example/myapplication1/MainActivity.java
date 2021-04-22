package com.example.myapplication1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    EditText imie;
    EditText nazwisko;
    EditText liczba;
    TextView button;
    String pimie="";
    String pnazwisko="";
    int pliczba=0;
    String psliczba;
    boolean poSredniej=false;
    Intent dane;

    public static final String NAPIS="napis";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.constraint_layout);
        imie = findViewById(R.id.imie);
        nazwisko = findViewById(R.id.nazwisko);
        liczba = findViewById(R.id.liczba);
        button= findViewById(R.id.button1);

        imie.addTextChangedListener(spr);
        nazwisko.addTextChangedListener(spr);
        liczba.addTextChangedListener(spr);



        imie.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    pimie = imie.getText().toString();
                    if(pimie.equals("")){
                        imie.setError(getString(R.string.imieErr));
                        Toast imietoast = Toast.makeText(MainActivity.this,R.string.imieErr,Toast.LENGTH_SHORT);
                        imietoast.show();

                    }
                }
            }
        });

        nazwisko.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    pnazwisko = nazwisko.getText().toString();
                    if(pnazwisko.equals("")){
                        nazwisko.setError(getString(R.string.nazwiskoErr));
                        Toast nazwiskotoast = Toast.makeText(MainActivity.this,R.string.nazwiskoErr,Toast.LENGTH_SHORT);
                        nazwiskotoast.show();

                    }
                }
            }
        });

        liczba.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    psliczba = liczba.getText().toString();
                    if(psliczba.equals("")) {

                        liczba.setError(getString(R.string.liczbaErr));
                    }else {
                        pliczba = parseInt(psliczba);
                        if (pliczba < 5 || pliczba > 15) {
                            liczba.setError(getString(R.string.liczbaErr));
                            Toast liczbatoast = Toast.makeText(MainActivity.this, R.string.liczbaErr, Toast.LENGTH_SHORT);
                            liczbatoast.show();
                        }

                    }
                }

            }
        });


        button.setOnClickListener(new View.OnClickListener()
               {
                   @Override
                   public void onClick(View v)
                   {
                       uruchomDrugaAktywnosc();
                   }
               }
        );

    }
    private TextWatcher spr = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String pimie = imie.getText().toString().trim();
            String pnazwisko = nazwisko.getText().toString().trim();
            String pliczba = liczba.getText().toString();


            if(!pimie.isEmpty() && !pnazwisko.isEmpty() && !pliczba.isEmpty() && parseInt(pliczba)>4 && parseInt(pliczba)<16) {
                button.setVisibility(View.VISIBLE);
            }else button.setVisibility(View.INVISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);


        imie.setText(savedInstanceState.getString("kImie"));
        nazwisko.setText(savedInstanceState.getString("knazwisko"));

        poSredniej=savedInstanceState.getBoolean("PO");
        if(poSredniej){
            dane=savedInstanceState.getParcelable("info");
            powrot();
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }


@Override
public void onSaveInstanceState(Bundle outState) {
    outState.putString("kImie",imie.getText().toString());
    outState.putString("knazwisko",nazwisko.getText().toString());
    //outState.putInt("kliczba",pliczba);
    outState.putBoolean("PO",poSredniej);
    outState.putParcelable("info",dane);

    super.onSaveInstanceState(outState);
}

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            dane=data;
            powrot();
        }
    }

    private void uruchomDrugaAktywnosc()
    {


        Intent zamiar=new Intent(this,Druga.class);

        String sLiczba= liczba.getText().toString();

        zamiar.putExtra(NAPIS,sLiczba);
        startActivityForResult(zamiar,REQUEST_CODE);
    }

    private void powrot(){
        TextView psrednia = findViewById(R.id.srednia);
        Bundle pakunek=dane.getExtras();
        double srednia=pakunek.getDouble("avg");

        psrednia.setVisibility(View.VISIBLE);
        psrednia.setText(getString(R.string.napisSrednia)+srednia);
        if(srednia >=3){
            button.setText(getString(R.string.sup));
        }else button.setText(getString(R.string.niesup));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(psrednia.getContext());
                alertDialog.setTitle(getString(R.string.koniecP));
                if(srednia >=3){
                    alertDialog.setMessage(getString(R.string.gratulacje));
                }else alertDialog.setMessage(getString(R.string.podanie));
                alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                alertDialog.create().show();
            }
        });
        poSredniej = true;

    }


}