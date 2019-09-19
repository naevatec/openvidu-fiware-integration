package io.openvidu.fiware.integration.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    /**
     * Changes the cameraUuid to get the session name.
     */
    public static String cameraUuidToSession(String cameraUuid) throws NoSuchAlgorithmException {
        MessageDigest salt = MessageDigest.getInstance("SHA-256");
        salt.update(cameraUuid.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(salt.digest());
    }

    /**
     * Converts the bytes to an string.
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
