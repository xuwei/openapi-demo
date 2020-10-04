package co.zip.candidate.userapi.service.impl;

import co.zip.candidate.userapi.model.UserModel;
import co.zip.candidate.userapi.repository.UserRepository;
import co.zip.candidate.userapi.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<UserModel> listUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public UserModel createUser(UserModel user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<UserModel> getUser(String userId) {
        UUID id = UUID.fromString(userId);
        UserModel user = userRepository.findFirstById(id);
        return Optional.of(user);
    }

    @Override
    public Optional<UserModel> getUserByEmail(String email) {
        UserModel user = userRepository.findFirstByEmail(email);
        return Optional.of(user);
    }
}
