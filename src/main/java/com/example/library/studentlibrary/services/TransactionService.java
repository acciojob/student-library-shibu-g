package com.example.library.studentlibrary.services;

import com.example.library.studentlibrary.models.*;
import com.example.library.studentlibrary.repositories.BookRepository;
import com.example.library.studentlibrary.repositories.CardRepository;
import com.example.library.studentlibrary.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {
	
	@Autowired
	CardService service;

    @Autowired
    BookRepository bookRepository5;

    @Autowired
    CardRepository cardRepository5;

    @Autowired
    TransactionRepository transactionRepository5;

    @Value("${books.max_allowed}")
    int max_allowed_books;

    @Value("${books.max_allowed_days}")
    int getMax_allowed_days;

    @Value("${books.fine.per_day}")
    int fine_per_day;

    public String issueBook(int cardId, int bookId) throws Exception {
        //check whether bookId and cardId already exist
        //conditions required for successful transaction of issue book:
        //1. book is present and available
        // If it fails: throw new Exception("Book is either unavailable or not present");
        //2. card is present and activated
        // If it fails: throw new Exception("Card is invalid");
        //3. number of books issued against the card is strictly less than max_allowed_books
        // If it fails: throw new Exception("Book limit has reached for this card");
        //If the transaction is successful, save the transaction to the list of transactions and return the id

        //Note that the error message should match exactly in all cases
    	Transaction t=new Transaction();
    	if(bookRepository5.existsById(bookId)&&cardRepository5.existsById(cardId)) {
    		Book b=bookRepository5.getOne(bookId);
    		Card c=cardRepository5.getOne(cardId);
    		if(!b.isAvailable()) {
    			throw new Exception("Book is either unavailable or not present");
    		}
    		if(c.getCardStatus().equals("ACTIVATED")) {
    			List<Book>books=c.getBooks();
    			if(books.size()>=max_allowed_books) {
    				throw new Exception("Book limit has reached for this card");
    			}else {
    				books.add(b);
    				c.setBooks(books);
    				cardRepository5.save(c);
    				t.setBook(b);
    				t.setCard(c);
    				t.setFineAmount(bookId);
    				t.setFineAmount(fine_per_day);
    				t.setTransactionStatus(TransactionStatus.SUCCESSFUL);
    				t.setIssueOperation(true);
    				transactionRepository5.save(t);
    			}
    			
    		}else{
    			throw new Exception("Card is invalid");
    		}
    	}else {
    		throw new Exception("book or card is not available");
    	}
    		
       return t.getTransactionId(); //return transactionId instead
    }

    public Transaction returnBook(int cardId, int bookId) throws Exception{

        List<Transaction> transactions = transactionRepository5.find(cardId, bookId,TransactionStatus.SUCCESSFUL, true);
        Transaction transaction = transactions.get(transactions.size() - 1);
        Transaction returnBookTransaction=new Transaction();
        Book b=bookRepository5.getOne(bookId);
		Card c=cardRepository5.getOne(cardId);
        returnBookTransaction.setBook(b);
        returnBookTransaction.setCard(c);
      returnBookTransaction.setFineAmount(transaction.getFineAmount());
        List<Book>books=c.getBooks();
        for(int i=0;i<books.size();i++) {
        	if(books.get(i).getId()==bookId) {
        		books.remove(i);
        		break;
        	}
        }
        b.setAvailable(true);
        bookRepository5.save(b);
        c.setBooks(books);
        transactionRepository5.save(returnBookTransaction);

        //for the given transaction calculate the fine amount considering the book has been returned exactly when this function is called
        //make the book available for other users
        //make a new transaction for return book which contains the fine amount as well

        
        return returnBookTransaction; //return the transaction after updating all details
    }
}