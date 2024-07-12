package vn.eledevo.vksbe.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.eledevo.vksbe.dto.response.PageResponse;

import java.util.List;

public abstract class BaseMapper<Rq, Rp, T> {
    public abstract T toEntity(Rq rq);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract T toEntityUpdate(Rq rq, @MappingTarget T t);

    public abstract Rp toResponse(T t);

    public abstract List<Rp> toListResponse(List<T> tList);

    public PageResponse<Rp> toPageResponse(List<T> tList, int total) {
        List<Rp> rpList = toListResponse(tList);
        return new PageResponse<>(total, rpList);
    }
}
