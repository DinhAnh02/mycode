package vn.eledevo.vksbe.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import vn.eledevo.vksbe.entity.User;

public interface UserRepository extends BaseRepository<User, UUID>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsernameAndIsDeletedFalse(String username);

    Boolean existsByUsername(String username);
}
