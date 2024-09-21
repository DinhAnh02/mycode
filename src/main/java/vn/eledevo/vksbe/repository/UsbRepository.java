package vn.eledevo.vksbe.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import vn.eledevo.vksbe.dto.request.UsbRequest;
import vn.eledevo.vksbe.dto.response.usb.UsbResponseFilter;
import vn.eledevo.vksbe.entity.Usbs;

public interface UsbRepository extends BaseRepository<Usbs, Long> {
    Optional<Usbs> findByAccounts_Id(Long accountId);

    @Query(
            "SELECT new vn.eledevo.vksbe.dto.response.usb.UsbResponseFilter(u.id, u.status, u.name, p.fullName, u.createAt) "
                    + "FROM Usbs u "
                    + "JOIN Accounts a ON a.id = u.accounts.id "
                    + "JOIN Profiles p ON a.id = p.accounts.id "
                    + "WHERE (u.usbCode like %:#{#usbRequest.usbCode}% OR :#{#usbRequest.usbCode} IS NULL) "
                    + "AND (p.fullName like %:#{#usbRequest.createByAccountName}% OR :#{#usbRequest.createByAccountName} IS NULL) "
                    + "AND (u.status like %:#{#usbRequest.status}% OR :#{#usbRequest.status} IS NULL) "
                    + "AND u.createAt BETWEEN :#{#usbRequest.fromDate} AND :#{#usbRequest.toDate}")
    Page<UsbResponseFilter> getUsbDeviceList(UsbRequest usbRequest, Pageable pageable);

    @Query(
            "select u from Usbs u inner join Accounts a on u.accounts.id = a.id where a.id =:accountId and u.status = 'CONNECTED'")
    Optional<Usbs> usbByAccountAndConnect(Long accountId);
}
