package com.mindhub.demo.configuration;


import com.mindhub.demo.models.Client;
import com.mindhub.demo.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {


    @Autowired
    ClientRepository clientRepository;


    //-------------------------------
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    //-------------------------------
    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(inputName-> {

             Optional<Client> client = clientRepository.findByEmail(inputName);
            //los optional devuelven si esta presente o no
            if (client.isPresent()) {

                return new User(client.get().getEmail(), client.get().getPassword(),

                        AuthorityUtils.createAuthorityList("CLIENT"));

            } else {

                throw new UsernameNotFoundException("Unknown user: " + inputName);

            }
        });
    }


}
