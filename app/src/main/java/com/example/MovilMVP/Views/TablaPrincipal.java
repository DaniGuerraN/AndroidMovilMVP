package com.example.MovilMVP.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.example.MovilMVP.Fragments.AdminTable;
import com.example.MovilMVP.Fragments.TablaAlumnos;
import com.example.MovilMVP.Helpers.Helpers;
import com.example.MovilMVP.R;

public class TablaPrincipal extends AppCompatActivity implements TablaAlumnos.OnFragmentInteractionListener, AdminTable.OnFragmentInteractionListener {
    AdminTable AdminTable;
    TablaAlumnos TablaAlumnos;
    private static TablaPrincipal TablaPrincipal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla_principal);
        AdminTable = new AdminTable();
        TablaAlumnos = new TablaAlumnos();
        if (Helpers.admin == true){
            getSupportFragmentManager().beginTransaction().add(R.id.fragment2,TablaAlumnos).commit();
        }else{
            getSupportFragmentManager().beginTransaction().add(R.id.fragment2, AdminTable).commit();
        }
    }

    public static Context context() {
        return TablaPrincipal.getApplicationContext();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
