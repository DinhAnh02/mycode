package vn.eledevo.vksbe.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.eledevo.vksbe.entity.Accounts;

public interface AccountRepository extends BaseRepository<Accounts, Long>, JpaSpecificationExecutor<Accounts> {
    @Query("SELECT a from Accounts a where a.username =:username and a.status = 'ACTIVE'")
    Optional<Accounts> findByUsernameAndActive(String username);

    @Query(value = "SELECT a.username, p.fullName, a.roleId, r.roleName," +
            "a.departmentId, d.departmentName, a.status, a.createAt, a.updateAt " +
            "FROM accounts a " +
            "JOIN profiles p ON a.username = p.username " +
            "JOIN roles r ON a.roleId = r.roleId " +
            "JOIN departments d ON a.departmentId = d.departmentId " +
            "WHERE (:username IS NULL OR a.username = :username) " +
            "AND (:fullName IS NULL OR p.fullName = :fullName) " +
            "AND (:roleId = 0 OR a.roleId = :roleId) " +
            "AND (:departmentId = 0 OR a.departmentId = :departmentId) " +
            "AND (:status IS NULL OR a.status = :status)",
            countQuery = "SELECT COUNT(*) " +
                    "FROM accounts a " +
                    "JOIN profiles p ON a.username = p.username " +
                    "JOIN roles r ON a.roleId = r.roleId " +
                    "JOIN departments d ON a.departmentId = d.departmentId " +
                    "WHERE (:username IS NULL OR a.username = :username) " +
                    "AND (:fullName IS NULL OR p.fullName = :fullName) " +
                    "AND (:roleId = 0 OR a.roleId = :roleId) " +
                    "AND (:departmentId = 0 OR a.departmentId = :departmentId) " +
                    "AND (:status IS NULL OR a.status = :status)",
            nativeQuery = true)
    Page<Object[]> getAccountList(
            @Param("username") String username,
            @Param("fullName") String fullName,
            @Param("roleId") int roleId,
            @Param("departmentId") int departmentId,
            @Param("status") String status,
            Pageable pageable
    );
}
