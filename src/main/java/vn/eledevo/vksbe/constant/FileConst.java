package vn.eledevo.vksbe.constant;

public class FileConst {

    private FileConst() {}

    public static final long BYTES_IN_MB = 1024L * 1024L;

    public static final long MAX_AVATAR_SIZE = 10L; // 10MB

    public static final String[] AVATAR_ALLOWED_EXTENSIONS = {".png", ".jpg", ".jpeg"};

    public static final String AVATAR_URI = "/api/v1/private/avatar";
}
