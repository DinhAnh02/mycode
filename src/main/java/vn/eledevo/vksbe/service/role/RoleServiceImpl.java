package vn.eledevo.vksbe.service.role;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import vn.eledevo.vksbe.entity.Roles;
import vn.eledevo.vksbe.repository.RoleRepository;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {
    final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Boolean roleNameChangeDetector(Long roleId, String roleName) {
        Optional<Roles> role = roleRepository.findById(roleId);
        return role.isPresent() && role.get().getName().equals(roleName);
    }
}
