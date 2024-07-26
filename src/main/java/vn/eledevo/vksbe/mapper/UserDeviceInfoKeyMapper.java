package vn.eledevo.vksbe.mapper;

import org.mapstruct.Mapper;

import vn.eledevo.vksbe.dto.request.UserDeviceInfoKeyRequest;
import vn.eledevo.vksbe.dto.response.UserDeviceInfoKeyResponse;
import vn.eledevo.vksbe.entity.UserDeviceInfoKey;

@Mapper(componentModel = "spring")
public abstract class UserDeviceInfoKeyMapper
        extends BaseMapper<UserDeviceInfoKeyRequest, UserDeviceInfoKeyResponse, UserDeviceInfoKey> {}
