package vn.eledevo.vksbe.service.usb;

import vn.eledevo.vksbe.dto.request.UsbRequest;
import vn.eledevo.vksbe.dto.request.usb.UsbToken;
import vn.eledevo.vksbe.dto.request.usb.UsbTokenInfo;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.dto.response.usb.UsbResponseFilter;
import vn.eledevo.vksbe.exception.ApiException;

public interface UsbService {
    String createUsbToken(Long idAccount, UsbToken usbToken) throws Exception;

    ResponseFilter<UsbResponseFilter> getUsbByFilter(UsbRequest usbRequest, Integer currentPage, Integer limit)
            throws ApiException;

    String createUsb(UsbTokenInfo usbToken);
}
