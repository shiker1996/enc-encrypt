package test;

import org.junit.Assert;
import org.junit.Test;
import tech.shiker.common.SecurityMethod;
import tech.shiker.enccore.DecryptResult;
import tech.shiker.enccore.EncryptResult;
import tech.shiker.security.SecurityInstance;


public class SecurityInstanceTest {

    @Test
    public void DES_ECB_PKCS5_PADDING_Test() throws Exception {
        String originalText = "Hello, world!";
        String password = "123456789012345678901234";
        String salt = "12345678";
        String index = "1234567812345678";
        SecurityInstance securityInstance = SecurityMethod.DES_ECB_PKCS5_PADDING.decryptInstance();
        // Encryption
        EncryptResult encryptResult = securityInstance.encrypt(originalText, password, index, salt, 1000);
        Assert.assertNull(encryptResult.message());
        // Decryption
        DecryptResult decryptResult = securityInstance.decrypt(encryptResult.encryptStr(), password, index, salt, 1000);
        Assert.assertEquals(decryptResult.decryptStr(), originalText);
    }

    @Test
    public void DES_ECB_NO_PADDING_Test() throws Exception {
        String originalText = "Hello, world!";
        String password = "123456789012345678901234";
        String salt = "12345678";
        String index = "1234567812345678";
        SecurityInstance securityInstance = SecurityMethod.DES_ECB_NO_PADDING.decryptInstance();
        // Encryption
        EncryptResult encryptResult = securityInstance.encrypt(originalText, password, index, salt, 1000);
        Assert.assertNull(encryptResult.message());
        // Decryption
        DecryptResult decryptResult = securityInstance.decrypt(encryptResult.encryptStr(), password, index, salt, 1000);
        Assert.assertEquals(decryptResult.decryptStr(), originalText);
    }

    @Test
    public void DES_CBC_NO_PADDING_Test() throws Exception {
        String originalText = "Hello, world!";
        String password = "123456789012345678901234";
        String salt = "12345678";
        String index = "12345678";
        SecurityInstance securityInstance = SecurityMethod.DES_CBC_NO_PADDING.decryptInstance();
        // Encryption
        EncryptResult encryptResult = securityInstance.encrypt(originalText, password, index, salt, 1000);
        Assert.assertNull(encryptResult.message());
        // Decryption
        DecryptResult decryptResult = securityInstance.decrypt(encryptResult.encryptStr(), password, index, salt, 1000);
        Assert.assertEquals(decryptResult.decryptStr(), originalText);
    }

    @Test
    public void DES_CBC_PKCS5_PADDING_Test() throws Exception {
        String originalText = "Hello, world!";
        String password = "123456789012345678901234";
        String salt = "12345678";
        String index = "12345678";
        SecurityInstance securityInstance = SecurityMethod.DES_CBC_PKCS5_PADDING.decryptInstance();
        // Encryption
        EncryptResult encryptResult = securityInstance.encrypt(originalText, password, index, salt, 1000);
        Assert.assertNull(encryptResult.message());
        // Decryption
        DecryptResult decryptResult = securityInstance.decrypt(encryptResult.encryptStr(), password, index, salt, 1000);
        Assert.assertEquals(decryptResult.decryptStr(), originalText);
    }


    @Test
    public void AES_ECB_PKCS5_PADDING_Test() throws Exception {
        String originalText = "Hello, world!";
        String password = "MySecretPassword";
        String salt = "12345678";
        String index = "1234567812345678";
        SecurityInstance securityInstance = SecurityMethod.AES_ECB_PKCS5_PADDING.decryptInstance();
        // Encryption
        EncryptResult encryptResult = securityInstance.encrypt(originalText, password, index, salt, 1000);
        Assert.assertNull(encryptResult.message());
        // Decryption
        DecryptResult decryptResult = securityInstance.decrypt(encryptResult.encryptStr(), password, index, salt, 1000);
        Assert.assertEquals(decryptResult.decryptStr(), originalText);
    }

    @Test
    public void AES_ECB_NO_PADDING_Test() throws Exception {
        String originalText = "Hello, world!";
        String password = "MySecretPassword";
        String salt = "12345678";
        String index = "1234567812345678";
        SecurityInstance securityInstance = SecurityMethod.AES_ECB_NO_PADDING.decryptInstance();
        // Encryption
        EncryptResult encryptResult = securityInstance.encrypt(originalText, password, index, salt, 1000);
        Assert.assertNull(encryptResult.message());
        // Decryption
        DecryptResult decryptResult = securityInstance.decrypt(encryptResult.encryptStr(), password, index, salt, 1000);
        Assert.assertEquals(decryptResult.decryptStr(), originalText);
    }

