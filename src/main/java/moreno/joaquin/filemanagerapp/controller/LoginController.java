package moreno.joaquin.filemanagerapp.controller;

import jakarta.persistence.GeneratedValue;
import moreno.joaquin.filemanagerapp.model.UserDTO;
import moreno.joaquin.filemanagerapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/user"})
public class LoginController {
    @Autowired
    UserService userService;

    @GetMapping("/signup")
    public String getSignUp(Model model){

        model.addAttribute("user", UserDTO.builder().build());

        return "signup";


    }

    @GetMapping("/signin")
    public String getSingIn(Model model){

        model.addAttribute("user", UserDTO.builder().build());

        return "signin";


    }

    @PostMapping("/signup")
    public String PostSignUp(UserDTO user){

        userService.createUser(user);

        return "redirect:/user/signin";


    }




}
