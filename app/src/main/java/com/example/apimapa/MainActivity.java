package com.example.apimapa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public TextView textviewget;
    TextView POST;
    EditText txtTitle;
    EditText txtBody;
    EditText txtId;
    Button btnPOST;
    Button btnMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textviewget = findViewById(R.id.txtInfo);
        POST = findViewById(R.id.textView3);
        txtTitle = findViewById(R.id.txtTitle);
        txtBody = findViewById(R.id.txtBody);
        txtId = findViewById(R.id.txtId);
        btnPOST = findViewById(R.id.btnPOST);
        btnMapa = findViewById(R.id.btnMapa);

        metodoGET();

        btnPOST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                metodoPOST();
            }
        });

        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(MainActivity.this, com.example.apimapa.Map.class));
            }
        });
    }

    public void metodoGET () {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://jsonplaceholder.typicode.com/posts/1";

        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String id = "title";
                try {
                    textviewget.setText(response.getString(id).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    textviewget.setText(e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textviewget.setText(error.toString());
            }
        });
        queue.add(jsonObjectRequest1);
    }
    public void metodoPOST(){
        String url = "https://jsonplaceholder.typicode.com/posts";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Id: " + (jsonObject.getString("userId") + "\n"));
                    stringBuilder.append("Titulo: " + (jsonObject.getString("title") + "\n"));
                    stringBuilder.append("Descripcion: " + (jsonObject.getString("body")));

                    Toast.makeText(MainActivity.this, "Haciendo el POST", Toast.LENGTH_LONG).show();
                    POST.setText(stringBuilder.toString());

                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("title", txtTitle.getText().toString());
                params.put("body", txtBody.getText().toString());
                params.put("userId", txtId.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }
}