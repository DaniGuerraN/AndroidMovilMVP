package com.example.MovilMVP.Views;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.MovilMVP.Enums.Enums;
import com.example.MovilMVP.Helpers.Helpers;
import com.example.MovilMVP.HelpersServices.HelpersService;
import com.example.MovilMVP.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    private EditText usuario;
    private EditText password;
    private Button entrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usuario = (EditText) findViewById(R.id.editText);
        password = (EditText)findViewById(R.id.editText2);
        entrar = (Button)findViewById(R.id.btnentrar);
        entrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    login();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void login() throws JSONException {

        JSONObject oJSONObject = new JSONObject();
        oJSONObject.put("username",usuario.getText().toString());
        oJSONObject.put("password",password.getText().toString());
        System.out.println(usuario.getText().toString());
        System.out.println(password.getText().toString());
        ByteArrayEntity oEntity = null;
        try {
            oEntity = new ByteArrayEntity(oJSONObject.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        oEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

        AsyncHttpClient client = HelpersService.getClient();
        System.out.println(Helpers.URL+Enums.login);
        client.post(getApplicationContext(), Helpers.URL+Enums.login, (HttpEntity) oEntity, "application/json", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String resp = new String(responseBody);
                String res="null";
                boolean bol = false;
                try {
                    JSONObject testV =new JSONObject(new String(responseBody));
                    res = testV.get("token").toString();
                    bol = Boolean.parseBoolean(testV.get("super").toString());
                }catch (Exception e){
                    System.out.println(e);
                }
                Helpers.token=res;
                Helpers.admin=bol;
                Intent intent = new Intent(MainActivity.this , TablaPrincipal.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }
}
