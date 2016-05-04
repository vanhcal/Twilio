package utils;

import java.util.Map;

public class Credentials {
    private Map<String, String> env;

    public Credentials() {
        env = System.getenv();
    }
    public String getGaToken() {
    	return env.get("GA_TOKEN");
    }
}