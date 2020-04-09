package com.ajeetMishra.flashchatnewfirebase;

public class Instantmesseges {
     public String messege;
     public String author;
    public Instantmesseges() {

    }

    public Instantmesseges(String messege , String author) {
        this.messege = messege;
        this.author = author;
    }

    public String getMessege() {
        return messege;
    }

    public String getAuthor() {
        return author;
    }
}
