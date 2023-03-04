package com.mendes.library.controller;

import com.mendes.library.model.Author;
import com.mendes.library.model.DTO.AuthorDTO.AuthorDTO;
import com.mendes.library.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PreAuthorize("hasAnyAuthority('AUTHOR_VIEW', 'ADMIN')")
    @GetMapping("/find/all")
    public Page<AuthorDTO> findAllAuthors(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        logger.info("Consultando todos autores");
        var page = authorService.findAllAuthors(pageable);
        var content = page.getContent()
                .stream()
                .map(object -> authorService.convertEntityToDto(object))
                .collect(Collectors.toList());

        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }


    @PreAuthorize("hasAnyAuthority('AUTHOR_VIEW', 'ADMIN')")
    @GetMapping("/find/{id}")
    public AuthorDTO findById(@PathVariable Long id) {
        logger.info("Consultando autor (ID = {})", id);
        Author object = authorService.findById(id);
        return authorService.convertEntityToDto(object);
    }

    @PreAuthorize("hasAnyAuthority('AUTHOR_INSERT', 'ADMIN')")
    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDTO insertAuthor(@Valid @RequestBody AuthorDTO objectDTO) {
        log.info("Cadastrando novo autor (Nome = {})", objectDTO.getName());
        Author objectRequest = authorService.convertDtoToEntity(objectDTO);
        Author object = authorService.insertAuthor(objectRequest);
        log.info("Autor cadastrado com sucesso (ID = {})", object.getId());
        return authorService.convertEntityToDto(object);
    }


    @PreAuthorize("hasAnyAuthority('AUTHOR_UPDATE', 'ADMIN')")
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDTO updateAuthor(@Valid @RequestBody AuthorDTO objectDTO, @PathVariable Long id) {
        log.info("Atualizando autor (ID = {})", id);
        Author objectRequest = authorService.convertDtoToEntity(objectDTO);
        Author object = authorService.updateAuthor(id, objectRequest);
        log.info("Autor atualizado com sucesso (ID = {})", id);
        return authorService.convertEntityToDto(object);
    }

    @PreAuthorize("hasAnyAuthority('AUTHOR_DELETE', 'ADMIN')")
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAuthor(@PathVariable Long id) {
        log.info("Deletando autor (ID = {})", id);
        authorService.deleteAuthor(id);
        log.info("Autor deletado com sucesso (ID = {})", id);
    }


}
