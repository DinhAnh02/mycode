package vn.eledevo.vksbe.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import vn.eledevo.vksbe.entity.UserDeviceInfoKey;

public interface UserDeviceInfoKeyRepository
        extends BaseRepository<UserDeviceInfoKey, Long>, JpaSpecificationExecutor<UserDeviceInfoKey> {
    Optional<UserDeviceInfoKey> findByUserId(UUID uuid);
}
