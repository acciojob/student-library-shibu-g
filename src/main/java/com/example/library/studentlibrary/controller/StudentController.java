package com.example.library.studentlibrary.controller;

import com.example.library.studentlibrary.models.Student;
import com.example.library.studentlibrary.services.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Add required annotations
@RequestMapping("/student")
public class StudentController {

    //Add required annotations
	
	@Autowired
	StudentService service;
	
	@GetMapping("/studentByEmail")
    public ResponseEntity<String> getStudentByEmail(@RequestParam("email") String email){
    	Student s=service.getDetailsByEmail(email);
    	//s.toString();
        return new ResponseEntity<>(s.toString(), HttpStatus.OK);
        //+"\nStudent details printed successfully "
    }

    //Add required annotations
	@GetMapping("/studentById")
    public ResponseEntity<String> getStudentById(@RequestParam("id") int id){
    	Student s=service.getDetailsById(id);

        return new ResponseEntity<>(s.toString(), HttpStatus.OK);
    }

    //Add required annotations
    @PostMapping("/")
    public ResponseEntity<String> createStudent(@RequestBody Student student){
       
    	service.createStudent(student);
    	
        return new ResponseEntity<>("the student is successfully added to the system", HttpStatus.CREATED);
    }

    //Add required annotations
    @PutMapping("/")
    public ResponseEntity<String> updateStudent(@RequestBody Student student){

        return new ResponseEntity<>("student is updated", HttpStatus.ACCEPTED);
    }

    //Add required annotations
    @DeleteMapping("/")
    public ResponseEntity<String> deleteStudent(@RequestParam("id") int id){
    	service.deleteStudent(id);

        return new ResponseEntity<>("student is deleted", HttpStatus.ACCEPTED);
    }

}
