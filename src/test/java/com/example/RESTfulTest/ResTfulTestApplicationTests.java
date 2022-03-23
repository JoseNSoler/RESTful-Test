package com.example.RESTfulTest;

import com.example.RESTfulTest.model.Widget;
import com.example.RESTfulTest.service.WidgetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class WidgetRestControllerTest {

	@MockBean
	private WidgetService service;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("GET /widgets success")
	void testGetWidgetsSuccess() throws Exception {
		Widget widget1 = new Widget(1l, "Widget Name", "Description", 1);
		Widget widget2 = new Widget(2l, "Widget 2 Name", "Description 2", 4);
		doReturn(Lists.newArrayList(widget1, widget2)).when(service).findAll();

		mockMvc.perform(get("/rest/widgets"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(header().string(HttpHeaders.LOCATION, "/rest/widgets"))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].name", is("Widget Name")))
				.andExpect(jsonPath("$[0].description", is("Description")))
				.andExpect(jsonPath("$[0].version", is(1)))
				.andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].name", is("Widget 2 Name")))
				.andExpect( jsonPath("$[1].description", is("Description 2")))
				.andExpect( jsonPath("$[1].version", is(4)));
	}
}
