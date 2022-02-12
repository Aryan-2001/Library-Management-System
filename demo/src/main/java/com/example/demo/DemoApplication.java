
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class DemoApplication  {

    @Autowired
    private BookRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/hello")
    public String begin(){
        return("\nLets Begin\n");
    }

    @GetMapping("/Initialise") //Intitialise the library with 3 books
    public String populateLib(){
        repository.save(new Book("Alice_in_wonderland"));
        repository.save(new Book("Twiglight Saga"));
        repository.save(new Book("Diary_of_the_wimpy_kid"));
        return("\n Added 3 books to the library \n");
    }

    @GetMapping("/ListAllBooks") //Api Call to list all books in the library
    public String listAllBooks(){
        String s = "";
        for(Book book : repository.findAll()){
            s += book.bookName+"\n";
        };
        return(s);
    }

    @GetMapping("/issueBook/{bookName}") //Api call to issue a book
    public String issueBook(@PathVariable String bookName){

        System.out.println(bookName);
        Book temp = repository.findByBookName(bookName);

        if(temp==null){
            return("\n\n\nNo book : "+bookName+" found!!Please check for another book which is available\n\n\n");
        }

        if(temp.nLended== temp.nCopies){
            return("\n\n\n All the copies of the book you need are already checked out\nPls visit when someone returns the book\n\n");
        }

        temp.nLended+=1;
        repository.save(temp);
        return("\n\nBoook has been issued : Have a nice day\n\n"+temp.toString());
    }

    @GetMapping("/addBook/{bookName}") //Api call to add a book
    public String addBook(@PathVariable String bookName){
        Book t = repository.findByBookName(bookName);
        if(t!=null){
            t.nCopies+=1;
            repository.save(t);
            return("\n\nBook has been added thanks for the donation\n\n"+t.toString());
        }
        t = new Book(bookName);
        repository.save(t);
        return("\n\nBook has been added thanks for the donation\n\n"+t.toString());
    }

    @GetMapping("/returnBook/{bookName}") //Api call to return an issued book
    public String returnBook(@PathVariable String bookName){

        Book t = repository.findByBookName(bookName);
        if(t==null){
            return("\n\nNo Such ever Existed in the library");
        }
        if(t.nLended==0){
            return("\n\nAll the copies are already present this book is not of this library");
        }
        t.nLended-=1;
        repository.save(t);
        return("Thanks For Returning the book , have a nice day\n\n" +t.toString());


    }

    @GetMapping("/book/{bookName}") //Api call to get details of a book
    public String book(@PathVariable String bookName){
        Book t = repository.findByBookName(bookName);
        if(t==null){
            return("\n\nNo Such Book in the library\n\n");
        }
        return("\n\n"+t.toString()+"\n\n");
    }

    @GetMapping("/removeBook/{bookName}") //Api call to remove a book from lms
    public String removeBook(@PathVariable String bookName){
        Book t = repository.findByBookName(bookName);
        if(t==null){
            return("\nNo Such book exists to remove\n");
        }
        repository.delete(t);
        return("\nBook Has been removed\n");

    }

}