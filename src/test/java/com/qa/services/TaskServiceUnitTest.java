package com.qa.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.models.Task;
import com.qa.repositories.TaskRepository;

@SpringBootTest
public class TaskServiceUnitTest {

	@MockBean
	private TaskRepository taskRepository;

	@Autowired
	private TaskService taskService;

	@Test
	void testReadUnitById() {
		Long taskId = 1L;
		Task expectedTask = new Task(taskId, "Get coffee");

		Mockito.when(this.taskRepository.findById(taskId)).thenReturn(Optional.of(expectedTask));

		assertThat(this.taskService.getTaskById(taskId)).isEqualTo(expectedTask);

		Mockito.verify(this.taskRepository, Mockito.times(1)).findById(taskId);
	}

	@Test
	void testCreateUnit() {
		Task coffee = new Task("Get coffee");
		Task coffeeWithId = new Task(1L, "Get coffee");

		Mockito.when(this.taskRepository.saveAndFlush(coffee)).thenReturn(coffeeWithId);

		assertThat(this.taskService.createTask(coffee)).isEqualTo(coffeeWithId);

		Mockito.verify(this.taskRepository, Mockito.times(1)).saveAndFlush(coffee);
	}

	@Test
	void testReadAll() {
		Long taskId = 1L;
		Task testCoffee = new Task(null, "Get coffee");
		testCoffee.setTaskId(taskId);
		List<Task> tasks = List.of(testCoffee);

		// WHEN
		Mockito.when(this.taskRepository.findAll()).thenReturn(tasks);

		// THEN
		assertThat(this.taskService.getAllTasks()).isEqualTo(tasks);

		// verify
		Mockito.verify(this.taskRepository, Mockito.times(1)).findAll();

	}

	@Test
	void testUpdateUnit() {
		Long taskId = 1L;
		Task newTask = new Task(taskId, "Read 10 pages");
		Task existingTask = new Task(taskId, "Get coffee");
//		Task updatedTask = new Task(taskId, newTask.getTaskName());

		Mockito.when(this.taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
		Mockito.when(this.taskRepository.save(newTask)).thenReturn(newTask);

		assertThat(this.taskService.updateTaskById(1L, newTask)).isEqualTo(newTask);

		Mockito.verify(this.taskRepository, Mockito.times(1)).findById(taskId);
		Mockito.verify(this.taskRepository, Mockito.times(1)).save(newTask);
	}

	@Test
	void testDelete() {
		Long testId = 1L;

		Mockito.when(this.taskRepository.existsById(testId)).thenReturn(false);
		
		assertThat(this.taskService.deleteTaskById(testId)).isTrue();

		Mockito.verify(this.taskRepository, Mockito.times(1)).existsById(testId);
	}

}
