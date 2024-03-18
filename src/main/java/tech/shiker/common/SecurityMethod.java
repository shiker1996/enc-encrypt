package tech.shiker.common;

import tech.shiker.security.AesECBPkcs5NoPaddingSecurityInstance;
import tech.shiker.security.AesECBZeroNoPaddingSecurityInstance;
import tech.shiker.security.SecurityInstance;

public enum SecurityMethod {

    AES_ECB_PKCS5_NO_PADDING("AES", "AES/ECB/PKCS5Padding", new AesECBPkcs5NoPaddingSecurityInstance()),
    AES_ECB_ZERO_NO_PADDING("AES", "AES/ECB/NoPadding", new AesECBZeroNoPaddingSecurityInstance()),
    ;

    private final String decryptType;

    private final String decryptInformation;

    private final SecurityInstance decryptInstance;

    SecurityMethod(String decryptType, String decryptInformation, SecurityInstance securityInstance) {
        this.decryptType = decryptType;
        this.decryptInformation = decryptInformation;
        this.decryptInstance = securityInstance;
    }

    public String decryptType() {
        return decryptType;
    }

    public String decryptInformation() {
        return decryptInformation;
    }

    public SecurityInstance decryptInstance() {
        return decryptInstance;
    }

    public static SecurityMethod decryptMethod(String decryptType, String decryptInformation) {
        for (SecurityMethod securityMethod : SecurityMethod.values()) {
            if(securityMethod.decryptInformation.equals(decryptInformation)
                    && securityMethod.decryptType.equals(decryptType))
                return securityMethod;
        }
        return null;
    }
}
