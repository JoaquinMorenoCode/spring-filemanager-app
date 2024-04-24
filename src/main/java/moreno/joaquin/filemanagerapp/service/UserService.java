package moreno.joaquin.filemanagerapp.service;

import moreno.joaquin.filemanagerapp.exception.DuplicateEntryException;
import moreno.joaquin.filemanagerapp.model.Role;
import moreno.joaquin.filemanagerapp.model.User;
import moreno.joaquin.filemanagerapp.model.UserDTO;
import moreno.joaquin.filemanagerapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    public void createUser(UserDTO user){
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new DuplicateEntryException(user.getUsername());
        }
        User newUser = User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(newUser);


    }

    public Optional<User> getUser(String username){
       return userRepository.findByUsername(username);
    }



}
