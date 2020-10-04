package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUserService {
    public Page<UserModel> listUsers(Pageable pageable);
    public UserModel createUser(UserModel user);
    public Optional<UserModel> getUser(String userId);
    public Optional<UserModel> getUserByEmail(String email);
}
