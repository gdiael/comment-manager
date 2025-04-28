package imd.ufrn;

public class Service {

    private ServiceCfg config;
    private SvClient client;
    private SvServer server;
    
    public Service() {
        config = new ServiceCfg();
    }

    public ServiceCfg cfg() {return config;}

    private void  start() {
        client = new SvClient(config);
        server = new SvServer(config);
    }

    public static void main(String[] args) {
        Service g = new Service();
        g.start();
    }

}
