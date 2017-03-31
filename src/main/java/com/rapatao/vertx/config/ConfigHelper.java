package com.rapatao.vertx.config;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by rapatao on 31/03/17.
 */
public class ConfigHelper {

    private static final Logger logger = LoggerFactory.getLogger(ConfigHelper.class);

    public static String getConfigAsString(final String configFile) {
        try {
            final URL resource = ConfigHelper.class.getResource(configFile);
            final List<String> readAllLines = Files.readAllLines(Paths.get(resource.toURI()));
            return String.join("\n", readAllLines.toArray(new String[readAllLines.size()]));
        } catch (Exception e) {
            logger.warn("Error when loading config file and generated an empty configuration.", e);
        }
        return "";
    }

    public static JsonObject getConfigAsJsonObject(final String configFile) {
        return new JsonObject(getConfigAsString(configFile));
    }

    public static DeploymentOptions getConfigAsDeploymentOptions(final String configFile) {
        return new DeploymentOptions().setConfig(getConfigAsJsonObject(configFile));
    }

}
