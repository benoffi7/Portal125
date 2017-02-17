package com.coffeeandcookies.portal125;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Delete;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gonzalo on 03/11/2016.
 */

public class frag_feed extends Fragment
{

    private View view;
    ProgressDialog progressDialog;
    RecyclerView rv_post;

    public frag_feed() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_feed, container, false);
        findViews();
        getPost();
        return view;
    }

    void findViews()
    {
        rv_post = (RecyclerView)view.findViewById(R.id.rv_post);
        rv_post.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    void getPost()
    {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Descargando noticias");
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url.getPost, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {
                if (progressDialog!=null) if (progressDialog.isShowing()) progressDialog.dismiss();
                List<Post> posts = new ArrayList<>();
                new Delete().from(Post.class).execute();
                for (int i=0;i<response.length();i++)
                {
                    try
                    {
                        Post post = new Post();
                        JSONObject jsonObject = response.getJSONObject(i);
                        post.setDate(jsonObject.getString("date_gmt"));
                        post.setContent(jsonObject.getJSONObject("content").getString("rendered"));
                        post.setTitle(jsonObject.getJSONObject("title").getString("rendered"));
                        post.setLink(jsonObject.getString("link"));
                        post.save();
                        posts.add(post);

                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
                setAdapter(posts);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                if (progressDialog!=null) if (progressDialog.isShowing()) progressDialog.dismiss();
            }
        });
        queue.add(jsonArrayRequest);
    }

    void setAdapter(List<Post> posts)
    {
        PostsAdapter postsAdapter = new PostsAdapter(posts,null,getActivity());
        rv_post.setAdapter(postsAdapter);
    }


}