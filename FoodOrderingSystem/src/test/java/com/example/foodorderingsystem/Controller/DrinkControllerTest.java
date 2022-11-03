package com.example.foodorderingsystem.Controller;

import com.example.foodorderingsystem.Entity.DesertEntity;
import com.example.foodorderingsystem.Entity.DrinkEntity;
import com.example.foodorderingsystem.repository.DesertRepository;
import com.example.foodorderingsystem.repository.DrinkRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DrinkController.class)
public class DrinkControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    DrinkRepository drinkRepository;

    DrinkEntity drinkEntity = new DrinkEntity(1l,"tea", 12.95);
    @Autowired
    WebApplicationContext applicationContext;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }


    @Test
    @Order(1)
    public void drinkControllerAnnotation() {
        RestController restController = DrinkController.class
                .getAnnotation(RestController.class);

        assertNotNull(restController);
    }

    @Test
    @Order(2)
    public void drinkControllerRequestMapping() {
        RequestMapping requestMapping = DrinkController.class
                .getAnnotation(RequestMapping.class);

        assertNotNull(requestMapping);
        assertThat(requestMapping.value().length).isEqualTo(1);
        assertThat(requestMapping.value()).contains("/drink");
    }

    @Test
    @Order(3)
    public void getAllDrinks_success() throws Exception {
        List<DrinkEntity> records = new ArrayList<>(Arrays.asList(drinkEntity));

        Mockito.when(drinkRepository.findAll()).thenReturn((records));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/drink/all_drinks")
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].name").value("tea"));


    }

    @Test
    @Order(4)
    public void getDrinkByName() throws Exception {
        List<DrinkEntity> drinks = new ArrayList<>(Arrays.asList(drinkEntity));

        Mockito.when(drinkRepository.findByName("tea")).thenReturn(drinks);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/drink/get_current_drink/%s", "tea"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1l))
                .andExpect(jsonPath("$[0].name").value("tea"))
                .andExpect(jsonPath("$[0].price").value(12.95));
    }

    @Test
    @Order(5)
    public void createRecord_success() throws Exception {
        DrinkEntity record = new DrinkEntity(1l,"tea", 4.3);

        Mockito.when(drinkRepository.save(record)).thenReturn(record);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/drink/new_drink")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(record));

        mockMvc.perform(mockRequest).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name").value("tea"));
    }
}
