    package vn.eledevo.vksbe.mapper;

    import lombok.Builder;
    import org.mapstruct.Mapper;

    import org.mapstruct.factory.Mappers;
    import vn.eledevo.vksbe.dto.model.UserQuery;
    import vn.eledevo.vksbe.dto.request.UserRequest;
    import vn.eledevo.vksbe.dto.response.UserResponse;
    import vn.eledevo.vksbe.entity.User;

    import java.util.List;
    @Builder
    @Mapper(componentModel = "spring")
    public abstract class UserMapper extends BaseMapper<UserRequest, UserResponse, User> {
        private static UserResponse userResponse;

        public UserResponse mapUserQuery(UserQuery userQuery){
            userResponse = UserResponse.builder()
                    .id(userQuery.getId())
                    .fullName(userQuery.getFullName())
                    .username(userQuery.getUsername())
                    .createdAt(userQuery.getCreatedAt())
                    .updatedAt(userQuery.getUpdatedAt())
                    .createdBy(userQuery.getCreatedBy())
                    .updatedBy(userQuery.getUpdatedBy())
                    .build();
            return userResponse;
        }
    }
