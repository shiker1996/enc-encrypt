package tech.shiker.common;

import tech.shiker.decrypt.AesECBPkcs5NoPaddingDecryptInstance;
import tech.shiker.decrypt.DecryptInstance;

public enum DecryptMethod {

    AES_ECB_PKCS5_NO_PADDING("AES", "AES/ECB/PKCS5Padding", new AesECBPkcs5NoPaddingDecryptInstance());

    private final String decryptType;

    private final String decryptInformation;

    private final DecryptInstance decryptInstance;

    DecryptMethod(String decryptType, String decryptInformation, DecryptInstance decryptInstance) {
        this.decryptType = decryptType;
        this.decryptInformation = decryptInformation;
        this.decryptInstance = decryptInstance;
    }

    public String decryptType() {
        return decryptType;
    }

    public String decryptInformation() {
        return decryptInformation;
    }

    public DecryptInstance decryptInstance() {
        return decryptInstance;
    }

    public static DecryptMethod decryptMethod(String decryptType, String decryptInformation) {
        for (DecryptMethod decryptMethod : DecryptMethod.values()) {
            if(decryptMethod.decryptInformation.equals(decryptInformation)
                    &&decryptMethod.decryptType.equals(decryptType))
                return decryptMethod;
        }
        return null;
    }
}
