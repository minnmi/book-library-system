package com.mendes.library.controller;

import com.mendes.library.model.DTO.LoanedDTO.LoanedDTO;
import com.mendes.library.model.Loaned;
import com.mendes.library.service.LoanedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/find/all")
    public List<LoanedDTO> findAllLoans() {
        return loanedService.findAllLoanedBooks()
                .stream()
                .map(object -> loanedService.convertEntityToDto(object)).toList();

    }

    @GetMapping("/find/{id}")
    public LoanedDTO findById(@PathVariable Long id) {
        Loaned object = loanedService.findById(id);
        return loanedService.convertEntityToDto(object);
    }

    @GetMapping("/find/{userId}")
    public List<LoanedDTO> findLoansByUser(@PathVariable Long userId) {
        return loanedService.findLoansByUser(userId)
                .stream()
                .map(object -> loanedService.convertEntityToDto(object))
                .collect(Collectors.toList());
    }

    @GetMapping("/find/{bookId}/books")
    public List<LoanedDTO> findLoansByBook(@PathVariable Long bookId) {
        return loanedService.findLoansByBook(bookId)
                .stream()
                .map(object -> loanedService.convertEntityToDto(object))
                .collect(Collectors.toList());
    }

    @GetMapping("/find/late-loans/{userId}")
    public List<LoanedDTO> findLateLoansByUser(@RequestBody LocalDateTime localDateTime, @PathVariable Long userId) {
        return loanedService.findLateLoansByUser(localDateTime, userId)
                .stream()
                .map(object -> loanedService.convertEntityToDto(object))
                .collect(Collectors.toList());
    }

    @GetMapping("/find/initial-date")
    public List<LoanedDTO> findByInitialDate(@RequestParam("initialDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime from,
                                             @RequestParam("finalDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime to) {
        return loanedService.findByInitialDate(from, to)
                .stream()
                .map(object -> loanedService.convertEntityToDto(object))
                .collect(Collectors.toList());

    }


    @GetMapping("/find/final-date")
    public List<LoanedDTO> findByFinalDate(@RequestParam("initialDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime from,
                                             @RequestParam("finalDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime to) {
        return loanedService.findByFinalDate(from, to)
                .stream()
                .map(object -> loanedService.convertEntityToDto(object))
                .collect(Collectors.toList());

    }

    @GetMapping("/find-history/{userId}")
    public List<LoanedDTO> findHistory(@PathVariable Long userId) {
        return loanedService.findHistory(userId)
                .stream()
                .map(object -> loanedService.convertEntityToDto(object))
                .collect(Collectors.toList());
    }


    @PostMapping("/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public LoanedDTO insertLoan(@Valid @RequestBody LoanedDTO objectDTO) throws Exception {
        log.info(" inserting a new loan: {} ", objectDTO.getBook());
        Loaned objectRequest = loanedService.convertDtoToEntity(objectDTO);
        Loaned object = loanedService.insertLoaned(objectRequest);
        return loanedService.convertEntityToDto(object);
    }

}
