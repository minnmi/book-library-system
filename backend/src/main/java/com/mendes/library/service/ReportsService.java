package com.mendes.library.service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportsService {
    private final DataSource dataSource;

    public ReportsService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void report(String pathToReport, String outputFilename) throws JRException, SQLException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ImagesPath", "/home/matheusc/Documents/codes/book-library-system/backend/src/main/resources/Reports/Images");
        var url = getClass().getResource(pathToReport);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());

        SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
        reportConfig.setSizePageToContent(true);
        reportConfig.setForceLineBreakPolicy(false);

        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFilename));
        exporter.setConfiguration(reportConfig);
        exporter.setConfiguration(new SimplePdfExporterConfiguration());
        exporter.exportReport();
    }
}