package com.mendes.library.controller;

import com.mendes.library.model.Author;
import com.mendes.library.model.DTO.AuthorDTO.AuthorDTO;
import com.mendes.library.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
@Slf4j
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/find/all")
    public List<AuthorDTO> findAllAuthors() {
        return authorService.findAllAuthors()
                .stream()
                .map(object -> authorService.convertEntityToDto(object))
                .collect(Collectors.toList());
    }


    @GetMapping("/find/{id}")
    public AuthorDTO findById(@PathVariable Long id) {
        Author object = authorService.findById(id);
        return authorService.convertEntityToDto(object);
    }

    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO insertAuthor(@Valid @RequestBody AuthorDTO objectDTO) {
        log.info(" inserting a new author: {} ", objectDTO.getName());
        Author objectRequest = authorService.convertDtoToEntity(objectDTO);
        Author object = authorService.insertAuthor(objectRequest);
        return authorService.convertEntityToDto(object);
    }


    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDTO updateAuthor(@Valid @RequestBody AuthorDTO objectDTO, @PathVariable Long id) {
        log.info(" updating author of id: {} ", id);
        Author objectRequest = authorService.convertDtoToEntity(objectDTO);
        Author object = authorService.updateAuthor(id, objectRequest);
        return authorService.convertEntityToDto(object);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }


}
