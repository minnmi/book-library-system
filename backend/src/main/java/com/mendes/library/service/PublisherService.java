package com.mendes.library.service;

import com.mendes.library.model.DTO.PublisherDTO.PublisherRequest;
import com.mendes.library.model.DTO.PublisherDTO.PublisherResponse;
import com.mendes.library.model.Publisher;
import com.mendes.library.repository.PublisherRepository;
import com.mendes.library.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository, ModelMapper modelMapper) {
        this.publisherRepository = publisherRepository;
        this.modelMapper = modelMapper;
    }

    public Page<Publisher> findAllPublishers(Pageable pageable) {
        return publisherRepository.findAll(pageable);
    }

    public Publisher findById(Long id) {
        Optional<Publisher> optionalPublisher = publisherRepository.findById(id);
        if (optionalPublisher.isPresent()) {
            return optionalPublisher.get();
        } else {
            throw new ObjectNotFoundException("Object not found: " + id + " type " + Publisher.class.getName());
        }
    }

    public Publisher insertPublisher(Publisher publisher) {
        publisher.setId(null);
        return publisherRepository.save(publisher);
    }

    public Publisher updatePublisher(Long id, Publisher publisher) {
        if (publisher == null) {
            throw new IllegalArgumentException("Publisher can't be null.");
        }
        Publisher currentPublisher = findById(id);
        toUpdatePublisher(currentPublisher, publisher);
        return publisherRepository.save(currentPublisher);
    }

    public void deletePublisher(Long id) {
        findById(id);
        if (id == null) {
            throw new IllegalArgumentException("Publisher can't be null");
        }
        this.publisherRepository.deleteById(id);
    }

    /**
     * Update object with new informations
     *
     * @param currentPublisher
     * @param publisher
     */

    private void toUpdatePublisher(Publisher currentPublisher, Publisher publisher) {
        currentPublisher.setName(publisher.getName());
    }

    public Publisher convertDtoToEntity(PublisherRequest publisherResquest) {
        return modelMapper.map(publisherResquest, Publisher.class);
    }

    public PublisherResponse convertEntityToDto(Publisher publisher) {
        return modelMapper.map(publisher, PublisherResponse.class);
    }
}
