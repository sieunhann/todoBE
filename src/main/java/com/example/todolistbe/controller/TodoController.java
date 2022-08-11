package com.example.todolistbe.controller;

import com.example.todolistbe.model.Todo;
import com.example.todolistbe.request.CreateTodoRequest;
import com.example.todolistbe.service.TodoService;
import com.example.todolistbe.request.UpdateTodoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class TodoController {
    @Autowired
    TodoService todoService;

    @GetMapping("/todos")
    public List<Todo> getList(@RequestParam("status") Optional<Boolean> status){
        if(status.isPresent()){
            return todoService.getTodos(status.get());
        }
        return todoService.getTodos();
    }

    @PostMapping("/todos")
    public Todo createTodo(@RequestBody CreateTodoRequest request){
        return todoService.createTodo(request);
    }

    @PutMapping("/todos/{id}")
    public Todo updateTodo(@PathVariable int id, @RequestBody UpdateTodoRequest request){
        return todoService.updateTodo(id, request);
    }

    @DeleteMapping("/todos/{id}")
    public void deleteTodo(@PathVariable int id){
        todoService.deleteTodo(id);
    }
}
