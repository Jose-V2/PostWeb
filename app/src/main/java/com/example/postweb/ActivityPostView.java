package com.example.postweb;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;

public class ActivityPostView extends AppCompatActivity {

    TextView titulo, contenido;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post_view);

        titulo = (TextView) findViewById(R.id.titulo);
        contenido = (TextView) findViewById(R.id.contenido);
        requestQueue = Volley.newRequestQueue(this);

        int postId = getIntent().getIntExtra("postId", 1);
        String url = "https://jsonplaceholder.typicode.com/posts/" + postId;

        cargarDetalle(url);

    }

    private void cargarDetalle(String url) {
        JsonObjectRequest jasonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
            response -> {
            try {
                String titulo1 = response.getString("title");
                String contenido1 = response.getString("body");
                titulo.setText("Titulo: "+ titulo1);
                contenido.setText("Contenido: "+ contenido1);
            } catch (Exception e){
                e.printStackTrace();
            }
            },
                error -> {
                    error.printStackTrace();
                }
        );

        requestQueue.add(jasonObjectRequest);
    }
}