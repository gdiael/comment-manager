package imd.ufrn;

public class Gateway {

    private final AppConfig config;
    
    public Gateway() {
        config = new AppConfig();
    }

    public AppConfig cfg() {return config;}

    public static void main(String[] args) {
        Gateway g = new Gateway();
        //impressao das variaveis para verificacao
        System.out.println("Host: " + g.cfg().getHost());
        System.out.println("Port: " + g.cfg().getPort());
        System.out.println("Mode: " + g.cfg().getWebMode());
    }

}