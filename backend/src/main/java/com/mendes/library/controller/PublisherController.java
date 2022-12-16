package com.mendes.library.controller;

import com.mendes.library.model.DTO.PublisherDTO.PublisherDTO;
import com.mendes.library.model.Publisher;
import com.mendes.library.service.PublisherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/publishers")
@Slf4j
public class PublisherController {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("/find/all")
    public List<PublisherDTO> findAllPublishers() {
        return publisherService.findAllPublishers()
                .stream()
                .map(object -> publisherService.convertEntityToDto(object))
                .collect(Collectors.toList());
    }

    @GetMapping("/find/{id}")
    public PublisherDTO findById(@PathVariable Long id) {
        Publisher object = publisherService.findById(id);
        return publisherService.convertEntityToDto(object);
    }

    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public PublisherDTO insertPublisher(@Valid @RequestBody PublisherDTO objectDTO) {
        log.info(" inserting a new publisher: {} ", objectDTO.getName());
        Publisher objectRequest = publisherService.convertDtoToEntity(objectDTO);
        Publisher object = publisherService.insertPublisher(objectRequest);
        return publisherService.convertEntityToDto(object);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PublisherDTO updatePublisher(@Valid @RequestBody PublisherDTO objectDTO, @PathVariable Long id) {
        log.info(" updating publisher of id: {} ", id);
        Publisher objectRequest = publisherService.convertDtoToEntity(objectDTO);
        Publisher object = publisherService.updatePublisher(id, objectRequest);
        return publisherService.convertEntityToDto(object);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAuthor(@PathVariable Long id) {
        publisherService.deletePublisher(id);
    }
}
