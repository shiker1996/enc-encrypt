package tech.shiker.enccore;

public class EncryptResult {

    private final String encryptStr;
    ;

    private final boolean isEncryptError;

    private final String message;

    public EncryptResult(String encryptStr, boolean isEncryptError, String message) {
        this.encryptStr = encryptStr;
        this.isEncryptError = isEncryptError;
        this.message = message;
    }

    public String encryptStr() {
        return encryptStr;
    }

    public boolean isEncryptError() {
        return isEncryptError;
    }

    public String message() {
        return message;
    }
}
