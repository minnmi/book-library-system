package com.mendes.library.service;

import com.mendes.library.model.Configuration;
import com.mendes.library.repository.ConfigurationRepository;
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

    private Configuration getConfiguration() throws Exception {
        Optional<Configuration> configurationOpt = this.configurationRepository.findById(1L);
        if (configurationOpt.isEmpty())
            throw new Exception("No configuration provided");

        return configurationOpt.get();
    }

    public final int getMaximumNumberBooksUser() throws Exception {
        return this.getConfiguration().getMaximumNumberBooksUser();
    }

    public final int getMaximumLoanPeriod() throws Exception {
        return this.getConfiguration().getMaximumLoanPeriod();
    }

    public final float getProportionBooksStock() throws Exception {
        return this.getConfiguration().getProportionBooksStock();
    }

    public final int getBookingTimeOut() throws Exception {
        return this.getConfiguration().getBookingTimeOut();
    }

}
