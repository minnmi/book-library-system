package com.mendes.library.controller;

import com.mendes.library.model.DTO.LiteratureCategoryDTO.LiteratureCategoryDTO;
import com.mendes.library.model.LiteratureCategory;
import com.mendes.library.service.LiteratureCategoryService;
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
@RequestMapping("/literatureCategories")
@Slf4j
public class LiteratureCategoryController {

    private final LiteratureCategoryService literatureCategoryService;

    @Autowired
    public LiteratureCategoryController(LiteratureCategoryService literatureCategoryService) {
        this.literatureCategoryService = literatureCategoryService;
    }
    @PreAuthorize("hasAnyAuthority('LITERATURE_CATEGORY_VIEW', 'ADMIN')")
    @GetMapping("/find/all")
    public Page<LiteratureCategoryDTO> findAllLiteratureCategories(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        var page = literatureCategoryService.findAllLiteratureCategories(pageable);
        var content = page.getContent()
                .stream()
                .map(object -> literatureCategoryService.convertEntityToDto(object))
                .collect(Collectors.toList());

        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    @PreAuthorize("hasAnyAuthority('LITERATURE_CATEGORY_VIEW', 'ADMIN')")
    @GetMapping("/find/{id}")
    public LiteratureCategoryDTO findById(@PathVariable Long id) {
        LiteratureCategory object = literatureCategoryService.findById(id);
        return literatureCategoryService.convertEntityToDto(object);
    }

    @PreAuthorize("hasAnyAuthority('LITERATURE_CATEGORY_INSERT', 'ADMIN')")
    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public LiteratureCategoryDTO insertLiteratureCategory(@Valid @RequestBody LiteratureCategoryDTO objectDTO) {
        log.info(" inserting a new literature cateogory: {} ", objectDTO.getCategoryName());
        LiteratureCategory objectRequest = literatureCategoryService.convertDtoToEntity(objectDTO);
        LiteratureCategory object = literatureCategoryService.insertLiteratureCategory(objectRequest);
        return literatureCategoryService.convertEntityToDto(object);
    }

    @PreAuthorize("hasAnyAuthority('LITERATURE_CATEGORY_UPDATE', 'ADMIN')")
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LiteratureCategoryDTO updateLiteratureCategory(@Valid @RequestBody LiteratureCategoryDTO objectDTO, @PathVariable Long id) {
        log.info(" updating literature category of id: {} ", id);
        LiteratureCategory objectRequest = literatureCategoryService.convertDtoToEntity(objectDTO);
        LiteratureCategory object = literatureCategoryService.updateLiteratureCategory(id, objectRequest);
        return literatureCategoryService.convertEntityToDto(object);
    }

    @PreAuthorize("hasAnyAuthority('LITERATURE_CATEGORY_DELETE', 'ADMIN')")
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLiteratureCategory(@PathVariable Long id) {
        literatureCategoryService.deleteLiteratureCategory(id);
    }
}
