package com.example.todolistbe.service;

import com.example.todolistbe.exception.BadRequestException;
import com.example.todolistbe.exception.NotFoundException;
import com.example.todolistbe.model.Todo;
import com.example.todolistbe.request.CreateTodoRequest;
import com.example.todolistbe.request.UpdateTodoRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class TodoService {
    private List<Todo> todos;

    public TodoService() {
        todos = new ArrayList<>();
        todos.add(new Todo(1, "Di choi", true));
        todos.add(new Todo(2, "Di hoc", true));
        todos.add(new Todo(3, "Di ngu", false));
        todos.add(new Todo(4, "Da bong", false));
        todos.add(new Todo(5, "Xem TV", true));
    }

    // Lay danh sach tat ca cong viec
    public List<Todo> getTodos(){
        return todos;
    }

    // Lay danh sach tat ca cong viec theo trang thai

    public List<Todo> getTodos(boolean status){
        if(status) {
            return todos.stream().filter(Todo::isStatus).toList();
        }
        return todos.stream().filter(todo -> !todo.isStatus()).toList();
    }

    // Tao cv
    public Todo createTodo(CreateTodoRequest request){
        if(request.getTitle().trim().equals("")){
            throw new BadRequestException("Title cannot blank");
        }

        Random rd = new Random();

        Todo todo = new Todo(rd.nextInt(1000), request.getTitle(), false);
        todos.add(todo);
        return todo;
    }

    // Cap nhat cv
    public Todo updateTodo(int id, UpdateTodoRequest request){
        if(request.getTitle().trim().equals("")){
            throw new BadRequestException("Title cannot blank");
        }

        Todo todo = findById(id).orElseThrow(() -> {
            throw new NotFoundException(id + ": doesn't exist");
        });

        todo.setTitle(request.getTitle());
        todo.setStatus(request.isStatus());
        return todo;
    }

    // Xoa cv
    public void deleteTodo(int id){
        Todo todo = findById(id).orElseThrow(() -> {
            throw new NotFoundException(id + ": doesn't exist");
        });
        todos.remove(todo);
    }

    // findById
    public Optional<Todo> findById(int id){
        return todos.stream()
                .filter(todo -> todo.getId() == id)
                .findFirst();
    }
}
