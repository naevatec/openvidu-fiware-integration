package io.openvidu.fiware.integration;

import io.openvidu.fiware.integration.config.OpenViduConfig;
import io.openvidu.fiware.integration.config.OrionConnectorConfiguration;
import io.openvidu.fiware.integration.daos.OrionConnector;
import io.openvidu.fiware.integration.daos.publisher.CameraPublisher;
import io.openvidu.fiware.integration.daos.publisher.OrionDeviceEventPublisher;
import io.openvidu.fiware.integration.daos.reader.CameraReader;
import io.openvidu.fiware.integration.models.orion.Device;
import io.openvidu.fiware.integration.utils.Consts;
import io.openvidu.fiware.integration.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
public class FiwareIntegrationApplication {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(FiwareIntegrationApplication.class, args);
    }

    @Bean
    public OpenViduConfig openViduInitializer() {
        // Read environment variables.
        String url = Utils.processEnvironmentVariable("openvidu.publicurl", Consts.DefaultOpenViduUrl);
        String filterUrl = Utils.processEnvironmentVariable("openvidu.filterurl", Consts.DefaultOpenViduFilterUrl);
        String secret = Utils.processEnvironmentVariable("openvidu.secret", Consts.DefaultOpenViduSecret);

        log.debug("Using openvidu.publicurl = " + url);
        log.debug("Using openvidu.filterurl = " + filterUrl);
        log.debug("Using openvidu.secret    = " + secret);

        return new OpenViduConfig(url, filterUrl, secret);
    }

    @Bean
    public OrionConnectorConfiguration orionConfiguration() throws Exception {
        // Read environment variables.
        String url = Utils.processEnvironmentVariable("orion.publicurl", Consts.DefaultOrionUrl);

        log.debug("Using orion.publicurl    = " + url);

        OrionConnectorConfiguration config = new OrionConnectorConfiguration();
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            log.error("The orion.publicurl is not a correct URL: " + url, e);
            throw new Exception("The orion.publicurl is not a correct URL: " + url, e);
        }

        config.setOrionScheme(uri.getScheme());
        config.setOrionHost(uri.getHost());
        config.setOrionPort(uri.getPort());

        // Test the configuration is ok.
        OrionConnector publisher = new OrionConnector<>(config, Device.class);
        publisher.ping();

        return config;
    }

    @Bean
    public CameraPublisher cameraPublisher(OrionConnectorConfiguration config) {
        return new CameraPublisher(config);
    }

    @Bean
    public OrionDeviceEventPublisher plateDetectedEventPublisher(OrionConnectorConfiguration config) {
        return new OrionDeviceEventPublisher(config);
    }

    @Bean
    public CameraReader cameraReader(OrionConnectorConfiguration config) {
        return new CameraReader(config);
    }
}
