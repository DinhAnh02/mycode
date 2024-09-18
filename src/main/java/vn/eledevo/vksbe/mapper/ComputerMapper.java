package vn.eledevo.vksbe.mapper;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import vn.eledevo.vksbe.dto.response.computer.ComputerResponse;
import vn.eledevo.vksbe.entity.Computers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class ComputerMapper {

    public ComputerResponse toResponse(Computers e) {
        if (Objects.isNull(e)) {
            return null;
        }

        ComputerResponse.ComputerResponseBuilder computerResponse = ComputerResponse.builder();

        computerResponse.id(e.getId());
        computerResponse.name(e.getName());
        computerResponse.code(e.getCode());
        computerResponse.status(e.getStatus());
        computerResponse.brand(e.getBrand());
        computerResponse.type(e.getType());
        computerResponse.note(e.getNote());
        computerResponse.createAt(e.getCreateAt());
        computerResponse.updateAt(e.getUpdateAt());
        computerResponse.createBy(e.getCreateBy());
        computerResponse.updateBy(e.getUpdateBy());

        return computerResponse.build();
    }

    public List<ComputerResponse> toListResponse(List<Computers> eList) {
        if (CollectionUtils.isEmpty(eList)) {
            return Collections.emptyList();
        }

        List<ComputerResponse> list = new ArrayList<>(eList.size());
        for (Computers computers : eList) {
            list.add(toResponse(computers));
        }

        return list;
    }
}
