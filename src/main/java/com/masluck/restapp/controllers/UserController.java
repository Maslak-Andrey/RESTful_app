package com.masluck.restapp.controllers;

import com.masluck.restapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    //Получить всех пользователей
    @GetMapping("/admin")
    public String index() {
        return "index";
    }
    //Создание нового пользователя
    @PostMapping("/admin")
    public String saveUser(){
        return "redirect:/index";
    }
    //Запрос формы для создания пользователя
    @GetMapping("/admin/new")
    public String formSaveUser() {
        return "new";
    }
    //Запрос формы по редактированию пользователя
    @GetMapping("/admin/{id}/edit")
    public String formEditUser() {
        return "edit";
    }
    //Получение пользователя по Id
    @GetMapping("/admin/{id}")
    public String getUser() {
        return "user";
    }
    //Обновление пользователя по Id
    @PatchMapping("/admin/{id}")
    public String updateUser() {
            return "redirect:/index";
        }
    //Удаление пользователя по Id
    @DeleteMapping("/admin/{id}")
    public String delete() {
        return "redirect:/index";
    }
}
