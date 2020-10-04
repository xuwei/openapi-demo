package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<UserModel> listUsers();
    UserModel createUser(UserModel user);
    UserModel getUser(String userId);
    UserModel getUserByEmail(String email);
    Page<UserModel> queryUsers(int pageNo, int pageSize);
}
