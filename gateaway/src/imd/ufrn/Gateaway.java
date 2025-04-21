package imd.ufrn;

public class Gateaway {

    private final AppConfig config;
    
    public Gateaway() {
        config = new AppConfig();
    }

    public AppConfig cfg() {return config;}

}