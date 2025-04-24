package imd.ufrn;

public class Gateway {

    private final AppConfig config;
    
    public Gateway() {
        config = new AppConfig();
    }

    public AppConfig cfg() {return config;}

    private void  start() {
        // impressao das variaveis para verificacao
        // String url = String.format("%s:%d/%s", config.getHost(), config.getPort(), config.getWebMode());
        // System.out.println(url);
        new Server(config);
    }

    public static void main(String[] args) {
        Gateway g = new Gateway();
        g.start();
    }

}