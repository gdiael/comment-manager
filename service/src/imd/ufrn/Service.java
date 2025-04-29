package imd.ufrn;

public class Service {

    private ServiceCfg config;
    private SvClient client;
    private SvServer server;
    private SvProps props;
    
    public Service() {
        config = new ServiceCfg();
        props = new SvProps();
    }

    public ServiceCfg cfg() {return config;}

    private void  start() {
        client = new SvClient(config);
        server = new SvServer(config, client, props);
    }

    public static void main(String[] args) {
        Service g = new Service();
        g.start();
    }

}
