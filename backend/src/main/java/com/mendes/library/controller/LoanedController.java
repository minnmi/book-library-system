package com.mendes.library.controller;

import com.mendes.library.model.DTO.LoanedDTO.LoanedDTO;
import com.mendes.library.model.Loaned;
import com.mendes.library.service.LoanedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/loans")
@Slf4j
public class LoanedController {

    private final LoanedService loanedService;

    @Autowired
    public LoanedController(LoanedService loanedService) {
        this.loanedService = loanedService;
    }

    @PreAuthorize("hasAnyAuthority('LOANED_VIEW', 'ADMIN')")
    @GetMapping("/find/all")
    public Page<LoanedDTO> findAllLoans(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        var page = loanedService.findAllLoanedBooks(pageable);
        var content = page.getContent()
                .stream()
                .map(object -> loanedService.convertEntityToDto(object)).toList();

        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());

    }

    @PreAuthorize("hasAnyAuthority('LOANED_VIEW', 'ADMIN')")
    @GetMapping("/find/{id}")
    public LoanedDTO findById(@PathVariable Long id) {
        Loaned object = loanedService.findById(id);
        return loanedService.convertEntityToDto(object);
    }

    @PreAuthorize("hasAnyAuthority('LOANED_VIEW', 'ADMIN')")
    @GetMapping("/find/{userId}")
    public List<LoanedDTO> findLoansByUser(@PathVariable Long userId) {
        return loanedService.findLoansByUser(userId)
                .stream()
                .map(object -> loanedService.convertEntityToDto(object))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('LOANED_VIEW', 'ADMIN')")
    @GetMapping("/find/{bookId}/books")
    public List<LoanedDTO> findLoansByBook(@PathVariable Long bookId) {
        return loanedService.findLoansByBook(bookId)
                .stream()
                .map(object -> loanedService.convertEntityToDto(object))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('LOANED_VIEW', 'ADMIN')")
    @GetMapping("/find/late-loans/{userId}")
    public List<LoanedDTO> findLateLoansByUser(@RequestBody LocalDateTime localDateTime, @PathVariable Long userId) {
        return loanedService.findLateLoansByUser(localDateTime, userId)
                .stream()
                .map(object -> loanedService.convertEntityToDto(object))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('LOANED_VIEW', 'ADMIN')")
    @GetMapping("/find/initial-date")
    public List<LoanedDTO> findByInitialDate(@RequestParam("initialDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime from,
                                             @RequestParam("finalDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime to) {
        return loanedService.findByInitialDate(from, to)
                .stream()
                .map(object -> loanedService.convertEntityToDto(object))
                .collect(Collectors.toList());

    }


    @PreAuthorize("hasAnyAuthority('LOANED_VIEW', 'ADMIN')")
    @GetMapping("/find/final-date")
    public List<LoanedDTO> findByFinalDate(@RequestParam("initialDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime from,
                                             @RequestParam("finalDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime to) {
        return loanedService.findByFinalDate(from, to)
                .stream()
                .map(object -> loanedService.convertEntityToDto(object))
                .collect(Collectors.toList());

    }

    @PreAuthorize("hasAnyAuthority('LOANED_VIEW', 'ADMIN')")
    @GetMapping("/find-history/{userId}")
    public List<LoanedDTO> findHistory(@PathVariable Long userId) {
        return loanedService.findHistory(userId)
                .stream()
                .map(object -> loanedService.convertEntityToDto(object))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('LOANED_INSERT', 'ADMIN')")
    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public LoanedDTO insertLoan(@Valid @RequestBody Long bookId) throws Exception {
//        log.info(" inserting a new loan: {} ", objectDTO.getBook());
        Loaned object = loanedService.insertLoaned(bookId);
        return loanedService.convertEntityToDto(object);
    }

    @PreAuthorize("hasAnyAuthority('LOANED_INSERT', 'ADMIN')")
    @PostMapping("/book/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    public LoanedDTO loanBook(@PathVariable Long bookId) throws Exception {
        var loan = loanedService.insertLoaned(bookId);
        return loanedService.convertEntityToDto(loan);
    }

    @PreAuthorize("hasAnyAuthority('LOANED_INSERT', 'ADMIN')")
    @PostMapping("{id}/return")
    public LoanedDTO returnBook(Long id) {
        return null;
    }

}
