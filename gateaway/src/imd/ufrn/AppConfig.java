package imd.ufrn;

public class AppConfig {
    private final String urlHost;
    private final int urlPort;
    private final String webMode; // UDP, TCP, HTTP

    public AppConfig() {
        urlHost = getEnvWithDefault("URL_HOST", "localhost");
        urlPort = Integer.parseInt(getEnvWithDefault("URL_PORT", "8080"));
        webMode = getEnvWithDefault("WEB_MODE", "HTTP");
    }

    private String getEnvWithDefault(String envName, String defaultValue) {
        String value = System.getenv(envName);
        return (value != null) ? value : defaultValue;
    }

    // Getters
    public String getHost() {return urlHost;}
    public int getPort() {return urlPort;}
    public String getWebMode() {return webMode;}

}