    @Test
    public void AES_CBC_NO_PADDING_Test() throws Exception {
        String originalText = "Hello, world!";
        String password = "MySecretPassword";
        String salt = "12345678";
        String index = "1234567812345678";
        SecurityInstance securityInstance = SecurityMethod.AES_CBC_NO_PADDING.decryptInstance();
        // Encryption
        EncryptResult encryptResult = securityInstance.encrypt(originalText, password, index, salt, 1000);
        Assert.assertNull(encryptResult.message());
        // Decryption
        DecryptResult decryptResult = securityInstance.decrypt(encryptResult.encryptStr(), password, index, salt, 1000);
        Assert.assertEquals(decryptResult.decryptStr(), originalText);
    }

    @Test
    public void AES_CBC_PKCS5_PADDING_Test() throws Exception {
        String originalText = "Hello, world!";
        String password = "MySecretPassword";
        String salt = "12345678";
        String index = "1234567812345678";
        SecurityInstance securityInstance = SecurityMethod.AES_CBC_PKCS5_PADDING.decryptInstance();
        // Encryption
        EncryptResult encryptResult = securityInstance.encrypt(originalText, password, index, salt, 1000);
        Assert.assertNull(encryptResult.message());
        // Decryption
        DecryptResult decryptResult = securityInstance.decrypt(encryptResult.encryptStr(), password, index, salt, 1000);
        Assert.assertEquals(decryptResult.decryptStr(), originalText);
    }

    @Test
    public void PBE_WITH_SHA1_AND_DES_Test() throws Exception {
        String originalText = "Hello, world!";
        String password = "MySecretPassword";
        String salt = "12345678";
        String index = "1234567812345678";
        SecurityInstance securityInstance = SecurityMethod.PBE_WITH_SHA1_AND_DES.decryptInstance();
        // Encryption
        EncryptResult encryptResult = securityInstance.encrypt(originalText, password, index, salt, 1000);
        Assert.assertNull(encryptResult.message());
        // Decryption
        DecryptResult decryptResult = securityInstance.decrypt(encryptResult.encryptStr(), password, index, salt, 1000);
        Assert.assertEquals(decryptResult.decryptStr(), originalText);
    }

    @Test
    public void PBE_WITH_MD5_AND_DES_Test() throws Exception {
        String originalText = "Hello, world!";
        String password = "MySecretPassword";
        String salt = "12345678";
        String index = "1234567812345678";
        SecurityInstance securityInstance = SecurityMethod.PBE_WITH_MD5_AND_DES.decryptInstance();
        // Encryption
        EncryptResult encryptResult = securityInstance.encrypt(originalText, password, index, salt, 1000);
        Assert.assertNull(encryptResult.message());
        // Decryption
        DecryptResult decryptResult = securityInstance.decrypt(encryptResult.encryptStr(), password, index, salt, 1000);
        Assert.assertEquals(decryptResult.decryptStr(), originalText);
    }

    @Test
    public void PBE_WITH_SHA512_AND_AES_256_Test() throws Exception {
        String originalText = "Hello, world!";
        String password = "MySecretPassword";
        String salt = "12345678";
        String index = "1234567812345678";
        SecurityInstance securityInstance = SecurityMethod.PBE_WITH_SHA512_AND_AES_256.decryptInstance();
        // Encryption
        EncryptResult encryptResult = securityInstance.encrypt(originalText, password, index, salt, 1000);
        Assert.assertNull(encryptResult.message());
        // Decryption
        DecryptResult decryptResult = securityInstance.decrypt(encryptResult.encryptStr(), password, index, salt, 1000);
        Assert.assertEquals(decryptResult.decryptStr(), originalText);
    }

    @Test
    public void PBE_WITH_SHA256_AND_AES_128_Test() throws Exception {
        String originalText = "Hello, world!";
        String password = "MySecretPassword";
        String salt = "12345678";
        String index = "1234567812345678";
        SecurityInstance securityInstance = SecurityMethod.PBE_WITH_SHA256_AND_AES_128.decryptInstance();
        // Encryption
        EncryptResult encryptResult = securityInstance.encrypt(originalText, password, index, salt, 1000);
        Assert.assertNull(encryptResult.message());
        // Decryption
        DecryptResult decryptResult = securityInstance.decrypt(encryptResult.encryptStr(), password, index, salt, 1000);
        Assert.assertEquals(decryptResult.decryptStr(), originalText);
    }
}
