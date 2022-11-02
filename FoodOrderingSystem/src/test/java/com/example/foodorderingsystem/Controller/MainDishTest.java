package com.example.foodorderingsystem.Controller;

import com.example.foodorderingsystem.Entity.CountryEntity;
import com.example.foodorderingsystem.Entity.MainDishEntity;
import com.example.foodorderingsystem.repository.CountryRepository;
import com.example.foodorderingsystem.repository.MainDishRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jboss.jandex.Main;
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

import java.io.DataInput;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(value = MainDishController.class)
public class MainDishTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    MainDishRepository mainDishRepository;

    @MockBean
    CountryRepository countryRepository;

    CountryEntity country = new CountryEntity(1l, "Poland");

    MainDishEntity mainDish = new MainDishEntity(1l,"sup", 12.95, country);
    @Autowired
    WebApplicationContext applicationContext;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
        countryRepository.deleteAll();
    }


    @Test
    @Order(1)
    public void mainDishControllerAnnotation() {
        RestController restController = MainDishController.class
                .getAnnotation(RestController.class);

        assertNotNull(restController);
    }

    @Test
    @Order(2)
    public void mainDishControllerRequestMapping() {
        RequestMapping requestMapping = MainDishController.class
                .getAnnotation(RequestMapping.class);

        assertNotNull(requestMapping);
        assertThat(requestMapping.value().length).isEqualTo(1);
        assertThat(requestMapping.value()).contains("/main_dish");
    }

    @Test
    @Order(3)
    public void getAllDishes_success() throws Exception {
        List<MainDishEntity> records = new ArrayList<>(Arrays.asList(mainDish));

        Mockito.when(mainDishRepository.findAll()).thenReturn((records));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/main_dish/all_dishes")
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].name").value("sup"));


    }

    @Test
    @Order(4)
    public void getDishByName() throws Exception {
        List<MainDishEntity> record = new ArrayList<>(Arrays.asList(mainDish));

        Mockito.when(mainDishRepository.findByName("sup")).thenReturn(record);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/main_dish/dish_by_name/%s", "sup"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1l))
                .andExpect(jsonPath("$[0].name").value("sup"))
                .andExpect(jsonPath("$[0].price").value(12.95));
    }

    @Test
    @Order(5)
    public void getDishesByCountry() throws Exception {
        List<MainDishEntity> record = new ArrayList<>(Arrays.asList(mainDish));

        Mockito.when(mainDishRepository.findAllByCountry("poland")).thenReturn(record);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/main_dish/dish_by_Country/%s", "poland"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1l))
                .andExpect(jsonPath("$[0].name").value("sup"))
                .andExpect(jsonPath("$[0].price").value(12.95));
    }

    @Test
    @Order(6)
    public void createRecord_success() throws Exception {

        MainDishEntity record = new MainDishEntity(1l, "sup", 12.95, country);



        Mockito.when(mainDishRepository.save(record)).thenReturn(record);


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/main_dish/new_main_dish")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(record));


        mockMvc.perform(mockRequest).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name").value("sup"));

    }

}
