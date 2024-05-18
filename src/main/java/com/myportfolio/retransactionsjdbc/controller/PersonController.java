package com.myportfolio.retransactionsjdbc.controller;

import com.myportfolio.retransactionsjdbc.model.Person;
import com.myportfolio.retransactionsjdbc.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @PostMapping("/person")
    public ResponseEntity<String> createPerson(@RequestBody Person person){
        try {
            personRepository.savePerson(new Person(person.getPerson_name(), person.getPerson_account_number(), person.getPerson_contact_info(), person.getRole(), person.getBalance()));
            return new ResponseEntity<>("Person successfully created.", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/person/update/{id}")
    public ResponseEntity<String> updatePersonById(@PathVariable("id") int personID, @RequestBody Person person){
        Person personObj = personRepository.findPersonById(personID);

        if (personObj != null) {
            personObj.setRole(person.getRole());
            personObj.setBalance(person.getBalance());
            try{
                personRepository.updatePerson(personObj);
                return new ResponseEntity<>("Person successfully updated.", HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>("Invalid SQL query: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else {
            return new ResponseEntity<>("Person with id " + personID + " not found.", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable("id") int personID){
        Person personObj = personRepository.findPersonById(personID);

        if (personObj == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(personObj, HttpStatus.OK);
        }
    }

    @GetMapping("/person/account/{accountNumber}")
    public ResponseEntity<Person> getPersonByAccountNumber(@PathVariable("accountNumber") String accountNumber){
        Person personObj = personRepository.findPersonByAccountNumber(accountNumber);

        if (personObj == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(personObj, HttpStatus.OK);
        }
    }

    @GetMapping("/person/role/{role}")
    public ResponseEntity<List<Person>> getPersonByRole(@PathVariable("role") String role){
        try {
            List<Person> persons = personRepository.findPersonByRole(role);

            if (persons.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                return new ResponseEntity<>(persons, HttpStatus.OK);
            }
        }catch (Exception e){
            System.out.println("Something went wrong: " + e.getCause());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/person/{id}")
    public ResponseEntity<String> removePersonById(@PathVariable("id") int personID){
        try {
            int response = personRepository.deletePersonById(personID);
            if (response == 0){
                return new ResponseEntity<>("No Person with id " + personID + " found.", HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>("Person with id " + personID + " successfully removed.", HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong. Cannot delete Person. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/person")
    public ResponseEntity<String> removeAllPersons(){
        try {
            int response = personRepository.deleteAll();
            if (response == 0) {
                return new ResponseEntity<>("Cannot delete. No Person has been found.", HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(response + " Persons deleted.", HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong. Cannot delete Persons. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
