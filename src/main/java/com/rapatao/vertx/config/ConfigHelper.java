package com.rapatao.vertx.config;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by rapatao on 31/03/17.
 */
public class ConfigHelper {

    private static final Logger logger = LoggerFactory.getLogger(ConfigHelper.class);

    public static String getConfigAsString(final String filename) {
        String result = null;

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = ClassLoader.getSystemResource(filename).openStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            result = bufferedReader.lines().reduce((a, b) -> a + b).orElse(null);
        } catch (Exception e) {
            logger.warn("Error when loading config file and generated an empty configuration.", e);
        } finally {
            close(bufferedReader);
            close(inputStreamReader);
            close(inputStream);
        }
        return result;
    }

    public static JsonObject getConfigAsJsonObject(final String configFile) {
        return new JsonObject(getConfigAsString(configFile));
    }

    public static DeploymentOptions getConfigAsDeploymentOptions(final String configFile) {
        return new DeploymentOptions().setConfig(getConfigAsJsonObject(configFile));
    }

    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

}
