package com.pupsiki.digitallibrary.models;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Book {

    @OneToMany(mappedBy = "book")
    Set<Deal> deals;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private String image;
    private String author;
    private String publisher;
    private String genre;
    @Column(length = 8192)
    private String description;
    private int year;
    private float price;
    private float rating;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;


    public Book() {
    }

    public Book(String title, String image, String author, String publisher, String genre, String description, int year, float price, float rating) {
        this.title = title;
        this.image = image;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
        this.description = description;
        this.year = year;
        this.price = price;
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
