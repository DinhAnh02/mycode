package vn.eledevo.vksbe.mapper;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import vn.eledevo.vksbe.dto.response.usb.UsbResponse;
import vn.eledevo.vksbe.entity.Usbs;

@Mapper(componentModel = "spring")
public interface UsbMapper {
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "mapLocalDateToLong")
    @Mapping(source = "updatedAt", target = "updatedAt", qualifiedByName = "mapLocalDateToLong")
    UsbResponse toTarget(Usbs source);

    List<UsbResponse> toTarget(List<Usbs> listEntity);

    @Named("mapLocalDateToLong")
    default Long mapLocalDateToLong(LocalDate localDate) {
        return localDate != null
                ? localDate.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
                : null;
    }
}
