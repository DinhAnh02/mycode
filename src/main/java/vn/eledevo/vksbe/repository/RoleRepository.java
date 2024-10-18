package vn.eledevo.vksbe.repository;

import org.jetbrains.annotations.NotNull;

import vn.eledevo.vksbe.entity.Roles;

public interface RoleRepository extends BaseRepository<Roles, Long> {
    boolean existsById(@NotNull Long id);
}
