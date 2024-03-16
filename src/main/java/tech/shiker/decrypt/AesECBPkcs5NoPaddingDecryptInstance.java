package tech.shiker.decrypt;

import org.codehaus.plexus.util.Base64;
import tech.shiker.common.DecryptMethod;
import tech.shiker.enccore.DecryptResult;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class AesECBPkcs5NoPaddingDecryptInstance implements DecryptInstance {

    @Override
    public DecryptResult decrypt(String src, String decryptedKey) throws Exception{
        byte[] raw = decryptedKey.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec keySpec = new SecretKeySpec(raw, DecryptMethod.AES_ECB_PKCS5_NO_PADDING.decryptType());
        Cipher cipher = Cipher.getInstance(DecryptMethod.AES_ECB_PKCS5_NO_PADDING.decryptInformation());
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] encrypted1 = new Base64().decode(src.getBytes());//先用base64解密
        byte[] original = cipher.doFinal(encrypted1);
        return new DecryptResult(new String(original, StandardCharsets.UTF_8), false, null);
    }

    @Override
    public DecryptResult decrypt(String src, String key, String index) throws Exception {
        return null;
    }
}
