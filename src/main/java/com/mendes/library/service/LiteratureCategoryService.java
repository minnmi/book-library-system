package com.mendes.library.service;

import com.mendes.library.model.DTO.LiteratureCategoryDTO.LiteratureCategoryDTO;
import com.mendes.library.model.LiteratureCategory;
import com.mendes.library.repository.LiteratureCategoryRepository;
import com.mendes.library.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LiteratureCategoryService {

    private final LiteratureCategoryRepository literatureCategoryRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public LiteratureCategoryService(LiteratureCategoryRepository literatureCategoryRepository, ModelMapper modelMapper) {
        this.literatureCategoryRepository = literatureCategoryRepository;
        this.modelMapper = modelMapper;
    }

    public List<LiteratureCategory> findAllLiteratureCategories() {
        return literatureCategoryRepository.findAll();
    }

    public LiteratureCategory findById(Long id) {
        Optional<LiteratureCategory> literatureCategory = literatureCategoryRepository.findById(id);
        if (literatureCategory.isPresent()) {
            return literatureCategory.get();
        } else {
            throw new ObjectNotFoundException("Object not found: " + id + " type " + LiteratureCategory.class.getName());
        }
    }

    public LiteratureCategory insertLiteratureCategory(LiteratureCategory object) {
        object.setId(null);
        return literatureCategoryRepository.save(object);
    }

    public LiteratureCategory updateLiteratureCategory(Long id, LiteratureCategory object) {
        if (object == null || object.getId() == null) {
            throw new IllegalArgumentException("Literature category can't be null");
        }
        LiteratureCategory newObject = findById(id);
        toUpdateLiteratureCategory(newObject, object);
        return literatureCategoryRepository.save(newObject);
    }

    public void deleteLiteratureCategory(Long id) {
        findById(id);
        if (id == null) {
            throw new IllegalArgumentException("Literature category can't be null");
        }
        this.literatureCategoryRepository.deleteById(id);
    }

    /**
     * Update object with new informations
     *
     * @param newObject
     * @param object
     */

    private void toUpdateLiteratureCategory(LiteratureCategory newObject, LiteratureCategory object) {
        newObject.setCategoryName(object.getCategoryName());
    }

    public LiteratureCategory convertDtoToEntity(LiteratureCategoryDTO objectDTO) {
        return modelMapper.map(objectDTO, LiteratureCategory.class);
    }

    public LiteratureCategoryDTO convertEntityToDto(LiteratureCategory object) {
        return modelMapper.map(object, LiteratureCategoryDTO.class);
    }
}
