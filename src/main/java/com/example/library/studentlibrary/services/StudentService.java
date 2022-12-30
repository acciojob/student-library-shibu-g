package com.example.library.studentlibrary.services;

import com.example.library.studentlibrary.models.Card;
import com.example.library.studentlibrary.models.Student;
import com.example.library.studentlibrary.repositories.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {


    @Autowired
    CardService cardService4;

    @Autowired
    StudentRepository studentRepository4;

    public Student getDetailsByEmail(String email){
        Student student = studentRepository4.findByEmailId(email);

        return student;
    }

    public Student getDetailsById(int id){
        Student student = studentRepository4.getOne(id);

        return student;
    }

    public void createStudent(Student student){
    	cardService4.createAndReturn(student);
    	studentRepository4.save(student);

    }

    public void updateStudent(Student student){
     Student s=studentRepository4.getOne(student.getId());
     s.setAge(student.getAge());
     s.setCard(student.getCard());
     s.setCountry(student.getCountry());
     s.setCreatedOn(student.getCreatedOn());
     s.setEmailId(student.getEmailId());
     s.setId(student.getId());
     s.setName(student.getName());
     s.setUpdatedOn(student.getUpdatedOn());
     studentRepository4.save(s);
    }

    public void deleteStudent(int id){
        //Delete student and deactivate corresponding card
    	cardService4.equals(id);
    	studentRepository4.deleteById(id);
    }
}