package part2.mini_injection_framework.context;

import part2.mini_injection_framework.core.BeanException;

import java.util.HashMap;

public class XMLContextImpl implements IContext {
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
