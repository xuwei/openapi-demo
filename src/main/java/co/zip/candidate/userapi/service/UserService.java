package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.model.UserModel;
import co.zip.candidate.userapi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserModel> listUsers() {
        return null;
    }

    public UserModel createUser(UserModel user) {
        return userRepository.save(user);
    }

    @Override
    public UserModel getUser(Long userId) {
        return null;
    }
}
