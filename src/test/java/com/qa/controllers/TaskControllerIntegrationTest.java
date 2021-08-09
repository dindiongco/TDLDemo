package com.qa.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.models.Task;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:task-schema.sql","classpath:task-data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles("test")
public class TaskControllerIntegrationTest {

	@Autowired
	private MockMvc mock;

	@Autowired
	private ObjectMapper mapper;
	
	private final Task testTask = new Task("Get coffee");

	private final Task testSavedTask = new Task(1L, "Get coffee");

	@Test
	void testCreateTask() throws Exception {
		Task task = new Task("Read 10 pages");

		String taskAsJSON = this.mapper.writeValueAsString(task);

		RequestBuilder mockRequest = post("/task/create").contentType(MediaType.APPLICATION_JSON).content(taskAsJSON);

		Task savedTask = new Task(2L, "Read 10 pages");

		String savedTaskAsJSON = this.mapper.writeValueAsString(savedTask);

		ResultMatcher matchStatus = status().isCreated();

		ResultMatcher matchBody = content().json(savedTaskAsJSON);

		this.mock.perform(mockRequest).andExpect(matchBody).andExpect(matchStatus);

	}

	@Test
	void testReadTaskById() throws Exception {
		 this.mock.perform(get("/task/read/1")).andExpect(status().isOk())
         .andExpect(content().json(this.mapper.writeValueAsString(testSavedTask)));

	}

	@Test
	void testReadAll() throws Exception {
		List<Task> tasks = new ArrayList<>();
		tasks.add(testTask);

		String result = this.mapper.writeValueAsString(tasks);

		RequestBuilder mockRequest = get("/task/readAll").contentType(MediaType.APPLICATION_JSON).content(result);

		List<Task> savedTasks = new ArrayList<>();
		savedTasks.add(testSavedTask);

		String savedResult = this.mapper.writeValueAsString(savedTasks);

		ResultMatcher matchBody = content().json(savedResult);

		this.mock.perform(mockRequest).andExpect(status().isOk()).andExpect(matchBody);

	}

	@Test
	void testUpdateTask() throws Exception {
				
		Task updateTask = new Task(1L, "Make coffee");

		String updateTaskAsJSON = this.mapper.writeValueAsString(updateTask);

		RequestBuilder mockRequest = 
									put("/task/update/1")
									.contentType(MediaType.APPLICATION_JSON)
									.content(updateTaskAsJSON);

		ResultMatcher matchBody = content().json(updateTaskAsJSON);
		
		ResultMatcher matchStatus = status().isOk();

		this.mock.perform(mockRequest).andExpect(matchBody).andExpect(matchStatus);
	}
	
	@Test
	void testDelete() throws Exception {
		this.mock.perform(delete("/task/delete/1")).andExpect(status().isOk());

	}

}
