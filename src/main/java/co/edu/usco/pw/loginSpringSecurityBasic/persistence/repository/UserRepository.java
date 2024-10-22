package co.edu.usco.pw.loginSpringSecurityBasic.persistence.repository;

import co.edu.usco.pw.loginSpringSecurityBasic.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findUserEntityByUsername(String username);
}
