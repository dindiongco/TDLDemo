package com.qa.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.models.Task;
import com.qa.services.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {

	private TaskService taskService;

	@Autowired
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@GetMapping("/readAll")
	public ResponseEntity<List<Task>> getAllTasks() {
		return new ResponseEntity<List<Task>>(this.taskService.getAllTasks(), HttpStatus.OK);
	}

	@GetMapping("/read/{id}")
	public ResponseEntity<?> getTaskById(@PathVariable("id") Long taskId) {
		return new ResponseEntity<Task>(this.taskService.getTaskById(taskId), HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
		Task data = this.taskService.createTask(task);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create("localhost:8080/" + data.getTaskId()));
		headers.setContentType(MediaType.APPLICATION_JSON);

		return new ResponseEntity<Task>(data, headers, HttpStatus.CREATED);

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Task> updateTaskById(@PathVariable("id") Long taskId, @Valid @RequestBody Task task) {
		return new ResponseEntity<Task>(this.taskService.updateTaskById(taskId, task), HttpStatus.OK);

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteTaskById(@PathVariable("id") Long taskId) {		
		return new ResponseEntity<>(this.taskService.deleteTaskById(taskId), HttpStatus.OK);
	}

}
