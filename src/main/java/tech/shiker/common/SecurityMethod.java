package tech.shiker.common;

import tech.shiker.security.AesCBCNoPaddingSecurityInstance;
import tech.shiker.security.AesCBCPkcs5PaddingSecurityInstance;
import tech.shiker.security.AesECBPkcs5PaddingSecurityInstance;
import tech.shiker.security.AesECBNoPaddingSecurityInstance;
import tech.shiker.security.DesCBCNoPaddingSecurityInstance;
import tech.shiker.security.DesCBCPkcs5PaddingSecurityInstance;
import tech.shiker.security.DesECBNoPaddingSecurityInstance;
import tech.shiker.security.DesECBPkcs5PaddingSecurityInstance;
import tech.shiker.security.SecurityInstance;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum SecurityMethod {

    AES_ECB_PKCS5_PADDING("AES", "AES/ECB/PKCS5Padding", new AesECBPkcs5PaddingSecurityInstance()),
    AES_ECB_NO_PADDING("AES", "AES/ECB/NoPadding", new AesECBNoPaddingSecurityInstance()),
    AES_CBC_PKCS5_PADDING("AES", "AES/CBC/PKCS5Padding", new AesCBCPkcs5PaddingSecurityInstance()),
    AES_CBC_NO_PADDING("AES", "AES/CBC/NoPadding", new AesCBCNoPaddingSecurityInstance()),
    DES_ECB_PKCS5_PADDING("DESede", "DESede/ECB/PKCS5Padding", new DesECBPkcs5PaddingSecurityInstance()),
    DES_ECB_NO_PADDING("DESede", "DESede/ECB/NoPadding", new DesECBNoPaddingSecurityInstance()),
    DES_CBC_PKCS5_PADDING("DESede", "DESede/CBC/PKCS5Padding", new DesCBCPkcs5PaddingSecurityInstance()),
    DES_CBC_NO_PADDING("DESede", "DESede/CBC/NoPadding", new DesCBCNoPaddingSecurityInstance()),
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

    public static Set<String> getAllDecryptType(){
        return Arrays.stream(SecurityMethod.values()).map(SecurityMethod::decryptType).collect(Collectors.toSet());
    }

    public static List<String> getAllDecryptInformation(){
        return Arrays.stream(SecurityMethod.values()).map(SecurityMethod::decryptInformation).collect(Collectors.toList());
    }
}
