package tech.shiker.common;

public enum SecurityInfo {

    AES_ECB_PKCS5_PADDING("AES", "AES/ECB/PKCS5Padding", -1, 16, false),
    AES_ECB_NO_PADDING("AES", "AES/ECB/NoPadding", -1, 16, true),
    AES_CBC_PKCS5_PADDING("AES", "AES/CBC/PKCS5Padding", 16, 16, false),
    AES_CBC_NO_PADDING("AES", "AES/CBC/NoPadding", 16, 16, true),
    DES_ECB_PKCS5_PADDING("DESede", "DESede/ECB/PKCS5Padding", -1, 24, false),
    DES_ECB_NO_PADDING("DESede", "DESede/ECB/NoPadding", -1, 24, true),
    DES_CBC_PKCS5_PADDING("DESede", "DESede/CBC/PKCS5Padding", 8, 24, false),
    DES_CBC_NO_PADDING("DESede", "DESede/CBC/NoPadding", 8, 24, true),
    PBE_WITH_MD5_AND_DES("PBEWithMD5AndDES", "PBEWithMD5AndDES", -1, 8),
    PBE_WITH_SHA1_AND_DES("PBEWithSHA1AndDESede", "PBEWithSHA1AndDESede", -1, 8),
    PBE_WITH_SHA256_AND_AES_128("PBEWithHmacSHA256AndAES_128", "PBEWithHmacSHA256AndAES_128", 16, 8),
    PBE_WITH_SHA512_AND_AES_256("PBEWithHmacSHA512AndAES_256", "PBEWithHmacSHA512AndAES_256", 16, 8),
    ;

    private final String decryptType;

    private final String decryptInformation;

    private final int indexLength;

    private final int keyLength;

    private final int saltLength;

    private final int iterations;

    private final boolean handlePadding;

    SecurityInfo(String decryptType, String decryptInformation, int indexLength, int keyLength, boolean handlePadding) {
        this.decryptType = decryptType;
        this.decryptInformation = decryptInformation;
        this.indexLength = indexLength;
        this.keyLength = keyLength;
        this.saltLength = -1;
        this.iterations = -1;
        this.handlePadding = handlePadding;
    }

    SecurityInfo(String decryptType, String decryptInformation, int indexLength, int saltLength) {
        this.decryptType = decryptType;
        this.decryptInformation = decryptInformation;
        this.indexLength = indexLength;
        this.keyLength = -1;
        this.saltLength = saltLength;
        this.iterations = 0;
        this.handlePadding = false;
    }

    public String decryptType() {
        return decryptType;
    }

    public String decryptInformation() {
        return decryptInformation;
    }

    public int indexLength() {
        return indexLength;
    }

    public int keyLength() {
        return keyLength;
    }

    public int saltLength() {
        return saltLength;
    }

    public int iterations() {
        return iterations;
    }

    public boolean handlePadding() {
        return handlePadding;
    }
}
