package co.zip.candidate.userapi.repository;

import co.zip.candidate.userapi.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Long> {
    AccountModel findAccountModelById(UUID id);
}
