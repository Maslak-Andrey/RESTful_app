package com.masluck.restapp.controllers;

import com.masluck.restapp.entities.User;
import com.masluck.restapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Получить всех пользователей
    @RequestMapping(value = "/admin/list", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<User>> index() {

        final List<User> users = userRepository.findAll();

        return users != null &&  !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Создание нового пользователя
    @PostMapping("/admin")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
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
    @PutMapping("/admin")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updatedUser = userRepository.saveAndFlush(user);

            return updatedUser != user
                    ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    //Удаление пользователя по Id
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
        User user = userRepository.findById(id).get(); // for boolean check
        userRepository.deleteById(id);

            return user == null
                    ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
