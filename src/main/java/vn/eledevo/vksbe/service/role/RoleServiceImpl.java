package vn.eledevo.vksbe.service.role;

import vn.eledevo.vksbe.entity.Roles;
import vn.eledevo.vksbe.repository.RoleRepository;

import java.util.Optional;

public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Boolean roleNameChangeDetector(Long roleId, String roleName) {
        Optional<Roles> role = roleRepository.findById(roleId);
        return role.isPresent() && role.get().getName().equals(roleName);
    }
}
