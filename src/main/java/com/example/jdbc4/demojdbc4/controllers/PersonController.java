package com.example.jdbc4.demojdbc4.controllers;

import com.example.jdbc4.demojdbc4.DAOs.Person;
import com.example.jdbc4.demojdbc4.DBManager.DBOperations;
import com.example.jdbc4.demojdbc4.request.CreateRequest;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class PersonController {
    @GetMapping("/getPersons")
    public List<Person>  listOfPerson() throws SQLException {
        return DBOperations.getPersons();
    }

    @PostMapping("/createTable")
    public  void createTable(@RequestParam(value="name") String name) throws SQLException {
        DBOperations.createTable(name);
    }
    @PostMapping("/insertperson")
    public void insertPerson(@RequestBody CreateRequest createRequest) throws SQLException {
        DBOperations.insertPerson(createRequest);
    }

}
