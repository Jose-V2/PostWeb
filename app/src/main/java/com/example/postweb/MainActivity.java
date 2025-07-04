package com.example.postweb;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lista;
    ArrayList<String> listaTitulos;
    ArrayList<Integer> listaID;
    ArrayAdapter<String> adp;
    RequestQueue requestQueue;

    String url = "https://jsonplaceholder.typicode.com/posts";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        lista = (ListView) findViewById(R.id.lista);
        listaTitulos = new ArrayList<>();
        listaID = new ArrayList<>();

        adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaTitulos);

        lista.setAdapter(adp);

        requestQueue = Volley.newRequestQueue(this);

        cargarPost();

        lista.setOnItemClickListener((parent, view, position, id) -> {
            int postId = listaID.get(position);
            Intent intent = new Intent(MainActivity.this, ActivityPostView.class);
            intent.putExtra("postId", postId);
            startActivity(intent);
                });
    }

    private void cargarPost() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        for (int i=0; i < response.length(); i++)
                        {
                            JSONObject post = response.getJSONObject(i);
                            int id = post.getInt("id");
                            String titulo = post.getString("title");

                            listaTitulos.add(titulo);
                            listaID.add(id);
                        }
                        adp.notifyDataSetChanged();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                },
                error -> {
                        error.printStackTrace();
                }
                );
        requestQueue.add(jsonArrayRequest);
    }
}