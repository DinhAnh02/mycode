package vn.eledevo.vksbe.mapper;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import vn.eledevo.vksbe.dto.response.AccountResponse;
import vn.eledevo.vksbe.entity.Accounts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
        accountResponse.setCreateAt(e.getCreateAt());
        accountResponse.setUpdateAt(e.getUpdateAt());
        accountResponse.setCreateBy(e.getCreateBy());
        accountResponse.setUpdateBy(e.getUpdateBy());

        return accountResponse;
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
