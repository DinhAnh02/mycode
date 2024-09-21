package vn.eledevo.vksbe.service.usb;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.constant.ErrorCode;
import vn.eledevo.vksbe.dto.request.UsbRequest;
import vn.eledevo.vksbe.dto.response.Result;
import vn.eledevo.vksbe.dto.response.usb.UsbResponseFilter;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.repository.UsbRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UsbServiceImpl implements UsbService {
    UsbRepository usbRepository;

    @Override
    public Result getUsbByFilter(UsbRequest usbRequest, Integer currentPage, Integer limit) throws ApiException {
        if (usbRequest.getFromDate() == null) {
            usbRequest.setFromDate(LocalDateTime.of(1900, 1, 1, 0, 0));
        }
        if (usbRequest.getToDate() == null) {
            usbRequest.setToDate(LocalDateTime.now());
        }
        if (usbRequest.getFromDate().isAfter(usbRequest.getToDate())) {
            throw new ApiException(ErrorCode.CHECK_FROM_DATE);
        }
        Pageable pageable = PageRequest.of(currentPage - 1, limit);
        Page<UsbResponseFilter> page = usbRepository.getUsbDeviceList(usbRequest, pageable);

        return new Result(page.getContent(), page.getTotalElements());
    }
}
