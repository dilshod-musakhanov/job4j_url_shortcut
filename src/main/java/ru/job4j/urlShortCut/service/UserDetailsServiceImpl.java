package ru.job4j.urlshortcut.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.model.Credential;
import ru.job4j.urlshortcut.repository.CredentialRepository;

import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
@AllArgsConstructor
@Log4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CredentialRepository credentialRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Credential> credentialOptional = credentialRepository.findByLogin(username);
        if (credentialOptional.isEmpty()) {
            log.error(String.format("Invalid login credentials - %s", username));
            throw new UsernameNotFoundException(username);
        }
        String password = credentialOptional.get().getPassword();
        return new User(username, password, emptyList());
    }
}
