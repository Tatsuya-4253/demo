package com.example.demo.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Todo;
import com.example.demo.repository.TodoRepository;

@Controller
public class TodoController {

    @Autowired
    HttpSession httpSession;

    @Autowired
    TodoRepository todoRepository;

    @GetMapping("/todo")
    public String index(Model model) {

        httpSession.invalidate();
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d");
        String formattedDate = now.format(formatter);

        model.addAttribute("now", formattedDate);

        List<Todo> todos = todoRepository.findAll();
        model.addAttribute("todos", todos);
        return "index";
    }

    @PostMapping("/todo")
    public String createtodo(
            @RequestParam(name = "name", defaultValue = "") String name,
            @RequestParam("style") String style,
            Model model) {
        Todo todo = new Todo(name, style);

        List<String> errList = new ArrayList<>();

        if (name.trim().isEmpty() || name.equals("")) {
            errList.add("Todoの内容は必須です");
        } else if (name.length() < 3) {
            errList.add("Todoの内容は3文字以上で入力してください");
            model.addAttribute("name", name);
        }
        if (style.equals("0")) {
            errList.add("カテゴリを選択してください");
            model.addAttribute("name", name);

        }

        if (!errList.isEmpty()) {
            model.addAttribute("errList", errList);
            return "newtodo";
        }

        todoRepository.save(todo);

        return "redirect:/todo";
    }

    @GetMapping("/todo/new")
    public String newtodo() {
        return "newtodo";
    }

    @GetMapping("/todo/{id}/edittodo")
    public String edittodo(
            @PathVariable Integer id,
            Model model) {
        Optional<Todo> todo = todoRepository.findById(id);
        Todo findtodo = todo.get();
        model.addAttribute("todo", findtodo);
        return "edittodo";
    }

    @PostMapping("/todo/{id}")
    public String updatetodo(
            @PathVariable Integer id,
            @RequestParam String name,
            @RequestParam String style) {
        Optional<Todo> todo = todoRepository.findById(id);
        Todo updatetodo = todo.get();
        updatetodo.setName(name);
        updatetodo.setStyle(style);
        todoRepository.save(updatetodo);
        return "redirect:/todo";
    }

    @PostMapping("/todo/{id}/delete")
    public String deletetodo(@PathVariable Integer id) {
        todoRepository.deleteById(id);
        return "redirect:/todo";
    }

}