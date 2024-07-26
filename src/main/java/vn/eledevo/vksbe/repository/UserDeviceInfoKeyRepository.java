package vn.eledevo.vksbe.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.eledevo.vksbe.entity.UserDeviceInfoKey;

import java.util.Optional;
import java.util.UUID;

public interface UserDeviceInfoKeyRepository extends BaseRepository<UserDeviceInfoKey,Long>, JpaSpecificationExecutor<UserDeviceInfoKey> {
	Optional<UserDeviceInfoKey> findByUserId (UUID uuid);
}
