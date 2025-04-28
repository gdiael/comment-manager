package imd.ufrn;

public class SvProcessor {
    
    public SvProcessor() {

    }

    public static String processUDP(String msg) {
        System.out.println("todo: process UDP | msg: " + msg);
        return "500|fail";
    }

    public static String processTCP(String msg) {
        System.out.println("todo: tokenizer TCP | msg: " + msg);
        return "500|fail";
    }

    public static String processHTTP(String msg) {
        System.out.println("todo: tokenizer HTTP | msg: " + msg);
        return "500|fail";
    }
}
