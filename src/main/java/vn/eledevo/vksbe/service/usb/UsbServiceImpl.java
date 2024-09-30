package vn.eledevo.vksbe.service.usb;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.eledevo.vksbe.constant.ErrorCode;
import vn.eledevo.vksbe.constant.ErrorCodes.AccountErrorCode;
import vn.eledevo.vksbe.constant.ErrorCodes.SystemErrorCode;
import vn.eledevo.vksbe.constant.ErrorCodes.UsbErrorCode;
import vn.eledevo.vksbe.dto.request.AccountActive;
import vn.eledevo.vksbe.dto.request.DataChange;
import vn.eledevo.vksbe.dto.request.UsbRequest;
import vn.eledevo.vksbe.dto.response.ResponseFilter;
import vn.eledevo.vksbe.dto.response.usb.UsbResponseFilter;
import vn.eledevo.vksbe.entity.Accounts;
import vn.eledevo.vksbe.entity.Computers;
import vn.eledevo.vksbe.exception.ApiException;
import vn.eledevo.vksbe.repository.AccountRepository;
import vn.eledevo.vksbe.repository.UsbRepository;
import vn.eledevo.vksbe.service.ChangeData;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static vn.eledevo.vksbe.constant.ErrorCode.*;

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
    public String createUsbToken(String username) throws Exception {
        String zipFilePath = "src/AppUsb/app_usb.zip";
        String unzippedFolderPath = "src/AppUsb/unzipped";
        unzipFile(zipFilePath, unzippedFolderPath);
        Optional<AccountActive> account = accountRepository.findByUsernameActive(username);
        if (account.isPresent()) {
            Optional<Accounts> acc = accountRepository.findById(account.get().getId());
            if (acc.isPresent()) {
                List<Computers> computers = acc.get().getComputers();
                String[] computerNames =
                        computers.stream().map(Computers::getCode).toArray(String[]::new);
                DataChange usbInfoToEncrypt = DataChange.builder()
                        .maPin(acc.get().getPin())
                        .keyUsb(acc.get().getUsb().getKeyUsb())
                        .listDevices(computerNames)
                        .usbVendorCode(acc.get().getUsb().getUsbVendorCode())
                        .usbCode(acc.get().getUsb().getUsbCode())
                        .build();

                // Mã hóa chuỗi JSON
                String encryptedData = ChangeData.encrypt(usbInfoToEncrypt);

                // ghi đè vào file setup.vks
                writeToFile(encryptedData);

                // zip file
                zipFiles();

                // delete folder unzipped
                deleteDirectory(Paths.get(unzippedFolderPath));
                return zipFilePath;
            }
        } else {
            throw new ApiException(AccountErrorCode.ACCOUNT_NOT_FOUND);
        }
        return null;
    }

    private void unzipFile(String zipFilePath, String destDirectory) throws IOException {
        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            zipFile.stream().forEach(zipEntry -> {
                try {
                    InputStream inputStream = zipFile.getInputStream(zipEntry);
                    Path outputPath = Paths.get(destDirectory + File.separator + zipEntry.getName());
                    Files.createDirectories(outputPath.getParent());
                    Files.copy(inputStream, outputPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void zipFiles() throws IOException {
        String sourceFolder = "src/AppUsb/unzipped";
        String zipFilePath = "src/AppUsb/app_usb.zip";

        try (FileOutputStream fos = new FileOutputStream(zipFilePath);
                ZipOutputStream zipOut = new ZipOutputStream(fos)) {

            // Nén từng file trong thư mục
            File folder = new File(sourceFolder);
            File[] filesToZip = folder.listFiles();

            if (filesToZip != null) {
                for (File fileToZip : filesToZip) {
                    if (fileToZip.isFile()) {
                        try (FileInputStream fis = new FileInputStream(fileToZip)) {
                            // Tạo entry trong file zip với tên file gốc
                            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                            zipOut.putNextEntry(zipEntry);

                            // Đọc và ghi nội dung file vào zip
                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = fis.read(buffer)) >= 0) {
                                zipOut.write(buffer, 0, length);
                            }
                        }
                    }
                }
            }
            zipOut.closeEntry();
        }
    }

    private void writeToFile(String data) throws ApiException {
        File file = getFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write(data);
            writer.flush();
        } catch (IOException e) {
            throw new ApiException(SystemErrorCode.INTERNAL_SERVER);
        }
    }

    private File getFile() throws ApiException {
        String resourcePath = "src/AppUsb/unzipped/";

        File directory = new File(resourcePath);

        if (!directory.exists()) {
            throw new ApiException(UsbErrorCode.FOLDER_NOT_FOUND);
        }
        return new File(directory, "setup.vks");
    }

    private void deleteDirectory(Path path) throws IOException {
        if (Files.exists(path)) {
            try (Stream<Path> paths = Files.walk(path)) {
                paths.sorted((p1, p2) -> p2.compareTo(p1)).forEach(p -> {
                    try {
                        Files.delete(p);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }
}
