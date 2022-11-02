package com.example.foodorderingsystem.Controller;

import com.example.foodorderingsystem.Entity.DesertEntity;
import com.example.foodorderingsystem.repository.DesertRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(DesertController.class)
public class DesertControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    DesertRepository desertRepository;

    DesertEntity desert1 = new DesertEntity(1l,"vafli", 12.95);
    @Autowired
    WebApplicationContext applicationContext;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }


    @Test
    @Order(1)
    public void desertRestControllerAnnotation() {
        RestController restController = DesertController.class
                .getAnnotation(RestController.class);

        assertNotNull(restController);
    }

    @Test
    @Order(2)
    public void desertControllerRequestMapping() {
        RequestMapping requestMapping = DesertController.class
                .getAnnotation(RequestMapping.class);

        assertNotNull(requestMapping);
        assertThat(requestMapping.value().length).isEqualTo(1);
        assertThat(requestMapping.value()).contains("/desert");
    }

    @Test
    @Order(3)
    public void getAllDesserts_success() throws Exception {
        List<DesertEntity> records = new ArrayList<>(Arrays.asList(desert1));

        Mockito.when(desertRepository.findAll()).thenReturn((records));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/desert/all_deserts")
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].name").value("vafli"));


    }

    @Test
    @Order(4)
    public void getDesertByName() throws Exception {
        List<DesertEntity> desert = new ArrayList<>(Arrays.asList(desert1));

        Mockito.when(desertRepository.findByName("vafli")).thenReturn(desert);

        mockMvc.perform(MockMvcRequestBuilders.get(String.format("/desert/%s", "vafli"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1l))
                .andExpect(jsonPath("$[0].name").value("vafli"))
                .andExpect(jsonPath("$[0].price").value(12.95));
    }

    @Test
    @Order(5)
    public void createRecord_success() throws Exception {
        DesertEntity record = new DesertEntity(1l,"pudding", 4.3);

        Mockito.when(desertRepository.save(record)).thenReturn(record);

       MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/desert/new_desert")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(record));

        mockMvc.perform(mockRequest).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name").value("pudding"));
    }
}
