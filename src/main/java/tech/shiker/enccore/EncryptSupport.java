package tech.shiker.enccore;

import tech.shiker.common.SecurityConstant;
import tech.shiker.common.SecurityMethod;
import tech.shiker.config.EncSettingState;

import java.util.ResourceBundle;

public class EncryptSupport {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("META-INF.EncToolBundle");

    public static EncryptResult encrypt(String sSrc) {
        try {
            // 判断Key是否正确
            if (EncSettingState.getInstance().decryptedKey == null) {
                return new EncryptResult("!!!!ERROR!!!", true, bundle.getString(SecurityConstant.KEY_NULL_MESSAGE));
            }
            if (EncSettingState.getInstance().decryptedType == null) {
                return new EncryptResult("!!!!ERROR!!!", true, bundle.getString(SecurityConstant.TYPE_NULL_MESSAGE));
            }
            if (EncSettingState.getInstance().decryptedInformation == null) {
                return new EncryptResult("!!!!ERROR!!!", true, bundle.getString(SecurityConstant.INFORMATION_NULL_MESSAGE));
            }
            SecurityMethod securityMethod = SecurityMethod.decryptMethod(EncSettingState.getInstance().decryptedType, EncSettingState.getInstance().decryptedInformation);
            if (securityMethod == null) {
                return new EncryptResult("!!!!ERROR!!!", true, bundle.getString(SecurityConstant.DECRYPT_UNKNOWN_MESSAGE));
            }
            return securityMethod.decryptInstance().encrypt(sSrc, EncSettingState.getInstance().decryptedKey, EncSettingState.getInstance().decryptedVi, EncSettingState.getInstance().decryptedSalt, EncSettingState.getInstance().decryptedIteration);
        } catch (Exception ex) {
            return new EncryptResult("!!!!ERROR!!!", true, String.format(bundle.getString(SecurityConstant.ENCRYPT_ERR_MESSAGE), sSrc, ex.getMessage()));
        }
    }
}
