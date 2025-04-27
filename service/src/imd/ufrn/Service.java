package imd.ufrn;

public class Service {

    private final ServiceCfg config;
    
    public Service() {
        config = new ServiceCfg();
    }

    public ServiceCfg cfg() {return config;}

    private void  start() {
        new SvServer(config);
    }

    public static void main(String[] args) {
        Service g = new Service();
        g.start();
    }

}
