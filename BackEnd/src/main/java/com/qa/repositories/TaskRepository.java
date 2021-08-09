package com.qa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
