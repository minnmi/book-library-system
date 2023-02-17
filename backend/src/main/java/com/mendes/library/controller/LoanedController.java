package com.mendes.library.controller;

import com.mendes.library.model.DTO.LoanedDTO.LoanedDTO;
import com.mendes.library.service.LoanedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        return loanedService.findAllLoanedBooks().stream().map(object -> loanedService.)
    }
}
