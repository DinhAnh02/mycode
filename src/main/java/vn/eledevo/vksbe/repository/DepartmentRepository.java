package vn.eledevo.vksbe.repository;

import vn.eledevo.vksbe.entity.Departments;

public interface DepartmentRepository extends BaseRepository<Departments, Long> {

    Departments findByAccounts_Id(Long accountId);
    boolean existsDepartmentsByName(String name);
}
