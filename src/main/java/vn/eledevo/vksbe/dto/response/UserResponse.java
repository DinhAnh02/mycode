package vn.eledevo.vksbe.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserResponse {
    UUID id;
    String username;
    String fullName;
    LocalDateTime createdAt;
    UUID createdBy;
    LocalDateTime updatedAt;
    UUID updatedBy;
}
