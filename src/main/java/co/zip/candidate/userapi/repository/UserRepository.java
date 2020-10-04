package co.zip.candidate.userapi.repository;

import co.zip.candidate.userapi.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findFirstByEmail(String email);
    UserModel findUserModelById(UUID id);
}
