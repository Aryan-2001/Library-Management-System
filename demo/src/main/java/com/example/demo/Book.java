package com.example.demo;

import org.springframework.data.annotation.Id;

public class Book {
    @Id
    public String id; //mongo db id
    public String bookName; // name of the book
    public String authorName;//name of the author
    public int nCopies;//number of copies
    public int nLended;//Number of copies Lended

    public Book() {}

    public Book(String bname, String aname, int n){
        this.bookName = bname;
        this.authorName = aname;
        this.nCopies = n;
        this.nLended = 0;
    }

    public Book(String bname){
        this.bookName = bname;
        this.authorName = "NA";
        this.nCopies = 1;
        this.nLended = 0;
    }

    public Book(String bname, int n){
        this.bookName = bname;
        this.authorName = "NA";
        this.nCopies = n;
        this.nLended=0;
    }

    public String toString(){
        return("Book name "+this.bookName + "\nAuthor= "+this.authorName +"\nThe number of copies are = "+this.nCopies + "\nAnd number of lended are:"+this.nLended);
    }
}
