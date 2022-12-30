package com.example.library.studentlibrary.controller;

import com.example.library.studentlibrary.models.Author;
import com.example.library.studentlibrary.models.Book;
import com.example.library.studentlibrary.models.Genre;
import com.example.library.studentlibrary.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Add required annotations
@RequestMapping("/book")
public class BookController {


    //Write createBook API with required annotations
	@Autowired
	BookService service;
	
	@PostMapping("/")
public ResponseEntity<String> createBook(@RequestBody Book book){
		service.createBook(book);
		
		return new ResponseEntity(book.toString()+"Success",HttpStatus.CREATED);
	}

    //Add required annotations
	@GetMapping("/")
    public ResponseEntity< List<Book>> getBooks(@RequestParam(value = "genre", required = false) String genre,
                                   @RequestParam(value = "available", required = false, defaultValue = "false") boolean available,
                                   @RequestParam(value = "author", required = false) String author){

        List<Book> bookList = service.getBooks(genre, available, author); //find the elements of the list by yourself

        return new ResponseEntity<>(bookList, HttpStatus.OK);

    }
}
