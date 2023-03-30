package com.mendes.library.controller;

import com.mendes.library.model.Author;
import com.mendes.library.model.DTO.AuthorDTO.AuthorRequest;
import com.mendes.library.model.DTO.AuthorDTO.AuthorResponse;
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
@RequestMapping("/v1/authors")
@Slf4j
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PreAuthorize("hasAnyAuthority('AUTHOR_VIEW', 'ADMIN')")
    @GetMapping("/find/all")
    public Page<AuthorResponse> findAllAuthors(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("Consultando todos autores");
        var page = authorService.findAllAuthors(pageable);
        var content = page.getContent()
                .stream()
                .map(authorService::convertEntityToDto)
                .toList();

        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }


    @PreAuthorize("hasAnyAuthority('AUTHOR_VIEW', 'ADMIN')")
    @GetMapping("/find/{id}")
    public AuthorResponse findById(@PathVariable Long id) {
        log.info("Consultando autor (ID = {})", id);
        var author = authorService.findById(id);
        return authorService.convertEntityToDto(author);
    }

    @PreAuthorize("hasAnyAuthority('AUTHOR_INSERT', 'ADMIN')")
    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorResponse insertAuthor(@Valid @RequestBody AuthorRequest authorRequest) {
        log.info("Cadastrando novo autor (Nome = {})", authorRequest.getName());
        var authorEntity = authorService.convertDtoToEntity(authorRequest);
        var author = authorService.insertAuthor(authorEntity);
        log.info("Autor cadastrado com sucesso (ID = {})", author.getId());
        return authorService.convertEntityToDto(author);
    }


    @PreAuthorize("hasAnyAuthority('AUTHOR_UPDATE', 'ADMIN')")
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorResponse updateAuthor(@Valid @RequestBody AuthorRequest authorRequest, @PathVariable Long id) {
        log.info("Atualizando autor (ID = {})", id);
        var authorEntity = authorService.convertDtoToEntity(authorRequest);
        var author = authorService.updateAuthor(id, authorEntity);
        log.info("Autor atualizado com sucesso (ID = {})", id);
        return authorService.convertEntityToDto(author);
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
