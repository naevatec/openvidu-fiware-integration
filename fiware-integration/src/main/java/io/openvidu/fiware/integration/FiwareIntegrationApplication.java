package io.openvidu.fiware.integration;

import io.openvidu.fiware.integration.config.OpenViduConfig;
import io.openvidu.fiware.integration.utils.Consts;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FiwareIntegrationApplication {
    public static void main(String[] args) {
        SpringApplication.run(FiwareIntegrationApplication.class, args);
    }

    @Bean
    public OpenViduConfig openViduInitializer() {
        // Read environment variables.
        String openViduUrl = processEnvironmentVariable("openvidu.publicurl", Consts.DefaultOpenViduUrl);
        String openViduSecret = processEnvironmentVariable("openvidu.secret", Consts.DefaultOpenViduSecret);

        return new OpenViduConfig(openViduUrl, openViduSecret);
    }

    /**
     * Gets the specified environment variable and returns it or the default value if it is undefined.
     */
    private static String processEnvironmentVariable(String envVarName, String defaultValue) {
        String value = System.getenv(envVarName);
        if (value == null) {
            return defaultValue;
        }

        return value;
    }
}
