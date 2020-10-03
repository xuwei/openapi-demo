package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.model.UserModel;

import java.util.List;

public interface IUserService {
    public List<UserModel> listUsers();
    public UserModel createUser(UserModel user);
    public UserModel getUser(Long id);
}
