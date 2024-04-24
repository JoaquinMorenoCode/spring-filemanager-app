package moreno.joaquin.filemanagerapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig   {

    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{

        http.formLogin(form ->{
            form.loginPage("/user/signin").loginProcessingUrl("/login").permitAll();
            form.defaultSuccessUrl("/files");

        });




        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(rq -> {
            rq.requestMatchers("/home/**").permitAll();
            rq.requestMatchers("/user/**").permitAll();
            rq.requestMatchers("/").permitAll();
            rq.requestMatchers("/files/**").authenticated();

            rq.anyRequest().authenticated();



        });

//        http.rememberMe(key ->{
//            key.rememberMeParameter("rememberme").tokenValiditySeconds(20).key("supersecretkey");
//        });

//        http.exceptionHandling((Customizer<ExceptionHandlingConfigurer<HttpSecurity>>) new LoginUrlAuthenticationEntryPoint("/files"));
        http.authenticationProvider(authenticationProvider);

        return http.build();

    }
}
