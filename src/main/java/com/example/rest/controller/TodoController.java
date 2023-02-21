package com.example.rest.controller;

import com.example.rest.model.Task;
import com.example.rest.repository.ITodoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = TodoController.TASKS)
public class TodoController {
    public static final String TASKS = "/tasks";
    @Autowired
    private ITodoRepository todoRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTask(@RequestBody Task task) {
        todoRepository.save(task);
    }

    @GetMapping
    public List<Task> getTasks() {
        return todoRepository.findAll();
    }

    @GetMapping(path = "{id}")
    public Task getTasksById(@PathVariable(name = "id") Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Id [%s] not found", id)));
    }


//    @PutMapping(path = "{id}")
//    public Task updateTaskById(@PathVariable(name = "id") Long id, @RequestBody Task task) {
//        Task updatedTask = todoRepository.findById(id).get();
//        updatedTask.setTitle(task.getTitle());
//        updatedTask.setDescription(task.getDescription());
//        return todoRepository.save(updatedTask);
//
//    }
@PutMapping(path = "{id}")
   public String updateTask(@PathVariable(name = "id") Long id, @RequestBody Task task) {
       Task updatedTask = todoRepository.findById(id).get();
        updatedTask.setTitle(task.getTitle());
       updatedTask.setDescription(task.getDescription());
       todoRepository.save(updatedTask);
       return "Task updated";
    }


    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id){
        Task deletedTask = todoRepository.findById(id).get();
        todoRepository.delete(deletedTask);
        return "Task Deleted";

    }


}
