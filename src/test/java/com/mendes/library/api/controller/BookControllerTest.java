package com.mendes.library.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {

    static String BookApi = "/api/books";

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Deve criar um livro com sucesso.")
    public void createBookTest() throws Exception {

        String json = new ObjectMapper().writeValueAsString(null);

       MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BookApi)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

       mockMvc.perform(request)
               .andExpect(MockMvcResultMatchers.status().isCreated())
               .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
               .andExpect(MockMvcResultMatchers.jsonPath("title").value("Meu Livro"))
               .andExpect(MockMvcResultMatchers.jsonPath("author").value("Autor"))
               .andExpect(MockMvcResultMatchers.jsonPath("isbn").value("6546464"))
               ;

    }

    @Test
    @DisplayName("Deve lançar erro de validação quando não houver dados suficientes para criação do livro.")
    public void createInvalidBookTest() {

    }
}
