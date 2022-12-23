package com.mendes.library.service;

import com.mendes.library.repository.LoanedRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanedService {

    private final LoanedRepository loanedRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public LoanedService(LoanedRepository loanedRepository, ModelMapper modelMapper) {
        this.loanedRepository = loanedRepository;
        this.modelMapper = modelMapper;
    }


}
