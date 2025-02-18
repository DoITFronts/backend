package com.codeit.side.common.adapter.out.security;

import com.codeit.side.common.application.port.out.CustomPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * SpringSecurity 의존성을 제외한 password encoder
 */
@Component
public class CustomPasswordEncoderImpl implements CustomPasswordEncoder {
    private static final int SALT_LENGTH = 16;

    public String encode(CharSequence rawPassword) {
        byte[] salt = generateSalt();
        byte[] hash = hash(rawPassword.toString().getBytes(), salt);

        byte[] saltedHash = new byte[salt.length + hash.length];
        System.arraycopy(salt, 0, saltedHash, 0, salt.length);
        System.arraycopy(hash, 0, saltedHash, salt.length, hash.length);

        return Base64.getEncoder().encodeToString(saltedHash);
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        byte[] saltedHash = Base64.getDecoder().decode(encodedPassword);

        byte[] salt = new byte[SALT_LENGTH];
        byte[] hash = new byte[saltedHash.length - SALT_LENGTH];

        System.arraycopy(saltedHash, 0, salt, 0, SALT_LENGTH);
        System.arraycopy(saltedHash, SALT_LENGTH, hash, 0, hash.length);

        byte[] checkHash = hash(rawPassword.toString().getBytes(), salt);

        return MessageDigest.isEqual(hash, checkHash);
    }

    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    private byte[] hash(byte[] password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            return md.digest(password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
}
