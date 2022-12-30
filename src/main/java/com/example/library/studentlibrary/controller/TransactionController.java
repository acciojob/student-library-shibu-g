package com.example.library.studentlibrary.controller;

import com.example.library.studentlibrary.models.Transaction;
import com.example.library.studentlibrary.services.CardService;
import com.example.library.studentlibrary.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Add required annotations
@RequestMapping("/transation")
public class TransactionController {

    //Add required annotations
	@Autowired
	TransactionService service;
	
	@PostMapping("/issueBook")
    public ResponseEntity<String> issueBook(@RequestParam("cardId") int cardId, @RequestParam("bookId") int bookId) throws Exception{
        String a= service.issueBook(cardId, bookId);
       return new ResponseEntity<>("transaction id : "+a+"\ntransaction completed", HttpStatus.ACCEPTED);
    }

    //Add required annotations
	@PostMapping("/returnBook")
    public ResponseEntity<String> returnBook(@RequestParam("cardId") int cardId, @RequestParam("bookId") int bookId) throws Exception{
       Transaction a=service.returnBook(cardId, bookId);
        return new ResponseEntity<>("transaction completed", HttpStatus.ACCEPTED);
    }
}
