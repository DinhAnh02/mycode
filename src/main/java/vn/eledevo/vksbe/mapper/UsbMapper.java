package vn.eledevo.vksbe.mapper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import vn.eledevo.vksbe.dto.response.usb.UsbResponse;
import vn.eledevo.vksbe.entity.Usbs;

@Mapper(componentModel = "spring")
public interface UsbMapper {
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "mapLocalDateTimeToLong")
    @Mapping(source = "updatedAt", target = "updatedAt", qualifiedByName = "mapLocalDateTimeToLong")
    UsbResponse toTarget(Usbs source);

    List<UsbResponse> toTarget(List<Usbs> listEntity);

    @Named("mapLocalDateTimeToLong")
    default Long mapLocalDateTimeToLong(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli() : null;
    }
}
