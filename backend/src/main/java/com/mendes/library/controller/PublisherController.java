package com.mendes.library.controller;

import com.mendes.library.model.DTO.PublisherDTO.PublisherRequest;
import com.mendes.library.model.DTO.PublisherDTO.PublisherResponse;
import com.mendes.library.model.Publisher;
import com.mendes.library.service.PublisherService;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/v1/publishers")
@Slf4j
public class PublisherController {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PreAuthorize("hasAnyAuthority('PUBLISHER_VIEW', 'ADMIN')")
    @GetMapping("/find/all")
    public Page<PublisherResponse> findAllPublishers(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        var page = publisherService.findAllPublishers(pageable);

        var content = page.getContent()
                .stream()
                .map(publisher -> publisherService.convertEntityToDto(publisher))
                .collect(Collectors.toList());

        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    @PreAuthorize("hasAnyAuthority('PUBLISHER_VIEW', 'ADMIN')")
    @GetMapping("/find/{id}")
    public PublisherResponse findById(@PathVariable Long id) {
        log.info("Consultando a editora (ID = {})", id);
        Publisher publisher = publisherService.findById(id);
        return publisherService.convertEntityToDto(publisher);
    }

    @PreAuthorize("hasAnyAuthority('PUBLISHER_INSERT', 'ADMIN')")
    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public PublisherResponse insertPublisher(@Valid @RequestBody PublisherRequest publisherRequest) {
        log.info("Cadastrando nova editora: {} ", publisherRequest.getName());
        Publisher publisherEntity = publisherService.convertDtoToEntity(publisherRequest);
        var publisher = publisherService.insertPublisher(publisherEntity);
        log.info("Editora cadastrada com sucesso (ID = {})", publisher.getId());
        return publisherService.convertEntityToDto(publisher);
    }

    @PreAuthorize("hasAnyAuthority('PUBLISHER_UPDATE', 'ADMIN')")
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PublisherResponse updatePublisher(@Valid @RequestBody PublisherRequest publisherRequest, @PathVariable Long id) {
        log.info("Atualizando a editora: {} ", id);
        Publisher publisherEntity = publisherService.convertDtoToEntity(publisherRequest);
        var publisher = publisherService.updatePublisher(id, publisherEntity);
        log.info("Editora atualizada com sucesso (ID = {})", id);
        return publisherService.convertEntityToDto(publisher);
    }

    @PreAuthorize("hasAnyAuthority('PUBLISHER_DELETE', 'ADMIN')")
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePublisher(@PathVariable Long id) {
        log.info("Deletando editora (ID = {})", id);
        publisherService.deletePublisher(id);
        log.info("Editora deletada com sucesso (ID = {})", id);
    }
}
