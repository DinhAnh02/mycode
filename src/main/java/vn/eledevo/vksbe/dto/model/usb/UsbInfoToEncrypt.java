package vn.eledevo.vksbe.dto.model.usb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.eledevo.vksbe.entity.Computers;

import java.util.List;

@Setter
@Getter
@Builder
public class UsbInfoToEncrypt {
    String pin;
    String keyUsb;
    List<String> listDevices;
    String usbCode;
    String usbVendorCode;
}
