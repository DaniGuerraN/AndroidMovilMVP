package com.example.MovilMVP.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import cz.msebera.android.httpclient.Header;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.MovilMVP.Enums.Enums;
import com.example.MovilMVP.Helpers.Helpers;
import com.example.MovilMVP.HelpersServices.HelpersService;
import com.example.MovilMVP.R;
import com.example.MovilMVP.Views.TablaPrincipal;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;


public class AdminTable extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String iden="a";
    TableLayout tl;
    private static TablaPrincipal mCurrentInstance;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AdminTable() {
        // Required empty public constructor
    }


    public static AdminTable newInstance(String param1, String param2) {
        AdminTable fragment = new AdminTable();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static Context context() {
        return mCurrentInstance.getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_tabla_administrador, container, false);
        tl = (TableLayout) view.findViewById(R.id.tl);
        AsyncHttpClient cliente = HelpersService.getClientToken();
        cliente.get(Helpers.URL+ Enums.getCarreras,new JsonHttpResponseHandler(){
            @SuppressLint("ResourceType")
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                Context con = getView().getContext();

                try {

                    TableRow tr_head = new TableRow(con);
                    tr_head.setId(10);
                    tr_head.setBackgroundColor(Color.GRAY);

                    TextView label_date = new TextView(con);
                    label_date.setId(20);
                    label_date.setText("ID");
                    label_date.setTextColor(Color.WHITE);
                    label_date.setPadding(5, 5, 50, 5);
                    tr_head.addView(label_date);

                    TextView label_name = new TextView(con);
                    label_name.setId(20);
                    label_name.setText("Nombre");
                    label_name.setTextColor(Color.WHITE);
                    label_name.setPadding(5, 5, 50, 5);
                    tr_head.addView(label_name);// add the column to the table row here
                    tr_head.setLayoutParams(new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));

                    TextView label_carrera = new TextView(con);
                    label_carrera.setId(21);
                    label_carrera.setText("Periodo");
                    label_carrera.setTextColor(Color.WHITE);
                    label_carrera.setPadding(5, 5, 50, 5);
                    tr_head.addView(label_carrera);// add the column to the table row here

                    tl.addView(tr_head, new TableLayout.LayoutParams(
                            TableRow.LayoutParams.FILL_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));

                    for (int i = 0; i <= response.length()-1 ; i++){
                        final JSONObject temp = response.getJSONObject(i);
                        System.out.println(temp.get("id").toString());
                        TableRow tr = new TableRow(con);
                        tr.setBackgroundColor(Color.WHITE);
                        tr.setId(100+i);
                        tr.setLayoutParams(new TableRow.LayoutParams(
                                TableRow.LayoutParams.FILL_PARENT,
                                TableRow.LayoutParams.WRAP_CONTENT));

                        TextView labelID = new TextView(con);
                        labelID.setId(i+200);
                        labelID.setText(temp.get("id").toString());
                        labelID.setPadding(2, 5, 5, 0);
                        labelID.setTextColor(Color.BLACK);
                        tr.addView(labelID);

                        TextView labelNombre = new TextView(con);
                        labelNombre.setId(i+200);
                        labelNombre.setText(temp.get("name").toString());
                        labelNombre.setPadding(2, 5, 5, 0);
                        labelNombre.setTextColor(Color.BLACK);
                        tr.addView(labelNombre);


                        TextView labelCarrera = new TextView(con);
                        labelCarrera.setId(i+200);
                        labelCarrera.setText(temp.get("periodo").toString());
                        labelCarrera.setPadding(2, 0, 5, 0);
                        labelCarrera.setTextColor(Color.BLACK);
                        tr.addView(labelCarrera);

                        Button btnCosa = new Button(con);
                        btnCosa.setId(i+200);
                        btnCosa.setText("Ver mas");
                        btnCosa.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    String ids = temp.get("id").toString();
                                    iden = ids;
                                    getData(iden);
                                    System.out.println(temp.get("id").toString());
                                    System.out.println(".-..-.-.-.-.-.-..-.-.-");
                                }catch (Exception e){}
                            }
                        });
                        tr.addView(btnCosa);


                        tl.addView(tr, new TableLayout.LayoutParams(
                                TableRow.LayoutParams.FILL_PARENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable error, JSONObject response) {
                JSONObject arr = null;
                Context con = getView().getContext();
                try {
                    Toast.makeText(con, "Error al obtener datos, quizá se murió el server", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void getData(String id){
        AsyncHttpClient cliente = HelpersService.getClientToken();
        cliente.get(Helpers.URL+Enums.getCarreras+id, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String x = new String(responseBody);
                AlertDialog.Builder alerta = new AlertDialog.Builder(getView().getContext());
                alerta.setMessage(x);
                alerta.setCancelable(false);
                alerta.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onFinish();
                    }
                });
                AlertDialog titlulo = alerta.create();
                titlulo.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
