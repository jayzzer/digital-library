package com.pupsiki.digitallibrary.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BookDto {

    @NotNull
    @NotEmpty
    private String title;
    @NotNull
    @NotEmpty
    private String author;
    @NotNull
    @NotEmpty
    private String publisher;
    @NotNull
    @NotEmpty
    private String genre;
    @NotNull
    @NotEmpty
    private String description;
    @NotNull
    @Min(1)
    private int year;
    @NotNull
    @Min(0)
    private float price;


    public BookDto() {
    }

    public BookDto(String title, String author, String publisher, String genre, String description, int year, float price) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
        this.description = description;
        this.year = year;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Название: " + title +
                ", Автор: " + author + ", Издатель: " + publisher +
                ", Жанр: " + genre + ", Описание: " + description +
                ", Год: " + year + ", Цена: " + price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
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
