package imd.ufrn;

public class Gateway {

    private final GatewayCfg config;
    
    public Gateway() {
        config = new GatewayCfg();
    }

    public GatewayCfg cfg() {return config;}

    private void  start() {
        new GwServer(config);
    }

    public static void main(String[] args) {
        Gateway g = new Gateway();
        g.start();
    }

}