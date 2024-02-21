package part2.mini_injection_framework.core;

import java.util.Map;

public class Bean {
    private final String id;
    private final Class<?> className;
    private final Map<String,String> properties;
    public Bean(String id, Class<?> className,Map<String,String> properties) {
        this.id = id;
        this.className = className;
        this.properties = properties;
    }
    public String getId() {
        return id;
    }
    public Class<?> getClassName() {
        return className;
    }
    public Map<String, String> getProperties() {
        return properties;
    }
}
