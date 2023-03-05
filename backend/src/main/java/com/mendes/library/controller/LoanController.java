package com.mendes.library.controller;

import com.mendes.library.model.DTO.LoanedDTO.LoanedDTO;
import com.mendes.library.model.Loan;
import com.mendes.library.service.LoanService;
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
@RequestMapping("/v1/loans")
@Slf4j
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PreAuthorize("hasAnyAuthority('LOANED_VIEW', 'ADMIN')")
    @GetMapping("/find/all")
    public Page<LoanedDTO> findAllLoans(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        var page = loanService.findAllLoanedBooks(pageable);
        var content = page.getContent()
                .stream()
                .map(object -> loanService.convertEntityToDto(object)).toList();

        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());

    }

    @PreAuthorize("hasAnyAuthority('LOANED_VIEW', 'ADMIN')")
    @GetMapping("/find/{id}")
    public LoanedDTO findById(@PathVariable Long id) {
        Loan object = loanService.findById(id);
        return loanService.convertEntityToDto(object);
    }

    @PreAuthorize("hasAnyAuthority('LOANED_VIEW', 'ADMIN')")
    @GetMapping("/find/{userId}")
    public List<LoanedDTO> findLoansByUser(@PathVariable Long userId) {
        return loanService.findLoansByUser(userId)
                .stream()
                .map(object -> loanService.convertEntityToDto(object))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('LOANED_VIEW', 'ADMIN')")
    @GetMapping("/find/{bookId}/books")
    public List<LoanedDTO> findLoansByBook(@PathVariable Long bookId) {
        return loanService.findLoansByBook(bookId)
                .stream()
                .map(object -> loanService.convertEntityToDto(object))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('LOANED_VIEW', 'ADMIN')")
    @GetMapping("/find/late-loans/{userId}")
    public List<LoanedDTO> findLateLoansByUser(@RequestBody LocalDateTime localDateTime, @PathVariable Long userId) {
        return loanService.findLateLoansByUser(localDateTime, userId)
                .stream()
                .map(object -> loanService.convertEntityToDto(object))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('LOANED_VIEW', 'ADMIN')")
    @GetMapping("/find/initial-date")
    public List<LoanedDTO> findByInitialDate(@RequestParam("initialDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime from,
                                             @RequestParam("finalDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime to) {
        return loanService.findByInitialDate(from, to)
                .stream()
                .map(object -> loanService.convertEntityToDto(object))
                .collect(Collectors.toList());

    }


    @PreAuthorize("hasAnyAuthority('LOANED_VIEW', 'ADMIN')")
    @GetMapping("/find/final-date")
    public List<LoanedDTO> findByFinalDate(@RequestParam("initialDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime from,
                                             @RequestParam("finalDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime to) {
        return loanService.findByFinalDate(from, to)
                .stream()
                .map(object -> loanService.convertEntityToDto(object))
                .collect(Collectors.toList());

    }

    @PreAuthorize("hasAnyAuthority('LOANED_VIEW', 'ADMIN')")
    @GetMapping("/find-history/{userId}")
    public List<LoanedDTO> findHistory(@PathVariable Long userId) {
        return loanService.findHistory(userId)
                .stream()
                .map(object -> loanService.convertEntityToDto(object))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('LOANED_INSERT', 'ADMIN')")
    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public LoanedDTO insertLoan(@Valid @RequestBody Long bookId) throws Exception {
//        log.info(" inserting a new loan: {} ", objectDTO.getBook());
        Loan object = loanService.insertLoaned(bookId);
        return loanService.convertEntityToDto(object);
    }

    @PreAuthorize("hasAnyAuthority('LOANED_INSERT', 'ADMIN')")
    @PostMapping("/book/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    public LoanedDTO loanBook(@PathVariable Long bookId) throws Exception {
        var loan = loanService.insertLoaned(bookId);
        return loanService.convertEntityToDto(loan);
    }

    @PreAuthorize("hasAnyAuthority('LOANED_INSERT', 'ADMIN')")
    @PostMapping("{id}/return")
    public LoanedDTO returnBook(Long id) {
        return null;
    }

}
