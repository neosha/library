package ru.library.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.library.library.entity.Author;
import ru.library.library.repository.AuthorRepository;
import ru.library.library.repository.MaintainDataBaseRepository;


import java.util.UUID;

@RestController
@RequestMapping("/author")
public class AuthorController {
    AuthorRepository authorRepository;
    MaintainDataBaseRepository maintainDataBaseRepository;

    @Autowired
    public AuthorController(AuthorRepository authorRepository, MaintainDataBaseRepository maintainDataBaseRepository) {
        this.authorRepository = authorRepository;
        this.maintainDataBaseRepository = maintainDataBaseRepository;
    }

    @GetMapping("/filldescription")
    public ResponseEntity fillDescription(){
        maintainDataBaseRepository.fillAuthorDescription();
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author){
        try{

            Author requestedAuthor = new Author();
//            requestedAuthor.setId(UUID.randomUUID());
            requestedAuthor.setAge(author.getAge());
            requestedAuthor.setName(author.getName());
            authorRepository.save(requestedAuthor);
            return new ResponseEntity<>(requestedAuthor, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
