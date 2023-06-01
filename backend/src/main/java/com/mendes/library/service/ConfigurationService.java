package com.mendes.library.service;

import com.mendes.library.controller.exception.LogicException;
import com.mendes.library.controller.exception.ValidationError;
import com.mendes.library.model.Configuration;
import com.mendes.library.repository.ConfigurationRepository;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfigurationService {
    private ConfigurationRepository configurationRepository;

    @Autowired
    public ConfigurationService(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    private Configuration getConfiguration() throws LogicException {
        Optional<Configuration> optionalConfiguration = this.configurationRepository.findById(1L);
        if (optionalConfiguration.isEmpty())
            throw new LogicException("No configuration provided");

        return optionalConfiguration.get();
    }

    public final int getMaximumNumberBooksUser() throws LogicException {
        return this.getConfiguration().getMaximumNumberBooksUser();
    }

    public final int getMaximumLoanPeriod() throws LogicException {
        return this.getConfiguration().getMaximumLoanPeriod();
    }

    public final float getProportionBooksStock() throws LogicException {
        return this.getConfiguration().getProportionBooksStock();
    }

    public final int getBookingTimeOut() throws LogicException {
        return this.getConfiguration().getBookingTimeOut();
    }

}
