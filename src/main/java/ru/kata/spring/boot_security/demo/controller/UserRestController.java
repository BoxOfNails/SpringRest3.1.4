package ru.kata.spring.boot_security.demo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/api")
public class UserRestController {

    private UserService userService;
    private RoleService roleService;
    private Logger logger = Logger.getLogger(getClass().getName());

    public UserRestController(UserService theUserservice, RoleService theRoleService) {
        userService = theUserservice;
        roleService = theRoleService;
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> theRoles = roleService.findAllRoles();
        return ResponseEntity.ok(theRoles);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        logger.info("In rest Controller");
        List<User> theUsers = userService.findAllUsers();
        return ResponseEntity.ok(theUsers);

    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUser(@PathVariable int userId) {
        User tempUser = userService.findById(userId);
        if(tempUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tempUser);
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User theUser) {
        theUser.setId(0);
        User dbUser = userService.save(theUser);
        return new ResponseEntity<>(theUser, HttpStatus.CREATED);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User theUser) {
        User tempUser = userService.findById(theUser.getId());
        if(tempUser == null) {
            return ResponseEntity.notFound().build();
        }
        tempUser = userService.save(theUser);
        return ResponseEntity.ok(tempUser);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        User tempUser = userService.findById(userId);
        if(tempUser == null) {
            return ResponseEntity.notFound().build();
        }

        userService.deleteById(userId);

        return ResponseEntity.ok("Deleted user id - " + userId);
    }
}
