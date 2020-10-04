package co.zip.candidate.userapi.service.impl;

import co.zip.candidate.userapi.exception.NonUniqueException;
import co.zip.candidate.userapi.model.UserModel;
import co.zip.candidate.userapi.repository.UserRepository;
import co.zip.candidate.userapi.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Cacheable("user")
    @Override
    public List<UserModel> listUsers() {
        return userRepository.findAll();
    }

    @CacheEvict(cacheNames = "user", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserModel createUser(UserModel user) {
        UserModel existingUser = getUserByEmail(user.getEmail());
        if (existingUser != null) { throw new NonUniqueException(); }
        return userRepository.save(user);
    }

    @Cacheable("user")
    @Override
    public UserModel getUser(String userId) {
        UUID id = UUID.fromString(userId);
        UserModel user = userRepository.findUserModelById(id);
        return user;
    }

    @Cacheable("user")
    @Override
    public UserModel getUserByEmail(String email) {
        UserModel user = userRepository.findFirstByEmail(email);
        return user;
    }

    @Override
    public Page<UserModel> queryUsers(int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return userRepository.findAll(paging);
    }
}
