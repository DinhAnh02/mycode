package vn.eledevo.vksbe.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.eledevo.vksbe.dto.model.account.AccountInfo;
import vn.eledevo.vksbe.dto.model.account.UserInfo;
import vn.eledevo.vksbe.dto.request.AccountInactive;
import vn.eledevo.vksbe.dto.request.AccountProfile;
import vn.eledevo.vksbe.dto.request.AccountRequest;
import vn.eledevo.vksbe.dto.response.account.AccountResponseByFilter;
import vn.eledevo.vksbe.entity.Accounts;

public interface AccountRepository extends BaseRepository<Accounts, Long>, JpaSpecificationExecutor<Accounts> {
    @Query(
            "SELECT a,r.code from Accounts a inner join Roles r on a.roles.id = r.id  where a.username =:username and a.status = 'ACTIVE'")
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
            "SELECT new vn.eledevo.vksbe.dto.response.account.AccountResponseByFilter(a.id, a.username, p.fullName, r.name, d.name, o.name, a.status, a.createAt, a.updateAt, false , false) "
                    + "FROM Accounts a "
                    + "JOIN Profiles p ON p.accounts.id = a.id "
                    + "JOIN Roles r ON r.id = a.roles.id "
                    + "JOIN Departments d ON d.id = a.departments.id "
                    + "JOIN Organizations o ON 1=1 "
                    + "WHERE (:#{#filter.username} IS NULL OR a.username like %:#{#filter.username}%) "
                    + "AND (:#{#filter.fullName} IS NULL OR p.fullName like %:#{#filter.fullName}%) "
                    + "AND (:#{#filter.roleId} = 0 OR a.roles.id = :#{#filter.roleId}) "
                    + "AND (:#{#filter.departmentId} = 0 OR a.departments.id = :#{#filter.departmentId}) "
                    + "AND (:#{#filter.organizationId} = 0 OR o.id= :#{#filter.organizationId}) "
                    + "AND (:#{#filter.status} IS NULL OR a.status like %:#{#filter.status}%) "
                    + "AND a.createAt BETWEEN (:#{#filter.fromDate}) AND :#{#filter.toDate}")
    Page<AccountResponseByFilter> getAccountList(AccountRequest filter, Pageable pageable);

    @Query("SELECT new vn.eledevo.vksbe.dto.model.account.AccountInfo(a.roles.code, a.departments.id,"
            + "a.isConnectComputer, a.isConnectUsb) from Accounts a where a.username =:username")
    AccountInfo findByUsername(String username);

    @Query("SELECT new vn.eledevo.vksbe.dto.request.AccountInactive(a.id, a.roles.code,"
            + "a.status) from Accounts a where a.username =:username")
    Optional<AccountInactive> findByUsernameActive(String username);

    @Query("SELECT new vn.eledevo.vksbe.dto.request.AccountProfile(a.id, a.roles.code," + "p.fullName) from Accounts a "
            + "join Profiles p on a.id = p.accounts.id where a.username =:username")
    AccountProfile findByUsernameAndProfile(String username);

    @Query(
            "SELECT a from Accounts  a where a.roles.code=:roleCode and a.departments.id=:departmentId and a.status=:accountStatus")
    Optional<Accounts> findByDepartment(Long departmentId, String roleCode, String accountStatus);

    boolean existsByUsername(String username);

    @Query(
            "SELECT new vn.eledevo.vksbe.dto.model.account.UserInfo(p.avatar, p.fullName, p.gender, p.phoneNumber, a.isConditionLogin1, a.isConditionLogin2, r.code, d.code) "
                    + "FROM Accounts a "
                    + "JOIN a.roles r "
                    + "JOIN a.departments d "
                    + "JOIN a.profile p "
                    + "WHERE a.id = :accountId")
    UserInfo findAccountProfileById(@Param("accountId") Long accountId);
}
