package moreno.joaquin.filemanagerapp.controller;

import jakarta.persistence.GeneratedValue;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import moreno.joaquin.filemanagerapp.model.User;
import moreno.joaquin.filemanagerapp.model.UserDTO;
import moreno.joaquin.filemanagerapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Type;

@Controller
@RequestMapping({"/user"})
public class LoginController {
    @Autowired
    UserService userService;


    @GetMapping("/signup")
    public String getSignUp(Model model,  @CurrentSecurityContext(expression="authentication?.name")
            String currentUser, RedirectAttributes ra){

        if(!currentUser.equals("anonymousUser")){
            ra.addFlashAttribute("message", "Logged with " + currentUser);
            return "redirect:/files ";
        }

        model.addAttribute("user", UserDTO.builder().build());

        return "signup";


    }

    @GetMapping("/signin")
    public String getSingIn( Model model, @CurrentSecurityContext(expression="authentication?.name")
    String currentUser){
        //Redirect logged in users
        if(!currentUser.equals("anonymousUser")){
               return "redirect:/files ";
        }

//        HttpSession session = request.getSession(false);
//        String errorMessage = null;
//        if (session != null) {
//            AuthenticationException ex = (AuthenticationException) session
//                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
//            if (ex != null) {
//                errorMessage = ex.getMessage();
//            }
//        }
//        model.addAttribute("errorMessage", errorMessage);


        model.addAttribute("user", UserDTO.builder().build());

        return "signin";


    }

    @PostMapping("/signup")
    public String postSignUp(@Validated @ModelAttribute("user") UserDTO user, BindingResult bindingResult, Model model, RedirectAttributes ra){

        if(userService.getUser(user.getUsername()).isPresent()){
            bindingResult.rejectValue("username","DuplicateUsername");
        }

        if(!user.getPassword().equals(user.getConfirmPassword())){
            bindingResult.rejectValue("confirmPassword","PasswordMissmatch","Passwords do not match.");
            model.addAttribute("user", user);

            return "signup";

        }


        //Return validation errors
        if(bindingResult.hasErrors()){
            model.addAttribute("user", user);
            return "signup";
        }



//        User userToSave = User.builder().username(user.getUsername()).password(user.getPassword()).build();

        userService.createUser(user);

        return "redirect:/user/signin";


    }




}
