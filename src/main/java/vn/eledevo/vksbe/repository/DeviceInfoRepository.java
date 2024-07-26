package vn.eledevo.vksbe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import vn.eledevo.vksbe.entity.DeviceInfo;

public interface DeviceInfoRepository extends BaseRepository<DeviceInfo, Long>, JpaSpecificationExecutor<DeviceInfo> {
    Optional<DeviceInfo> findByDeviceUuid(String deviceUuid);

    Boolean existsByName(String name);
}
