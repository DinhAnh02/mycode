package vn.eledevo.vksbe.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.eledevo.vksbe.dto.model.account.AccountInfo;
import vn.eledevo.vksbe.dto.request.AccountRequest;
import vn.eledevo.vksbe.dto.response.account.AccountResponseByFilter;
import vn.eledevo.vksbe.entity.Accounts;

public interface AccountRepository extends BaseRepository<Accounts, Long>, JpaSpecificationExecutor<Accounts> {
    @Query("SELECT a from Accounts a where a.username =:username and a.status = 'ACTIVE'")
    Optional<Accounts> findByUsernameAndActive(String username);

    @Query(
            value = "SELECT a.username, p.fullName, a.roleId, r.roleName,"
                    + "a.departmentId, d.departmentName, a.status, a.createAt, a.updateAt "
                    + "FROM accounts a "
                    + "JOIN profiles p ON a.username = p.username "
                    + "JOIN roles r ON a.roleId = r.roleId "
                    + "JOIN departments d ON a.departmentId = d.departmentId "
                    + "WHERE (:username IS NULL OR a.username = :username) "
                    + "AND (:fullName IS NULL OR p.fullName = :fullName) "
                    + "AND (:roleId = 0 OR a.roleId = :roleId) "
                    + "AND (:departmentId = 0 OR a.departmentId = :departmentId) "
                    + "AND (:status IS NULL OR a.status = :status)",
            countQuery = "SELECT COUNT(*) " + "FROM accounts a "
                    + "JOIN profiles p ON a.username = p.username "
                    + "JOIN roles r ON a.roleId = r.roleId "
                    + "JOIN departments d ON a.departmentId = d.departmentId "
                    + "WHERE (:username IS NULL OR a.username = :username) "
                    + "AND (:fullName IS NULL OR p.fullName = :fullName) "
                    + "AND (:roleId = 0 OR a.roleId = :roleId) "
                    + "AND (:departmentId = 0 OR a.departmentId = :departmentId) "
                    + "AND (:status IS NULL OR a.status = :status)",
            nativeQuery = true)
    Page<Object[]> getAccountList(
            @Param("username") String username,
            @Param("fullName") String fullName,
            @Param("roleId") int roleId,
            @Param("departmentId") int departmentId,
            @Param("status") String status,
            Pageable pageable);

    @Query(
            """
			SELECT a.username, p.fullName, a.roles.id, r.name, a.departments.id, d.name,
					a.status, a.createAt, a.updateAt
			FROM Accounts a
			JOIN a.profile p
			JOIN a.roles r
			JOIN a.departments d
			WHERE (:#{#filter.username} IS NULL OR a.username like %:#{#filter.username}%)
			AND (:#{#filter.fullName} IS NULL OR p.fullName like %:#{#filter.fullName}%)
			AND (:#{#filter.roleId} = 0 OR a.roles.id = :#{#filter.roleId})
			AND (:#{#filter.departmentId} = 0 OR a.departments.id = :#{#filter.departmentId})
			AND (:#{#filter.status} IS NULL OR a.status = :#{#filter.status})
			""")
    Page<Object[]> getAccountList(AccountRequest filter, Pageable pageable);

    @Query("SELECT new vn.eledevo.vksbe.dto.model.account.AccountInfo(a.roles.code, a.departments.id,"
			+ "a.isConnectComputer, a.isConnectUsb) from Accounts a where a.username =:username")
    AccountInfo findByUsername(String username);
}
