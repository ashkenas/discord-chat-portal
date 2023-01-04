package com.jacobashkenas.dcp;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.regex.Pattern;

public class DCPPropertiesHandler {
    private final Pattern WEBHOOK_URL_PATTERN = Pattern.compile("https://discord\\.com/api/webhooks/[0-9]+/.+");

    public final String URL;
    public final boolean FACE_LEFT;

    public DCPPropertiesHandler() {
        URL = "";
        FACE_LEFT = true;
    }

    public DCPPropertiesHandler(Properties properties, Path path) {
        String parsedURL = properties.getProperty("url", "");
        if (!WEBHOOK_URL_PATTERN.matcher(parsedURL).matches()) {
            DiscordChatPortal.INSTANCE.getLOGGER()
                    .info("Invalid webhook URL provided. Must match format: https://discord.com/api/webhooks/<ID>/<token>");
            this.URL = "";
        } else {
            this.URL = parsedURL;
        }
        FACE_LEFT = !properties.getProperty("head-direction", "left").equals("right");
        saveProperties(path, properties);
    }

    protected static DCPPropertiesHandler loadProperties(Path path) {
        Properties properties = new Properties();
        properties.setProperty("url", "");
        properties.setProperty("head-direction", "left");

        try {
            InputStream inputStream = Files.newInputStream(path);
            properties.load(inputStream);

            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Throwable error) {
            DiscordChatPortal.INSTANCE.getLOGGER()
                    .error("Failed to load webhook config.");
        }

        return new DCPPropertiesHandler(properties, path);
    }

    private static void saveProperties(Path path, Properties properties) {
        try {
            OutputStream outputStream = Files.newOutputStream(path);
            properties.store(outputStream, "Discord Webhook Config");

            if (outputStream != null) {
                outputStream.close();
            }
        } catch (Throwable error) {
            DiscordChatPortal.INSTANCE.getLOGGER()
                    .error("Failed to save webhook config.");
        }
    }
}
