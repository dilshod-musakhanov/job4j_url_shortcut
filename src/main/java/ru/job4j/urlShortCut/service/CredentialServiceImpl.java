package ru.job4j.urlshortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.model.Credential;
import ru.job4j.urlshortcut.repository.CredentialRepository;
import ru.job4j.urlshortcut.util.generator.LoginGenerator;
import ru.job4j.urlshortcut.util.generator.PasswordGenerator;

@Service
@AllArgsConstructor
public class CredentialServiceImpl implements CredentialService {

    private final CredentialRepository credentialRepository;
    private final LoginGenerator loginGenerator;
    private final PasswordGenerator passwordGenerator;
    private final BCryptPasswordEncoder encoder;

    @Override
    public Credential createCredential() {
        String login = loginGenerator.generateLogin();
        String password = passwordGenerator.generatePassword();
        while (findByLogin(login)) {
            login = loginGenerator.generateLogin();
        }
        while (findByPassword(password)) {
            password = passwordGenerator.generatePassword();
        }
        return new Credential(login, password);
    }

    @Override
    public Credential save(Credential credential) {
        credential.setPassword(encoder.encode(credential.getPassword()));
        return credentialRepository.save(credential);
    }

    @Override
    public boolean findByLogin(String login) {
        return credentialRepository.findByLogin(login).isPresent();
    }

    @Override
    public boolean findByPassword(String password) {
        return credentialRepository.findByPassword(password).isPresent();
    }

}
