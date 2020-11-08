package com.masluck.restapp.controllers;

import com.masluck.restapp.entities.User;
import com.masluck.restapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Получить всех пользователей
    @GetMapping("/admin")
    public ResponseEntity<List<User>> index() {

        final List<User> users = userRepository.findAll();

        return users != null &&  !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Создание нового пользователя
    @PostMapping("/admin")
    public ResponseEntity<?> saveUser(@RequestBody User user){
        userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
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
    public ResponseEntity<User> getUser(@PathVariable(name = "id") long id) {
            User user = userRepository.findById(id).get();

            return user != null
                    ? new ResponseEntity<>(user, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Обновление пользователя по Id
//    @PatchMapping("/admin/{id}")
//    public ResponseEntity<?> updateUser(@PathVariable(name = "id") long id, @RequestBody User user) {
//
//            final boolean updated = userRepository.saveAndFlush(user, id);
//
//            return updated
//                    ? new ResponseEntity<>(HttpStatus.OK)
//                    : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
//    }
//
//    //Удаление пользователя по Id
//    @DeleteMapping("/admin/{id}")
//    public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
//            final boolean deleted = userRepository.deleteById(id);
//
//            return deleted
//                    ? new ResponseEntity<>(HttpStatus.OK)
//                    : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
//    }
}
