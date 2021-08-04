package com.qa.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.models.Task;
import com.qa.repositories.TaskRepository;

@Service
public class TaskService {

	private TaskRepository taskRepository;

	@Autowired
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	public List<Task> getAllTasks() {
		List<Task> tasksInDb = taskRepository.findAll();

		return tasksInDb;
	}

	public Task getTaskById(Long taskId) {
		return this.taskRepository.findById(taskId).orElseThrow(EntityNotFoundException::new);
	}

	public Task createTask(Task task) {
		Task savedTask = this.taskRepository.saveAndFlush(task);

		return savedTask;
	}

	public Task updateTaskById(Long taskId, Task task) {
		if (!taskRepository.existsById(taskId))
			throw new EntityNotFoundException();

		Task taskInDb = taskRepository.getById(taskId);

		taskInDb.setTaskName(task.getTaskName());

		Task updatedTask = this.taskRepository.saveAndFlush(taskInDb);
		return updatedTask;
	}

	public void deleteTaskById(Long taskId) {
		if (!taskRepository.existsById(taskId))
			throw new EntityNotFoundException();

		taskRepository.deleteById(taskId);
	}
}
