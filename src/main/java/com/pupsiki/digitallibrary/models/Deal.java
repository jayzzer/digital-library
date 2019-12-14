package com.pupsiki.digitallibrary.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Deal")
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date deal_date;

    public Deal() {
    }

    public Deal(User user, Book book){
        this.book = book;
        this.user = user;
    }

    public Date getDeal_date() {
        return deal_date;
    }

    public void setDeal_date(Date deal_date) {
        this.deal_date = deal_date;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Book getBook() { return book; }

    public void setBook(Book book) { this.book = book; }


}
