package vn.eledevo.vksbe.service.usb;

import vn.eledevo.vksbe.dto.request.UsbRequest;
import vn.eledevo.vksbe.dto.response.Result;
import vn.eledevo.vksbe.exception.ApiException;

public interface UsbService {
    String createUsbToken(String username) throws Exception;

    Result getUsbByFilter(UsbRequest usbRequest, Integer currentPage, Integer limit) throws ApiException;
}
