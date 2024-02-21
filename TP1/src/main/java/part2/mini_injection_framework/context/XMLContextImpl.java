package part2.mini_injection_framework.context;

import part2.mini_injection_framework.core.BeanException;

import java.io.InputStream;
import java.util.HashMap;

public class XMLContextImpl implements IContext {
    private final HashMap<String, Object> beans = new HashMap<>();

    public XMLContextImpl(String configLocation){
        InputStream i = getClass().getClassLoader().getResourceAsStream(configLocation);

    }
    @Override
    public HashMap<String, Object> getAllBeans()  {
        return null;
    }

    @Override
    public Object getBean(Class<?> classType) throws BeanException {
        return null;
    }

    @Override
    public Object getBean(String beanName) throws BeanException {
        return null;
    }
}
