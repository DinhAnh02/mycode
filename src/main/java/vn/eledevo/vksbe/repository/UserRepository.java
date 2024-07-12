package vn.eledevo.vksbe.repository;

import vn.eledevo.vksbe.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends BaseRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}
