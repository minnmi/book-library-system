package com.mendes.library.service;

import com.mendes.library.model.DTO.PublisherDTO;
import com.mendes.library.model.Publisher;
import com.mendes.library.repository.PublisherRepository;
import com.mendes.library.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Publisher> findAllPublishers() {
        return publisherRepository.findAll();
    }

    public Publisher findById(Long id) {
        Optional<Publisher> publisher = publisherRepository.findById(id);
        if (publisher.isPresent()) {
            return publisher.get();
        } else {
            throw new ObjectNotFoundException("Object not found: " + id + " type " + Publisher.class.getName());
        }
    }

    public Publisher insertPublisher(Publisher object) {
        object.setId(null);

        return publisherRepository.save(object);
    }

    public Publisher updatePublisher(Long id, Publisher object) {
        if (object == null || object.getId() == null) {
            throw new IllegalArgumentException("Publisher can't be null.");
        }
        Publisher newObject = findById(id);
        toUpdatePublisher(newObject, object);
        return publisherRepository.save(newObject);
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
     * @param newObject
     * @param object
     */

    private void toUpdatePublisher(Publisher newObject, Publisher object) {
        newObject.setName(object.getName());
    }

    public Publisher convertDtoToEntity(PublisherDTO objectDTO) {
        return modelMapper.map(objectDTO, Publisher.class);
    }

    public PublisherDTO convertEntityToDto(Publisher object) {
        return modelMapper.map(object, PublisherDTO.class);
    }
}
