package vn.eledevo.vksbe.dto.response.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class AccountResponse {
    String username;
    String fullName;
    Long roleId;
    String roleName;
    Long departmentId;
    String departmentName;
    Long organizationId;
    String organizationName;
    String status;
    String fromeDate;
    String toDate;
}
