package vn.eledevo.vksbe.constant.ErrorCodes;

import org.springframework.http.HttpStatusCode;

import java.util.Map;

public interface BaseErrorCode {
    HttpStatusCode getStatusCode(); // Mã trạng thái HTTP tương ứng
    Map<String, String> getResult();    // Trả về kết quả dưới dạng Object (hoặc Optional)
    String getCode();      // Mã lỗi
    String getMessage();   // Thông điệp lỗi
}


