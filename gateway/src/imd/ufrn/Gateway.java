package imd.ufrn;

public class Gateway {

    private final GatewayCfg config;

    private GwServer server;
    private GwClient client;

    public Gateway() {
        config = new GatewayCfg();
    }

    public GatewayCfg cfg() {return config;}

    private void  start() {
        client = new GwClient(config);
        server = new GwServer(config, client);
    }

    public static void main(String[] args) {
        Gateway g = new Gateway();
        g.start();
    }

}