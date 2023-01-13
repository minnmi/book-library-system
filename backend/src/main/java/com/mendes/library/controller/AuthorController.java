package com.mendes.library.controller;

import com.mendes.library.model.Author;
import com.mendes.library.model.DTO.AuthorDTO.AuthorDTO;
import com.mendes.library.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/find/all")
    public List<AuthorDTO> findAllAuthors() {
        logger.info("Consultando todos autores");
        return authorService.findAllAuthors()
                .stream()
                .map(object -> authorService.convertEntityToDto(object))
                .collect(Collectors.toList());
    }


    @GetMapping("/find/{id}")
    public AuthorDTO findById(@PathVariable Long id) {
        logger.info("Consultando autor (ID = {})", id);
        Author object = authorService.findById(id);
        return authorService.convertEntityToDto(object);
    }

    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO insertAuthor(@Valid @RequestBody AuthorDTO objectDTO) {
        log.info("Cadastrando novo autor (Nome = {})", objectDTO.getName());
        Author objectRequest = authorService.convertDtoToEntity(objectDTO);
        Author object = authorService.insertAuthor(objectRequest);
        log.info("Autor cadastrado com sucesso (ID = {})", object.getId());
        return authorService.convertEntityToDto(object);
    }


    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDTO updateAuthor(@Valid @RequestBody AuthorDTO objectDTO, @PathVariable Long id) {
        log.info("Atualizando autor (ID = {})", id);
        Author objectRequest = authorService.convertDtoToEntity(objectDTO);
        Author object = authorService.updateAuthor(id, objectRequest);
        log.info("Autor atualizado com sucesso (ID = {})", id);
        return authorService.convertEntityToDto(object);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAuthor(@PathVariable Long id) {
        log.info("Deletando autor (ID = {})", id);
        authorService.deleteAuthor(id);
        log.info("Autor deletado com sucesso (ID = {})", id);
    }


}
