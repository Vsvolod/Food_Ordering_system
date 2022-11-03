package com.example.foodorderingsystem.Controller;

import com.example.foodorderingsystem.Entity.*;
import com.example.foodorderingsystem.repository.*;
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
@WebMvcTest(value = OrderController.class)
public class OrderControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    OrderRepository orderRepository;

    @MockBean
    MainDishRepository mainDishRepository;
    @MockBean
    DrinkRepository drinkRepository;
    @MockBean
    DesertRepository desertRepository;
    @MockBean
    CountryRepository countryRepository;

    CountryEntity country = new CountryEntity(1l, "Poland");

    MainDishEntity mainDish = new MainDishEntity(1l,"sup", 12.95, country);

    DesertEntity desert = new DesertEntity(1l,"vafli", 12.95);

    DrinkEntity drinkEntity = new DrinkEntity(1l,"tea", 12.95);

    OrderEntity order = new OrderEntity(1l, 1l, mainDish, desert, drinkEntity, true, true);


    @Autowired
    WebApplicationContext applicationContext;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
        orderRepository.deleteAll();
    }


    @Test
    @Order(1)
    public void OrderControllerAnnotation() {
        RestController restController = OrderController.class
                .getAnnotation(RestController.class);

        assertNotNull(restController);
    }

    @Test
    @Order(2)
    public void orderControllerRequestMapping() {
        RequestMapping requestMapping = OrderController.class
                .getAnnotation(RequestMapping.class);

        assertNotNull(requestMapping);
        assertThat(requestMapping.value().length).isEqualTo(1);
        assertThat(requestMapping.value()).contains("/order");
    }


    @Test
    @Order(3)
    public void getOrderByClientId() throws Exception {
        List<OrderEntity> record = new ArrayList<>(Arrays.asList(order));

        Mockito.when(orderRepository.findByClientId(1L)).thenReturn(record);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/order/clients_order/%d", 1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1L));
    }


    @Test
    @Order(4)
    public void createRecordOfOrderSuccess() throws Exception {

        Mockito.when(orderRepository.save(order)).thenReturn(order);


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/order/new_order")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(order));


        mockMvc.perform(mockRequest).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id").value(1L));

    }
}
