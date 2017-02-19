package com.coffeeandcookies.portal125;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Gonzalo on 18/02/2017.
 */

@Table(name = "Favorito")
public class Favorito extends Model
{
    @Column(name = "Post",onDelete = Column.ForeignKeyAction.SET_NULL)
    Post post;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
