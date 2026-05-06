package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin")
public class AdminController {



    private final UserService userService;
    private final RoleService roleService;
    private Logger logger = Logger.getLogger(getClass().getName());
    public AdminController(UserService theUserService, RoleService theRoleService) {
        userService = theUserService;
        roleService = theRoleService;
    }

    @GetMapping("/list")
    public ModelAndView listUsers(Model theModel) {
        ModelAndView mav = new ModelAndView("list-users");

        //get the users and roles from the db
        List<User> theUsers = userService.findAllUsers();
        List<Role> allRoles = roleService.findAllRoles();

        User theUser = new User();

        //add to the spring model
        mav.addObject("users", theUsers);
        mav.addObject("user", theUser);
        mav.addObject("roles", allRoles);


        return mav;
    }

    @PostMapping("/save")
    public ModelAndView saveUser(@ModelAttribute("user") User theUser) {
        ModelAndView mav = new ModelAndView("redirect:/admin/list");
        userService.save(theUser);
        return mav;
    }
    @GetMapping("/delete")
    public ModelAndView delete(@RequestParam("userId") int theId) {
        ModelAndView mav = new ModelAndView("redirect:/admin/list");
        userService.deleteById(theId);
        return mav;
    }

}
