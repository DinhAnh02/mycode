package vn.eledevo.vksbe.dto.response.account;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountFilterCaseResponse {
    Long id;
    String username;
    String fullName;
    String avatar;
    String roleName;
    String status;
    Boolean isChecked;

    public AccountFilterCaseResponse(
            Long id, String username, String fullName, String avatar, String roleName, Boolean isChecked) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.avatar = avatar;
        this.roleName = roleName;
        this.isChecked = isChecked;
    }

    public AccountFilterCaseResponse(
            Long id, String username, String fullName, String avatar, String roleName, String status) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.avatar = avatar;
        this.roleName = roleName;
        this.status = status;
    }
}
