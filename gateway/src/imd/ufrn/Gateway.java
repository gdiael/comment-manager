package imd.ufrn;

public class Gateway {

    private final GatewayCfg config;

    private GwClient client;
    private GwProps props;

    public Gateway() {
        config = new GatewayCfg();
        props = new GwProps();
    }

    public GatewayCfg cfg() {return config;}

    private void  start() {
        client = new GwClient(config);
        new GwServer(config, client, props);
    }

    public static void main(String[] args) {
        Gateway g = new Gateway();
        g.start();
    }

}