package web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import web.dao.UserDaoImpl;

@Controller
public class UserController {
    private final UserService userService;
    private final RoleService roleService;


    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/admin/users")
    public String listUsers(Model model) {
        List<User> listOfUsers = this.userService.listUsers();
        List<Role> roleList = this.roleService.getListRoles();

        User user;
        user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);

        model.addAttribute("listUsers", listOfUsers);
        model.addAttribute("roleList", roleList);

        return "usersboot";

//        return "users";
    }

    @PostMapping(value = "/admin/users/addrole")
    public String addRole(Role role) {
        this.roleService.addRole(role);
        return "redirect:/admin/users";
    }

    @PostMapping(value = "/admin/users/add")
    public String addUser(User user, String[] role) {
        //Сделать цикл добавления в сет ролей :todo
        Set<Role> roleSet = new HashSet<Role>();
        for (String r : role) {
            roleSet.add(this.roleService.getRoleByName(r));
        }
        user.setRoles(roleSet);


        if (user.getId() == 0) {
//        if (userService.getUserByName(user.getName()) == null) {
            this.userService.addUser(user);
        } else {
            this.userService.updateUser(user);
        }

        return "redirect:/admin/users";
    }

    @PostMapping(value = "/admin/remove")
    public String removeUser(User user) {
        this.userService.removeUser(user.getId());

        return "redirect:/admin/users";
    }

    @GetMapping(value = "/admin/edit/user")
    public String editUser(User user, Model model) {
        List<Role> roleList = this.roleService.getListRoles();
        model.addAttribute("roleList", roleList);

        model.addAttribute("user", this.userService.getUserById(user.getId()));

        return "edit";
    }

    @PostMapping(value = "/admin/edit")
    public String editUserPost(User user, String[] role) {

        Set<Role> roleSet = new HashSet<>();
        for (String r : role) {
            roleSet.add(this.roleService.getRoleByName(r));
        }
        user.setRoles(roleSet);
        this.userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/user")
    public String helloUser(Model model) {
        User user;
        user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
//        return "user";
        return "userboot";
    }

    @GetMapping("/adminuser")
    public String helloAdminUser(Model model) {
        User user;
        user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);

        return "adminuserboot";
    }




    @GetMapping(value = "hello")
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello! Java Mentor");
        messages.add("I'm Spring MVC-SECURITY application");
        messages.add("01/08/2020 ");
        model.addAttribute("messages", messages);
        return "hello";
    }

    @GetMapping(value = "/")
    public ModelAndView AllUsers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }


    @GetMapping(value = "login")
    public String loginPage() {
        return "login";
    }

}
