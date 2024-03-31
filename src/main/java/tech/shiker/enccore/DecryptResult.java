package tech.shiker.enccore;

public class DecryptResult {

    private final String decryptStr;
    ;

    private final boolean isDecryptError;

    private final String message;

    public DecryptResult(String decryptStr, boolean isDecryptError, String message) {
        this.decryptStr = decryptStr;
        this.isDecryptError = isDecryptError;
        this.message = message;
    }

    public String decryptStr() {
        return decryptStr;
    }

    public boolean isDecryptError() {
        return isDecryptError;
    }

    public String message() {
        return message;
    }
}
