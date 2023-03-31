package com.mendes.library.service;

import com.mendes.library.model.DTO.LiteratureCategoryDTO.LiteratureCategoryRequest;
import com.mendes.library.model.DTO.LiteratureCategoryDTO.LiteratureCategoryResponse;
import com.mendes.library.model.LiteratureCategory;
import com.mendes.library.repository.LiteratureCategoryRepository;
import com.mendes.library.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LiteratureCategoryService {

    private final LiteratureCategoryRepository literatureCategoryRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public LiteratureCategoryService(LiteratureCategoryRepository literatureCategoryRepository, ModelMapper modelMapper) {
        this.literatureCategoryRepository = literatureCategoryRepository;
        this.modelMapper = modelMapper;
    }

    public Page<LiteratureCategory> findAllLiteratureCategories(Pageable pageable) {
        return literatureCategoryRepository.findAll(pageable);
    }

    public LiteratureCategory findById(Long id) {
        return literatureCategoryRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object not found: " + id + " type " + LiteratureCategory.class.getName()));
    }

    public LiteratureCategory insertLiteratureCategory(LiteratureCategory literatureCategory) {
        literatureCategory.setId(null);
        return literatureCategoryRepository.save(literatureCategory);
    }

    public LiteratureCategory updateLiteratureCategory(Long id, LiteratureCategory literatureCategory) {
        if (literatureCategory == null) {
            throw new IllegalArgumentException("Literature category can't be null");
        }
        LiteratureCategory currentLiteratureCategory = findById(id);
        toUpdateLiteratureCategory(currentLiteratureCategory, literatureCategory);
        return literatureCategoryRepository.save(currentLiteratureCategory);
    }

    private void toUpdateLiteratureCategory(LiteratureCategory currentLiteratureCategory, LiteratureCategory literatureCategory) {
        currentLiteratureCategory.setCategoryName(literatureCategory.getCategoryName());
    }

    public void deleteLiteratureCategory(Long id) {
        findById(id);
        if (id == null) {
            throw new IllegalArgumentException("Literature category can't be null");
        }
        this.literatureCategoryRepository.deleteById(id);
    }

    public LiteratureCategory convertDtoToEntity(LiteratureCategoryRequest literatureCategoryRequest) {
        return modelMapper.map(literatureCategoryRequest, LiteratureCategory.class);
    }

    public LiteratureCategoryResponse convertEntityToDto(LiteratureCategory literatureCategory) {
        return modelMapper.map(literatureCategory, LiteratureCategoryResponse.class);
    }
}
