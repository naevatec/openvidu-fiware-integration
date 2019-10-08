package io.openvidu.fiware.integration.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
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

    /**
     * Gets the current date in the format:
     */
    public static String getFormattedCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    /**
     * Gets the public url of the api server.
     */
    public static String getPublicUrl() {
        return processEnvironmentVariable("publicurl", Consts.DefaultOwnUrl);
    }

    /**
     * Gets the specified environment variable and returns it or the default value if it is undefined.
     */
    public static String processEnvironmentVariable(String envVarName, String defaultValue) {
        String value = System.getenv(envVarName);
        if (value == null) {
            return defaultValue;
        }

        return value;
    }
}
