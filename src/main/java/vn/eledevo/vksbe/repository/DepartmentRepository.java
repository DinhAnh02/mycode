package vn.eledevo.vksbe.repository;

import org.springframework.data.jpa.repository.Query;

import vn.eledevo.vksbe.entity.Departments;

public interface DepartmentRepository extends BaseRepository<Departments, Long> {

    @Query("SELECT d FROM Departments d JOIN d.accounts a WHERE a.id = :accountId")
    Departments findByAccountId(Long accountId);
}
