package com.mendes.library.controller;

import com.mendes.library.model.DTO.LiteratureCategoryDTO.LiteratureCategoryRequest;
import com.mendes.library.model.DTO.LiteratureCategoryDTO.LiteratureCategoryResponse;
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
@RequestMapping("/v1/literatureCategories")
@Slf4j
public class LiteratureCategoryController {

    private final LiteratureCategoryService literatureCategoryService;

    @Autowired
    public LiteratureCategoryController(LiteratureCategoryService literatureCategoryService) {
        this.literatureCategoryService = literatureCategoryService;
    }
    @PreAuthorize("hasAnyAuthority('LITERATURE_CATEGORY_VIEW', 'ADMIN')")
    @GetMapping("/find/all")
    public Page<LiteratureCategoryResponse> findAllLiteratureCategories(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        var page = literatureCategoryService.findAllLiteratureCategories(pageable);
        var content = page.getContent()
                .stream()
                .map(object -> literatureCategoryService.convertEntityToDto(object))
                .collect(Collectors.toList());

        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    @PreAuthorize("hasAnyAuthority('LITERATURE_CATEGORY_VIEW', 'ADMIN')")
    @GetMapping("/find/{id}")
    public LiteratureCategoryResponse findById(@PathVariable Long id) {
        LiteratureCategory literatureCategory = literatureCategoryService.findById(id);
        return literatureCategoryService.convertEntityToDto(literatureCategory);
    }

    @PreAuthorize("hasAnyAuthority('LITERATURE_CATEGORY_INSERT', 'ADMIN')")
    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public LiteratureCategoryResponse insertLiteratureCategory(@Valid @RequestBody LiteratureCategoryRequest literatureCategoryRequest) {
        log.info(" inserting a new literature cateogory: {} ", literatureCategoryRequest.getCategoryName());
        LiteratureCategory literatureCategoryEntity = literatureCategoryService.convertDtoToEntity(literatureCategoryRequest);
        var literatureCategory = literatureCategoryService.insertLiteratureCategory(literatureCategoryEntity);
        return literatureCategoryService.convertEntityToDto(literatureCategory);
    }

    @PreAuthorize("hasAnyAuthority('LITERATURE_CATEGORY_UPDATE', 'ADMIN')")
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LiteratureCategoryResponse updateLiteratureCategory(@Valid @RequestBody LiteratureCategoryRequest literatureCategoryRequest, @PathVariable Long id) {
        log.info(" updating literature category of id: {} ", id);
        LiteratureCategory literatureCategoryEntity = literatureCategoryService.convertDtoToEntity(literatureCategoryRequest);
        var literatureCategory = literatureCategoryService.updateLiteratureCategory(id, literatureCategoryEntity);
        return literatureCategoryService.convertEntityToDto(literatureCategory);
    }

    @PreAuthorize("hasAnyAuthority('LITERATURE_CATEGORY_DELETE', 'ADMIN')")
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLiteratureCategory(@PathVariable Long id) {
        literatureCategoryService.deleteLiteratureCategory(id);
    }
}
