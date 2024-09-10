package vn.eledevo.vksbe.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.eledevo.vksbe.dto.model.UserDeviceInfoKeyQuery;
import vn.eledevo.vksbe.entity.UserDeviceInfoKey;

public interface UserDeviceInfoKeyRepository
        extends BaseRepository<UserDeviceInfoKey, Long>, JpaSpecificationExecutor<UserDeviceInfoKey> {
    Optional<UserDeviceInfoKey> findByUserId(UUID uuid);

    @Query("SELECT new vn.eledevo.vksbe.dto.model.UserDeviceInfoKeyQuery(" + "udk.id, "
            + "udk.deviceInfoId, "
            + "u.id, "
            + "udk.keyUsb, "
            + "di.deviceUuid, "
            + "CASE WHEN udk.isDeleted IS NULL THEN FALSE ELSE TRUE END) "
            + "FROM UserDeviceInfoKey udk "
            + "JOIN udk.deviceInfo di "
            + "JOIN udk.user u "
            + "WHERE u.id = :userId")
    Optional<UserDeviceInfoKeyQuery> findUserDeviceInfoKeyByUserId(@Param("userId") UUID userId);
}
