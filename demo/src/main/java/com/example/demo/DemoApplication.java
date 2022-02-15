package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@SpringBootApplication
@RestController

public class DemoApplication  {

    @Autowired
    private BookRepository repository;


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    /* API's */

    @GetMapping("/testing")
    private String begin(){
        return("\nLMS system online\n");
    }

    @GetMapping("/Initialise") //Initialise the library with 4 books
    private String populateLib(){
        repository.deleteAll();
        repository.save(new Book("Alice_in_wonderland"));
        repository.save(new Book("Twiglight_Saga"));
        repository.save(new Book("Diary_of_the_wimpy_kid"));
        repository.save(new Book("Hercules"));
        return("\n\n Added 4 books to the library \n\n");
    }

    @GetMapping("/ListAllBooks") //API Call to list all books in the library
    private String listAllBooks(){
        String s = "\n\n The Library has the following books :-\n";
        for(Book book : repository.findAll()){
            s += book.bookName+"\n";
        };
        return(s+"\n\n");
    }

    @GetMapping("/ListAvailable") //API Call to get the list of all book in library available for lending
    private String listAvailable(){
        String s = "\n\n The Library has the following books available for issuing :-\n";
        for(Book book : repository.findAll()){
            if(book.nCopies==book.nLended){continue;}
            s += book.bookName+"\n";
        };
        return(s+"\n\n");
    }


    @GetMapping("/issueBook/{bookName}") //API call to issue a book
    private String issueBook(@PathVariable String bookName){

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

    @PostMapping("/addBook/{bookName}") //API call to add a book
    private String addBook(@PathVariable String bookName){
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

    @PostMapping("/returnBook/{bookName}") //API call to return an issued book
    private String returnBook(@PathVariable String bookName){

        Book t = repository.findByBookName(bookName);
        if(t==null){
            return("\n\nNo Such ever Existed in the library\n\n");
        }
        if(t.nLended==0){
            return("\n\nAll the copies are already present this book is not of this library\n\n");
        }
        t.nLended-=1;
        repository.save(t);
        return("Thanks For Returning the book , have a nice day\n\n" +t.toString());


    }

    @GetMapping("/book/{bookName}") //API call to get details of a book
    private String book(@PathVariable String bookName){
        Book t = repository.findByBookName(bookName);
        if(t==null){
            return("\n\nNo Such Book in the library\n\n");
        }
        return("\n\n"+t.toString()+"\n\n");
    }

    @DeleteMapping("/removeBook/{bookName}") //Api call to remove a book from LMS
    private String removeBook(@PathVariable String bookName){
        Book t = repository.findByBookName(bookName);
        if(t==null){
            return("\n\nNo Such book exists to remove\n\n");
        }
        repository.delete(t);
        return("\n\nBook Has been removed\n\n");

    }

    @DeleteMapping("/emptyLibrary") //API call to remove all books from LMS
    private String emptyLib(){
        repository.deleteAll();
        return("\n\nLibrary is now empty\n\n");
    }

}