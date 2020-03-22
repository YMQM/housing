package priv.ymqm.housing.common.util;


import com.google.common.annotations.Beta;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Bytes;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * @author chenhonnian
 * @since 2020/03/21
 */
public class EncryptionUtil {
    private static final int SALT_ITERATION_TIMES = 10;

    private static final int DEFAULT_SALT_LENGTH = 128;

    private static final byte[] USER_TOKEN_HMAC_KEY_BYTES = getStrAsBytes("ymqm_housing_2020");

    private EncryptionUtil() {
    }

    public static String createUserToken() {
        UUID uuid = UUID.randomUUID();
        byte[] uuidBytes = getStrAsBytes(uuid.toString());
        HashFunction hmacMd5 = Hashing.hmacMd5(USER_TOKEN_HMAC_KEY_BYTES);
        HashCode uuidHashCode = hmacMd5.hashBytes(uuidBytes);
        return uuidHashCode.toString();
    }

    public static String sha256Hash(String content, String salt) {
        byte[] contentBytes = getStrAsBytes(content);
        byte[] saltBytes = getStrAsBytes(salt);
        HashFunction md5 = Hashing.md5();
        byte[] iteration_bytes = md5.hashBytes(saltBytes).asBytes();
        HashFunction sha256 = Hashing.sha256();
        HashCode contentHashCode = null;
        for (int i = 0; i < SALT_ITERATION_TIMES; i++) {
            Hasher hasher = sha256.newHasher();
            hasher.putBytes(contentBytes);
            hasher.putBytes(iteration_bytes);
            contentHashCode = hasher.hash();
            contentBytes = contentHashCode.asBytes();
        }
        String hexStr = contentHashCode.toString();
        return hexStr;
    }

    public static String generateRandomSalt(int saltLength) {
        return RandomStringUtils.randomAscii(saltLength);
    }

    public static String generateRandomSalt() {
        return generateRandomSalt(DEFAULT_SALT_LENGTH);
    }

    public static byte[] getStrAsBytes(String content) {
        byte[] bytes = content.getBytes(Charsets.UTF_8);
        return bytes;
    }

    public static void main(String[] args) {
        String pass = "chn971229";
        String salt = generateRandomSalt();
        String hashPass = sha256Hash(pass, salt);
        System.out.println("hashPass len: " + hashPass.length());
        System.out.println("hashPass: " + hashPass);
        System.out.println("salt len: " + salt.length());
        System.out.println("salt: " + salt);
    }
}
