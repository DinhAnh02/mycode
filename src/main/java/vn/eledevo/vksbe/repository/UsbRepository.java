package vn.eledevo.vksbe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import vn.eledevo.vksbe.dto.request.UsbRequest;
import vn.eledevo.vksbe.dto.response.ListUsbResponse;
import vn.eledevo.vksbe.entity.Usbs;

public interface UsbRepository extends BaseRepository<Usbs, Long> {
    @Query("SELECT u FROM Usbs u WHERE u.accounts.id = :accountId")
    Optional<Usbs> findByAccounts_Id(Long accountId);

    Usbs findByAccountsId(Long accountId);

    @Query(
            value = "SELECT u.id, u.status, u.name, p.full_name, u.createadAt " +"FROM usb u "
                    + "JOIN accounts a ON c.account_id = a.id "
                    + "JOIN profiles p ON a.id = p.account_id"
                    + "WHERE u.usbCode = :#{#usbRequest.usbCode}"
                    + "AND p.fullName = :#{#usbRequest.fullName}"
                    + "AND u.status IS NOT NULL "
                    + "AND u.status <> '' "
                    + "AND u.status = :#{#usbRequest.status}",
            nativeQuery = true)
    List<ListUsbResponse> getUsbDeviceList(UsbRequest usbRequest);
}
