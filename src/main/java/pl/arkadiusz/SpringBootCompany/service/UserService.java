package pl.arkadiusz.SpringBootCompany.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.arkadiusz.SpringBootCompany.model.User;
import pl.arkadiusz.SpringBootCompany.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User findbyLogin(String login) {
        return userRepository.getUserByUsername(login);
    }

}
