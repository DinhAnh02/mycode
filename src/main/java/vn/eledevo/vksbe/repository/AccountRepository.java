package vn.eledevo.vksbe.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.eledevo.vksbe.dto.model.account.AccountQueryToFilter;
import vn.eledevo.vksbe.dto.model.account.OldPositionAccInfo;
import vn.eledevo.vksbe.dto.model.account.UserInfo;
import vn.eledevo.vksbe.dto.request.AccountActive;
import vn.eledevo.vksbe.dto.request.AccountRequest;
import vn.eledevo.vksbe.entity.Accounts;

public interface AccountRepository extends BaseRepository<Accounts, Long>, JpaSpecificationExecutor<Accounts> {
    @Query("SELECT a,r.code from Accounts a inner join Roles r on a.roles.id = r.id  where a.username =:username")
    Optional<Accounts> findAccountInSystem(String username);

    @Query("SELECT new vn.eledevo.vksbe.dto.model.account.AccountQueryToFilter("
            + "a.id, a.username, p.fullName, r.name, r.code, r.id, d.id, d.name, o.id,"
            + "o.name, a.status, a.isConnectComputer, "
            + "a.isConnectUsb, a.createdAt, a.updatedAt, false , false, false , false) "
            + "FROM Accounts a "
            + "LEFT JOIN Profiles p ON p.accounts.id = a.id "
            + "LEFT JOIN Roles r ON r.id = a.roles.id "
            + "AND (:isBoss = true OR (r.code = 'TRUONG_PHONG' OR r.code = 'PHO_PHONG' OR r.code = 'KIEM_SAT_VIEN')) "
            + "LEFT JOIN Departments d ON d.id = a.departments.id "
            + "LEFT JOIN Organizations o ON 1=1 "
            + "WHERE (:#{#filter.username} IS NULL OR a.username LIKE %:#{#filter.username}%) "
            + "AND (:#{#filter.fullName} IS NULL OR p.fullName LIKE %:#{#filter.fullName}%) "
            + "AND (:#{#filter.roleId} = 0 OR a.roles.id = :#{#filter.roleId}) "
            + "AND (:#{#filter.departmentId} = 0 OR a.departments.id = :#{#filter.departmentId}) "
            + "AND (:#{#filter.organizationId} = 0 OR o.id = :#{#filter.organizationId}) "
            + "AND (:#{#filter.status} IS NULL OR a.status LIKE %:#{#filter.status}%) "
            + "AND a.createdAt >= :#{#filter.fromDate.atStartOfDay()} "
            + "AND a.createdAt <= :#{#filter.toDate.atTime(23, 59, 59)}")
    Page<AccountQueryToFilter> getAccountList(AccountRequest filter, Boolean isBoss, Pageable pageable);

    Accounts findAccountsByUsername(String username);

    @Query("SELECT new vn.eledevo.vksbe.dto.request.AccountActive(a.id, a.roles.code,"
            + "a.status,a.departments.id) from Accounts a where a.username =:username")
    Optional<AccountActive> findByUsernameActive(String username);

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
    Optional<UserInfo> findAccountProfileById(@Param("accountId") Long accountId);

    @Query("SELECT new vn.eledevo.vksbe.dto.model.account.OldPositionAccInfo(a.id, p.fullName, a.username) "
            + "FROM Accounts a "
            + "JOIN a.profile p "
            + "WHERE a.departments.id = :departmentId "
            + "AND a.status = 'ACTIVE' "
            + "AND a.roles.id IN(1, 4)")
    OldPositionAccInfo getOldPositionAccInfo(@Param("departmentId") Long departmentId);
}
