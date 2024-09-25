package vn.eledevo.vksbe.constant;

public class ResponseMessage {

    private ResponseMessage() {}

    public static final String USER_EXIST = "Người dùng đã tồn tại";

    public static final String USER_BLANK = "Tên đăng nhập không được để trống";

    public static final String USER_SIZE = "Tên đăng nhập phải có độ dài từ 3 đến 50 ký tự";

    public static final String PASSWD_BLANK = "Mật khẩu không được để trống";

    public static final String EXITS_PIN = "Mã pin không được để trống";

    public static final String RESET_PASSWORD_SUCCESS = "Reset mật khẩu thành công";

    public static final String CHANGE_PASSWORD_SUCCESS = "Thay đổi mật khẩu thành công";

    public static final String OLD_PASSWD_BLANK = "Mật khẩu cũ không được để trống";

    public static final String NEW_PASSWD_BLANK = "Mật khẩu mới không được để trống";
    public static final String USERNAME_REQUIRE = "Mã cán bộ không được để trống";

    public static final String USERNAME_SIZE = "Mã cán bộ phải có độ dài từ {min} đến {max} ký tự";

    public static final String FULL_NAME_REQUIRE = "Họ và Tên cán bộ không được để trống";

    public static final String FULL_NAME_SIZE = "Họ và Tên cán bộ phải có độ dài từ {min} đến {max} ký tự";

    public static final String ROLE_ID_REQUIRE = "Chức vụ không được để trống";

    public static final String ROLE_NAME_REQUIRE = "Tên chức vụ không được để trống";

    public static final String ROLE_CODE_SIZE = "Mã chức vụ phải có độ dài từ {min} đến {max} ký tự";

    public static final String DEPARTMENT_REQUIRE = "Phòng ban không được để trống";

    public static final String DEPARTMENT_NAME_REQUIRE = "Tên phòng ban không được để trống";

    public static final String ORGANIZATION_REQUIRE = "Đơn vị không được để trống";

    public static final String ORGANIZATION_NAME_REQUIRE = "Tên đơn vị không được để trống";

    public static final String PASSWD_SIZE = "Mật khẩu phải có từ 8 kí tự trở lên";

    public static final String PIN_SIZE = "Mã PIN phải có 6 kí";

    public static final String CREATE_PIN_SUCCESS = "Tạo mã PIN thành công";

    public static final String PHONE_NUMBER_REQUIRE = "Số điện thoại không được để trống";

    public static final String PHONE_NUMBER_INVALID = "Số điện thoại không hợp lệ";

    public static final String USERNAME_IS_EXIST = "Mã cán bộ đã tồn tại";

    public static final String ROLE_NOT_EXIST = "Chức vụ không tồn tại";

    public static final String OUTDATED_DATA =
            "Cơ cấu tổ chức đã thay đổi. Vui lòng đăng nhập lại để có dữ liệu mới nhất.";

    public static final String DEPARTMENT_NOT_EXIST = "Phòng ban không tồn tại";

    public static final String ORGANIZATION_NOT_EXIST = "Đơn vị không tồn tại";

    public static final String AVATAR_MAX_SIZE = "Ảnh đại diện không được vượt quá {0} MB.";

    public static final String AVATAR_EXTENSION_INVALID = "Ảnh đại diện chỉ chấp nhận định dạng {0}.";

    public static final String SWAP_ACCOUNT_SUCCESS = "Hoán đổi trạng thái tài khoản thành công";

    public static final String AVATAR_URL_INVALID = "Đường dẫn ảnh đại diện không hợp lệ";

    public static final String UPDATE_COMPUTER_INFOR_SUCCESS = "Chỉnh sửa thông tin thiết bị máy tính thành công";

    public static final String LOCK_ACCOUNT_SUCCESS = "Khóa tài khoản thành công";
}
