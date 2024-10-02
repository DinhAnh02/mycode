package vn.eledevo.vksbe.service.usb;

import static vn.eledevo.vksbe.constant.ErrorCode.*;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import vn.eledevo.vksbe.constant.ErrorCode;
import vn.eledevo.vksbe.constant.ErrorCodes.AccountErrorCode;
import vn.eledevo.vksbe.constant.ErrorCodes.SystemErrorCode;
import vn.eledevo.vksbe.constant.ErrorCodes.UsbErrorCode;
import vn.eledevo.vksbe.constant.Status;
import vn.eledevo.vksbe.dto.request.AccountActive;
import vn.eledevo.vksbe.dto.request.DataChange;
import vn.eledevo.vksbe.dto.request.UsbRequest;
import vn.eledevo.vksbe.dto.request.usb.UsbToken;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.dto.response.usb.UsbResponseFilter;
import vn.eledevo.vksbe.entity.Accounts;
import vn.eledevo.vksbe.entity.Computers;
import vn.eledevo.vksbe.entity.Usbs;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.repository.AccountRepository;
import vn.eledevo.vksbe.repository.UsbRepository;
import vn.eledevo.vksbe.service.ChangeData;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UsbServiceImpl implements UsbService {
    UsbRepository usbRepository;
    AccountRepository accountRepository;

    @Override
    public ResponseFilter<UsbResponseFilter> getUsbByFilter(UsbRequest usbRequest, Integer currentPage, Integer limit)
            throws ApiException {
        if (usbRequest.getFromDate() == null) {
            usbRequest.setFromDate(LocalDate.of(1900, 1, 1));
        }
        if (usbRequest.getToDate() == null) {
            usbRequest.setToDate(LocalDate.now());
        }
        if (usbRequest.getFromDate().isAfter(usbRequest.getToDate())) {
            throw new ApiException(ErrorCode.CHECK_FROM_DATE);
        }
        Pageable pageable = PageRequest.of(currentPage - 1, limit);
        Page<UsbResponseFilter> page = usbRepository.getUsbDeviceList(usbRequest, pageable);

        return new ResponseFilter<>(
                page.getContent(),
                (int) page.getTotalElements(),
                page.getSize(),
                page.getNumber(),
                page.getTotalPages());
    }

    @Override
    public String createUsbToken(Long idAccount, UsbToken usbToken) throws Exception {
        // Sử dụng ClassLoader để lấy đường dẫn file zip từ tài nguyên
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("AppUsb/app_usb.zip");
        if (resource == null) {
            throw new ApiException(UsbErrorCode.FOLDER_NOT_FOUND, "Tài nguyên app_usb.zip không tồn tại");
        }
        // Copy file zip từ tài nguyên ra một thư mục tạm để giải nén
        Path tempDir = Files.createTempDirectory("usbTemp");
        Path zipFilePath = tempDir.resolve("app_usb.zip");
        Files.copy(Paths.get(resource.toURI()), zipFilePath, StandardCopyOption.REPLACE_EXISTING);

        Path unzippedFolderPath = tempDir.resolve("unzipped");
        Files.createDirectories(unzippedFolderPath);
        unzipFile(zipFilePath.toString(), unzippedFolderPath.toString());

        Optional<Accounts> account = accountRepository.findById(idAccount);
        if(account.isEmpty()){
            throw new ApiException(AccountErrorCode.ACCOUNT_NOT_FOUND);
        }
        if(Boolean.TRUE.equals(account.get().getIsConnectUsb())){
            throw new ApiException(AccountErrorCode.ACCOUNT_LINKED_USB);
        }
        Optional<Usbs> usbInfo = usbRepository.usbTokenNew(usbToken.getUsbCode(),usbToken.getUsbVendorCode());
        if(usbInfo.isEmpty()){
            throw new ApiException(UsbErrorCode.USB_NOT_FOUND);
        }
        if(usbInfo.get().getKeyUsb() != null && !usbInfo.get().getKeyUsb().isEmpty()){
            throw new ApiException(UsbErrorCode.USB_IS_CONNECTED);
        }
        UUID keyUsb = UUID.randomUUID();
        account.get().setIsConnectUsb(Boolean.TRUE);
        Accounts accountUpdate = accountRepository.save(account.get());
        usbInfo.get().setKeyUsb(keyUsb.toString());
        usbInfo.get().setStatus(Status.CONNECTED.name());
        usbInfo.get().setAccounts(accountUpdate);
        usbRepository.save(usbInfo.get());
        accountUpdate.setUsb(usbInfo.get());
        accountRepository.save(accountUpdate);
        List<Computers> computers = accountUpdate.getComputers();
        String[] computerCode =
                        computers.stream().map(Computers::getCode).toArray(String[]::new);
        //Thông tin cần mã hoá
        DataChange usbInfoToEncrypt = DataChange.builder()
                        .maPin(accountUpdate.getPin())
                        .keyUsb(accountUpdate.getUsb().getKeyUsb())
                        .listDevices(computerCode)
                        .usbVendorCode(accountUpdate.getUsb().getUsbVendorCode())
                        .usbCode(accountUpdate.getUsb().getUsbCode())
                        .build();
        // Mã hóa chuỗi JSON
        String encryptedData = ChangeData.encrypt(usbInfoToEncrypt);
        // Ghi đè vào file setup.vks
        writeToFile(encryptedData, unzippedFolderPath.toString());
        zipFiles(unzippedFolderPath.toString(), zipFilePath.toString());
        deleteDirectory(unzippedFolderPath);
        return zipFilePath.toString();
    }


    private void unzipFile(String zipFilePath, String destDirectory) throws IOException {
        // Tạo thư mục đích nếu chưa tồn tại
        Files.createDirectories(Paths.get(destDirectory));

        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                // Tạo đường dẫn output cho entry hiện tại
                Path outputPath = Paths.get(destDirectory, zipEntry.getName());
                // Nếu entry là thư mục, tạo thư mục
                if (zipEntry.isDirectory()) {
                    Files.createDirectories(outputPath);
                } else {
                    // Nếu entry là file, copy nội dung file
                    Files.createDirectories(outputPath.getParent());
                    try (InputStream inputStream = zipFile.getInputStream(zipEntry)) {
                        Files.copy(inputStream, outputPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
        } catch (IOException e) {
            throw e;
        }
    }


    private void zipFiles(String sourceFolder, String zipFilePath) throws IOException {
        // Tạo stream ghi file zip
        try (FileOutputStream fos = new FileOutputStream(zipFilePath);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {

            File folderToZip = new File(sourceFolder);
            // Kiểm tra thư mục nguồn có tồn tại không
            if (!folderToZip.exists() || !folderToZip.isDirectory()) {
                throw new IOException("Thư mục nguồn để nén không tồn tại hoặc không phải là thư mục: " + sourceFolder);
            }
            // Nén từng file trong thư mục
            File[] filesToZip = folderToZip.listFiles();
            if (filesToZip != null) {
                for (File fileToZip : filesToZip) {
                    if (fileToZip.isFile()) {
                        // Tạo FileInputStream để đọc nội dung file
                        try (FileInputStream fis = new FileInputStream(fileToZip)) {
                            // Tạo entry trong file zip với tên file gốc
                            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                            zipOut.putNextEntry(zipEntry);
                            // Đọc nội dung file và ghi vào zip
                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = fis.read(buffer)) >= 0) {
                                zipOut.write(buffer, 0, length);
                            }
                            zipOut.closeEntry(); // Đóng entry sau khi ghi xong
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw e;
        }
    }


    private void writeToFile(String data, String unzippedFolderPath) throws ApiException {
        File file = getFile(unzippedFolderPath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write(data);
            writer.flush();
        } catch (IOException e) {
            throw new ApiException(SystemErrorCode.INTERNAL_SERVER);
        }
    }

    private File getFile(String unzippedFolderPath) throws ApiException {
        File directory = new File(unzippedFolderPath);
        if (!directory.exists()) {
            throw new ApiException(UsbErrorCode.FOLDER_NOT_FOUND);
        }
        return new File(directory, "setup.vks");
    }


    private void deleteDirectory(Path path) throws IOException {
        if (Files.exists(path)) {
            try (Stream<Path> paths = Files.walk(path)) {
                paths.sorted((p1, p2) -> p2.compareTo(p1)) // Sắp xếp để xóa file con trước
                        .forEach(p -> {
                            try {
                                Files.delete(p); // Xóa từng file hoặc thư mục
                            } catch (IOException e) {
                                try {
                                    throw e;
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        });
            } catch (IOException e) {
                throw e;
            }
        }
    }
}
