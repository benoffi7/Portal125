package com.coffeeandcookies.portal125;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Gonzalo on 17/02/2017.
 */

@Table(name = "post")
public class Post extends Model
{
    @Column (name = "date")
    String date;
    @Column (name = "link")
    String link;
    @Column (name = "title")
    String title;
    @Column (name = "content")
    String content;
    @Column (name = "author")
    String author;
    @Column (name = "tags")
    String tags;
    @Column (name = "categories")
    String categories;

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
