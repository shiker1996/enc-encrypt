package tech.shiker.common;

import com.google.common.collect.Lists;
import tech.shiker.security.AesCBCNoPaddingSecurityInstance;
import tech.shiker.security.AesCBCPkcs5PaddingSecurityInstance;
import tech.shiker.security.AesECBNoPaddingSecurityInstance;
import tech.shiker.security.AesECBPkcs5PaddingSecurityInstance;
import tech.shiker.security.DesCBCNoPaddingSecurityInstance;
import tech.shiker.security.DesCBCPkcs5PaddingSecurityInstance;
import tech.shiker.security.DesECBNoPaddingSecurityInstance;
import tech.shiker.security.DesECBPkcs5PaddingSecurityInstance;
import tech.shiker.security.PBEWithMD5AndDesSecurityInstance;
import tech.shiker.security.PBEWithSHA1AndDesSecurityInstance;
import tech.shiker.security.PBEWithSHA256AndAes128SecurityInstance;
import tech.shiker.security.PBEWithSHA512AndAes256SecurityInstance;
import tech.shiker.security.SecurityInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum SecurityMethod {

    AES_ECB_PKCS5_PADDING("AES", "AES/ECB/PKCS5Padding", new AesECBPkcs5PaddingSecurityInstance()),
    AES_ECB_NO_PADDING("AES", "AES/ECB/NoPadding", new AesECBNoPaddingSecurityInstance()),
    AES_CBC_PKCS5_PADDING("AES", "AES/CBC/PKCS5Padding", new AesCBCPkcs5PaddingSecurityInstance()),
    AES_CBC_NO_PADDING("AES", "AES/CBC/NoPadding", new AesCBCNoPaddingSecurityInstance()),
    DES_ECB_PKCS5_PADDING("DESede", "DESede/ECB/PKCS5Padding", new DesECBPkcs5PaddingSecurityInstance()),
    DES_ECB_NO_PADDING("DESede", "DESede/ECB/NoPadding", new DesECBNoPaddingSecurityInstance()),
    DES_CBC_PKCS5_PADDING("DESede", "DESede/CBC/PKCS5Padding", new DesCBCPkcs5PaddingSecurityInstance()),
    DES_CBC_NO_PADDING("DESede", "DESede/CBC/NoPadding", new DesCBCNoPaddingSecurityInstance()),
    PBE_WITH_MD5_AND_DES("PBEWithMD5AndDES", "PBEWithMD5AndDES", new PBEWithMD5AndDesSecurityInstance()),
    PBE_WITH_SHA1_AND_DES("PBEWithSHA1AndDESede", "PBEWithSHA1AndDESede", new PBEWithSHA1AndDesSecurityInstance()),
    PBE_WITH_SHA256_AND_AES_128("PBEWithHmacSHA256AndAES_128", "PBEWithHmacSHA256AndAES_128", new PBEWithSHA256AndAes128SecurityInstance()),
    PBE_WITH_SHA512_AND_AES_256("PBEWithHmacSHA512AndAES_256", "PBEWithHmacSHA512AndAES_256", new PBEWithSHA512AndAes256SecurityInstance()),

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

    public static Map<String, List<String>> getType2Information(){
        Map<String, List<String>> result = new HashMap<>();
        for (SecurityMethod value : SecurityMethod.values()) {
            if(result.containsKey(value.decryptType())){
                result.get(value.decryptType()).add(value.decryptInformation());
            }else{
                result.put(value.decryptType(), Lists.newArrayList(value.decryptInformation()));
            }
        }
        return result;
    }
}
