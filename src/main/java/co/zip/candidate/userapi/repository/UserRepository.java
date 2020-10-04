package co.zip.candidate.userapi.repository;

import co.zip.candidate.userapi.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    public UserModel findFirstByEmail(String email);
    public UserModel findFirstById(UUID id);
}
