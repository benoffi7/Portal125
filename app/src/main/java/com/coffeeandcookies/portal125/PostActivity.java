package com.coffeeandcookies.portal125;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.activeandroid.query.Select;

public class PostActivity extends AppCompatActivity
{
    long id;
    Post post;
    TextView title;
    TextView content;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        id = getIntent().getExtras().getLong("id");
        post = new Select().from(Post.class).where("Id = ?",id).executeSingle();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViews();
        setUI();
    }

    void findViews()
    {
        title = (TextView)findViewById(R.id.title);
        content = (TextView)findViewById(R.id.content);
    }

    void  setUI()
    {
        title.setText(Html.fromHtml(post.getTitle()));
        content.setText(Html.fromHtml(post.getContent()));
    }
}
