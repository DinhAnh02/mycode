package vn.eledevo.vksbe.constant;

public class ResponseMessage {

    private ResponseMessage() {}

    public static final String USER_BLANK = "Tên đăng nhập không được để trống";

    public static final String PASSWD_BLANK = "Mật khẩu không được để trống";

    public static final String EXITS_PIN = "Mã pin không được để trống";

    public static final String EXITS_PIN_OLD = "Mã PIN cũ không được để trống";

    public static final String OLD_PASSWD_BLANK = "Mật khẩu cũ không được để trống";

    public static final String NEW_PASSWD_BLANK = "Mật khẩu mới không được để trống";

    public static final String USERNAME_REQUIRE = "Mã cán bộ không được để trống";

    public static final String USERNAME_SIZE = "Mã cán bộ phải có đúng 8 ký tự";

    public static final String FULL_NAME_REQUIRE = "Họ và Tên cán bộ không được để trống";

    public static final String FULL_NAME_SIZE = "Họ và tên chỉ bao gồm chữ, dấu cách và tối đa 255 ký tự";

    public static final String PASSWD_SIZE = "Mật khẩu phải có từ 8 kí tự trở lên";

    public static final String PIN_SIZE = "Mã PIN phải có 6 kí tự";

    public static final String PHONE_NUMBER_REQUIRE = "Số điện thoại không được để trống";

    public static final String PHONE_NUMBER_INVALID = "Số điện thoại không hợp lệ";

    public static final String USERNAME_IS_EXIST = "Mã cán bộ đã tồn tại";

    public static final String ROLE_NOT_EXIST = "Chức vụ không tồn tại";

    public static final String OUTDATED_DATA =
            "Cơ cấu tổ chức đã thay đổi. Vui lòng đăng nhập lại để có dữ liệu mới nhất.";

    public static final String DEPARTMENT_NOT_EXIST = "Phòng ban không tồn tại";

    public static final String ORGANIZATION_NOT_EXIST = "Đơn vị không tồn tại";

    public static final String AVATAR_URL_INVALID = "Đường dẫn ảnh đại diện không hợp lệ";

    public static final String COMPUTER_CONNECTED_WITH_ANOTHER_ACCOUNT = "Thiết bị đã được kết nối với tài khoản khác";

    public static final String COMPUTER_CONNECTED_SUCCESS = "Kết nối thiết bị thành công";

    public static final String COMPUTER_NOT_FOUND_SYSTEM = "Máy tính không tồn tại trong hệ thống";

    public static final String ROLE_ID_NOT_NULL = "Tên chức vụ không được để trống";

    public static final String DEPARTMENT_ID_NOT_NULL = "Tên phòng ban không được để trống";

    public static final String ORGANIZATION_ID_NOT_NULL = "Tên đơn vị không được để trống";

    public static final String GENDER_REQUIRE = "Giới tính không được để trống";

    public static final String GENDER_NOT_BLANK = "Giới tính không được để trống";

    public static final String MINDMAPTEMPLATE_NOT_NULL = "Tên sơ đồ mẫu không được để trống";

    public static final String NAMEMINDMAP_MAX = "Tên không được vượt quá 255 ký tự";

    public static final String NAMEMINDMAP_SPECIAL = "Tên không được chứa ký tự đặc biệt";

    public static final String CASE_STATUS_NAME_CANNOT_BE_BLANK = "Tên trạng thái không được để rỗng";

    public static final String CASE_STATUS_NAME_CANNOT_EXCEED_255_CHARACTER  = "Tên trạng thái không được quá 255 kí tự";

    public static final String STATUS_NAME_CAN_ONLY_CONTAIN_LETTER  = "Tên trạng thái chỉ có thể chứa các chữ cái";


    public static final String  ORGANIZATION_NAME_NOT_BLANK = "Tên đơn vị không được để trống";

    public static final String  ORGANIZATION_NAME_SIZE = "Tên đơn vị tối thiểu 4 kí tự, tối đa 255 kí tự";

    public static final String  ORGANIZATION_CODE_NOT_BLANK = "Mã đơn vị không được để trống";

    public static final String  ORGANIZATION_CODE_SIZE = "Mã đơn vị tối thiểu 4 kí tự, tối đa 8 kí tự";

    public static final String  ORGANIZATION_ADDRESS_NOT_BLANK = "Địa chỉ không được để trống";

    public static final String  ORGANIZATION_ADDRESS_SIZE = "Địa chỉ tối đa 225 kí tự";

    public static final String COMPUTER_CONNECTED_WITH_THIS_ACCOUNT = "Thiết bị đã được kết nối với tài khoản này";

    public static final String MIND_MAP_IMG_URL_INVALID = "Đường dẫn ảnh sơ đồ mẫu không hợp lệ";
}
