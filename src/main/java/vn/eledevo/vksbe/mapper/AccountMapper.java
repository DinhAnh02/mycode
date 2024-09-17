package vn.eledevo.vksbe.mapper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import vn.eledevo.vksbe.dto.response.AccountResponse;
import vn.eledevo.vksbe.entity.Accounts;

@Component
public class AccountMapper {
    public AccountResponse toResponse(Accounts e) {
        if (Objects.isNull(e)) {
            return null;
        }

        AccountResponse accountResponse = new AccountResponse();

        accountResponse.setId(e.getId());
        accountResponse.setUsername(e.getUsername());
        accountResponse.setStatus(e.getStatus());
        accountResponse.setIsConditionLogin1(e.getIsConditionLogin1());
        accountResponse.setIsConditionLogin2(e.getIsConditionLogin2());
        accountResponse.setIsConnectComputer(e.getIsConnectComputer());
        accountResponse.setIsConnectUsb(e.getIsConnectUsb());
        accountResponse.setCreateAt(mapLocalDateTimeToEpochTimestamp(e.getCreateAt()));
        accountResponse.setUpdateAt(mapLocalDateTimeToEpochTimestamp(e.getUpdateAt()));
        accountResponse.setCreateBy(e.getCreateBy());
        accountResponse.setUpdateBy(e.getUpdateBy());

        return accountResponse;
    }

    private Long mapLocalDateTimeToEpochTimestamp(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toEpochSecond(ZoneOffset.UTC) : null;
    }

    public List<AccountResponse> toListResponse(List<Accounts> eList) {
        if (CollectionUtils.isEmpty(eList)) {
            return Collections.emptyList();
        }

        List<AccountResponse> list = new ArrayList<AccountResponse>(eList.size());
        for (Accounts accounts : eList) {
            list.add(toResponse(accounts));
        }

        return list;
    }
}
