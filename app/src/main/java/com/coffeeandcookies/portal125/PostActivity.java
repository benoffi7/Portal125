package com.coffeeandcookies.portal125;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.everseat.textviewlabel.TextViewLabel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity
{
    private static final String TAG = "portal125";
    long id;
    Post post;
    TextView titulo;
    TextView ccontenido;
    TextView nombre;
    NetworkImageView networkImageView;
    LinearLayout ll_autor;
    LinearLayout ll_etiquetas;
    LinearLayout ll_tags;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        id = getIntent().getExtras().getLong("id");
        post = new Select().from(Post.class).where("Id = ?",id).executeSingle();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Te envio esta noticia "+post.getLink()+" "+getResources().getString(R.string.hashtag));
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });


        FloatingActionButton fab_fav = (FloatingActionButton) findViewById(R.id.fab_fav);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Favorito favorito = new Favorito();
                favorito.setPost(post);
                favorito.save();
            }
        });
        

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViews();
        setUI();
    }

    void findViews()
    {
        titulo = (TextView)findViewById(R.id.titulo);
        ccontenido = (TextView)findViewById(R.id.contenido);
        nombre  = (TextView)findViewById(R.id.nombre);
        networkImageView = (NetworkImageView) findViewById(R.id.networkImageView);
        ll_autor = (LinearLayout)findViewById(R.id.ll_autor);
        ll_etiquetas = (LinearLayout)findViewById(R.id.ll_etiquetas);
        ll_tags = (LinearLayout)findViewById(R.id.ll_tags);
    }

    void  setUI()
    {
        titulo.setText(Html.fromHtml(post.getTitle()));
        ccontenido.setText(Html.fromHtml(post.getContent()));
        getAuthor();
        if (post.getCategories().length()>0)
        {
            String etiquetas[] = post.getCategories().split("##");
            for (int i = 0;i<etiquetas.length;i++)
            {
                Log.d(TAG, "setUI: etiquetas[i] "+etiquetas[i]);
                TextViewLabel textViewLabel = new TextViewLabel(getApplicationContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 0, 10, 0);
                textViewLabel.setLayoutParams(params);
                textViewLabel.setText(((Categoria)new Select().from(Categoria.class).where("local_id = ?",etiquetas[i]).executeSingle()).getNombre());
                textViewLabel.setLabelColor(getResources().getColor(R.color.colorAccent));
                ll_etiquetas.addView(textViewLabel);
            }
            ll_etiquetas.setVisibility(View.VISIBLE);
        }

        if (post.getTags().length()>0)
        {
            String tags[] = post.getTags().split("##");
            for (int i = 0;i<tags.length;i++)
            {
                Log.d(TAG, "setUI: tags[i] "+tags[i]);
                TextViewLabel textViewLabel = new TextViewLabel(getApplicationContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 0, 10, 0);
                textViewLabel.setLayoutParams(params);
                textViewLabel.setText(((Tag)new Select().from(Tag.class).where("local_id = ?",tags[i]).executeSingle()).getNombre());
                textViewLabel.setLabelColor(getResources().getColor(R.color.colorPrimary));
                ll_tags.addView(textViewLabel);
            }
            ll_tags.setVisibility(View.VISIBLE);
        }
    }

    void getAuthor()
    {
        RequestQueue queue = Volley.newRequestQueue(PostActivity.this);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET,post.getAuthor(), null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    ll_autor.setVisibility(View.VISIBLE);
                    Autor autor = new Autor();
                    autor.setNombre(response.getString("name"));
                    autor.setUrl(response.getJSONObject("avatar_urls").getString("96"));
                    nombre.setText(autor.getNombre());
                    ImageLoader.ImageCache imageCache = new BitmapLruCache();
                    ImageLoader imageLoader = new ImageLoader(Volley.newRequestQueue(PostActivity.this), imageCache);
                    networkImageView.setImageUrl(autor.getUrl(), imageLoader);
                    networkImageView.setDefaultImageResId(R.mipmap.ic_launcher);
                    networkImageView.setErrorImageResId(R.mipmap.ic_launcher);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        });
        queue.add(jsonArrayRequest);
    }
}
