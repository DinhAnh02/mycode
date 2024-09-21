package vn.eledevo.vksbe.constant;

public class ResponseMessage {

    private ResponseMessage() {}

    public static final String USER_EXIST = "Người dùng đã tồn tại";

    public static final String USER_BLANK = "Tên đăng nhập không được để trống";

    public static final String USER_SIZE = "Tên đăng nhập phải có độ dài từ 3 đến 50 ký tự";

    public static final String PASSWD_BLANK = "Mật khẩu không được để trống";

    public static final String RESET_PASSWORD_SUCCESS = "Reset mật khẩu thành công";

    public static final String CHANGE_PASSWORD_SUCCESS = "Thay đổi mật khẩu thành công";
}
