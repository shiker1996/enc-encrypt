package tech.shiker.common;

import com.google.common.collect.Lists;
import tech.shiker.security.SecurityInstance;
import tech.shiker.security.impl.CBCSecurityInstance;
import tech.shiker.security.impl.ECBSecurityInstance;
import tech.shiker.security.impl.PBEWithAesSecurityInstance;
import tech.shiker.security.impl.PBEWithDesSecurityInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum SecurityMethod {

    AES_ECB_NO_PADDING(SecurityInfo.AES_ECB_NO_PADDING, new ECBSecurityInstance(SecurityInfo.AES_ECB_NO_PADDING)),
    AES_ECB_PKCS5_PADDING(SecurityInfo.AES_ECB_PKCS5_PADDING, new ECBSecurityInstance(SecurityInfo.AES_ECB_PKCS5_PADDING)),
    DES_ECB_NO_PADDING(SecurityInfo.DES_ECB_NO_PADDING, new ECBSecurityInstance(SecurityInfo.DES_ECB_NO_PADDING)),
    DES_ECB_PKCS5_PADDING(SecurityInfo.DES_ECB_PKCS5_PADDING, new ECBSecurityInstance(SecurityInfo.DES_ECB_PKCS5_PADDING)),
    AES_CBC_NO_PADDING(SecurityInfo.AES_CBC_NO_PADDING, new CBCSecurityInstance(SecurityInfo.AES_CBC_NO_PADDING)),
    AES_CBC_PKCS5_PADDING(SecurityInfo.AES_CBC_PKCS5_PADDING, new CBCSecurityInstance(SecurityInfo.AES_CBC_PKCS5_PADDING)),
    DES_CBC_NO_PADDING(SecurityInfo.DES_CBC_NO_PADDING, new CBCSecurityInstance(SecurityInfo.DES_CBC_NO_PADDING)),
    DES_CBC_PKCS5_PADDING(SecurityInfo.DES_CBC_PKCS5_PADDING, new CBCSecurityInstance(SecurityInfo.DES_CBC_PKCS5_PADDING)),
    PBE_WITH_MD5_AND_DES(SecurityInfo.PBE_WITH_MD5_AND_DES, new PBEWithDesSecurityInstance(SecurityInfo.PBE_WITH_MD5_AND_DES)),
    PBE_WITH_SHA1_AND_DES(SecurityInfo.PBE_WITH_SHA1_AND_DES, new PBEWithDesSecurityInstance(SecurityInfo.PBE_WITH_SHA1_AND_DES)),
    PBE_WITH_SHA256_AND_AES_128(SecurityInfo.PBE_WITH_SHA256_AND_AES_128, new PBEWithAesSecurityInstance(SecurityInfo.PBE_WITH_SHA256_AND_AES_128)),
    PBE_WITH_SHA512_AND_AES_256(SecurityInfo.PBE_WITH_SHA512_AND_AES_256, new PBEWithAesSecurityInstance(SecurityInfo.PBE_WITH_SHA512_AND_AES_256)),

    ;


    private final SecurityInfo securityInfo;

    private final SecurityInstance decryptInstance;

    SecurityMethod(SecurityInfo securityInfo, SecurityInstance securityInstance) {
        this.securityInfo = securityInfo;
        this.decryptInstance = securityInstance;
    }

    public static SecurityMethod decryptMethod(String decryptType, String decryptInformation) {
        for (SecurityMethod securityMethod : SecurityMethod.values()) {
            if (securityMethod.securityInfo.decryptInformation().equals(decryptInformation)
                    && securityMethod.securityInfo.decryptType().equals(decryptType))
                return securityMethod;
        }
        return null;
    }

    public String decryptType() {
        return securityInfo.decryptType();
    }

    public SecurityInstance decryptInstance() {
        return decryptInstance;
    }

    public String decryptInformation() {
        return securityInfo.decryptInformation();
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
