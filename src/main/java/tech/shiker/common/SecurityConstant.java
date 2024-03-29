package tech.shiker.common;

public class SecurityConstant {
    public static final String ENC_DECRYPT_TITLE = "ENC Decrypt Tool";

    public static final String KEY_NULL_MESSAGE = "Security key can't be null";

    public static final String KEY_INVALID_MESSAGE = "Security key is invalid";

    public static final String SALT_INVALID_MESSAGE = "Security salt must be at least 8 bytes long";

    public static final String ITERATIONS_INVALID_MESSAGE = "Security iterations must be greater than 0";

    public static final String IV_NULL_MESSAGE = "Security iv can't be null";

    public static final String IV_INVALID_MESSAGE = "Security iv is invalid";

    public static final String DECRYPT_ERR_MESSAGE = "Decrypt error：%s, err:%s";

    public static final String ENCRYPT_ERR_MESSAGE = "Encrypt error：%s, err:%s";

    public static final String ENCRYPT_NULL_MESSAGE = "Please select some text to encrypt.";

    public static final String ENCRYPT_RESULT = "ENC(%s)";

    public static final String UN_SUPPORT_MESSAGE = "Unsupported decrypt file!";

    public static final String CONTENT_TYPE = "text/html";

    public static final String ORIGINAL_FILE_TITLE = "Source file";

    public static final String DECRYPT_FILE_TITLE = "Target file";

    public static final String TYPE_NULL_MESSAGE = "Decrypt type can't be null";

    public static final String INFORMATION_NULL_MESSAGE = "Decrypt information can't be null";

    public static final String DECRYPT_UNKNOWN_MESSAGE = "Decrypt type is not supported, please check it or contract me!";
}
