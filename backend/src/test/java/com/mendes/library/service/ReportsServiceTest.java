package com.mendes.library.service;

import net.sf.jasperreports.engine.JRException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

@SpringBootTest
class ReportsServiceTest {
    @Autowired
    private ReportsService reportsService;

    @Test
    void testBookReport() throws JRException, SQLException {
        try {
            reportsService.report("/Reports/BooksReport.jasper", "book-report.pdf");
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testUserHistoryReport() throws JRException, SQLException {
        try {
            reportsService.report("/Reports/UserHistory.jasper", "user-history-report.pdf");
        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}