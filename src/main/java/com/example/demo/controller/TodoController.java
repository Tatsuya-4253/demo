package com.example.demo.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
    TodoRepository todoRepository;

    // Todo一覧画面表示
    @GetMapping("/todo")
    public String index(Model model) {

        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formattedDate = now.format(formatter);

        model.addAttribute("now", formattedDate);

        List<Todo> todos = todoRepository.findAll();
        model.addAttribute("todos", todos);
        return "index";
    }

    // Todo新規登録画面
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
            model.addAttribute("name", name);
            model.addAttribute("style", style);
            model.addAttribute("errList", errList);
            return "newtodo";
        }

        todoRepository.save(todo);

        return "redirect:/todo";
    }

    // Todo新規登録を一覧へ反映
    @GetMapping("/todo/new")
    public String newtodo() {
        return "newtodo";
    }

    // Todo編集・削除画面
    @GetMapping("/todo/{id}/edittodo")
    public String edittodo(
            @PathVariable Integer id,
            Model model) {
        Optional<Todo> todo = todoRepository.findById(id);
        Todo findtodo = todo.get();
        model.addAttribute("todo", findtodo);
        return "edittodo";
    }

    // Todo編集を一覧へ反映
    @PostMapping("/todo/{id}")
    public String updatetodo(
            @PathVariable Integer id,
            @RequestParam("name") String name,
            @RequestParam("style") String style,
            Model model) {
        Optional<Todo> todo = todoRepository.findById(id);
        Todo updatetodo = todo.get();

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
            updatetodo.setName(name);
            updatetodo.setStyle(style);
            model.addAttribute("todo", updatetodo);
            model.addAttribute("errList", errList);
            return "edittodo";
        }
        updatetodo.setName(name);
        updatetodo.setStyle(style);
        todoRepository.save(updatetodo);
        return "redirect:/todo";
    }

    // Todo編集・削除画面でTodoを削除
    @PostMapping("/todo/{id}/delete")
    public String deletetodo(@PathVariable Integer id) {
        todoRepository.deleteById(id);
        return "redirect:/todo";
    }

    // Todo一覧画面で完了済をグレーアウト
    @PostMapping("/todo/{id}/complete")
    public String toggletodo(@PathVariable Integer id) {
        Optional<Todo> todo = todoRepository.findById(id);
        Todo compTodo = todo.get();
        compTodo.setComplete(!compTodo.getComplete());
        todoRepository.save(compTodo);
        return "redirect:/todo";
    }

}