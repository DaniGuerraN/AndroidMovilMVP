package com.example.MovilMVP.Models;

import android.content.Context;

import com.example.MovilMVP.HelpersServices.HelpersService;
import com.loopj.android.http.JsonHttpResponseHandler;

public class Alumno {
    public static void getAlumnos(Context con, JsonHttpResponseHandler JsonHttpResponseHandler){
        HelpersService.getAllUsers(con,new JsonHttpResponseHandler(){

        });
    }

    public static void getAlumno(Context con,String id){
        HelpersService.getUser(id,new JsonHttpResponseHandler(){

        });
    }
}
