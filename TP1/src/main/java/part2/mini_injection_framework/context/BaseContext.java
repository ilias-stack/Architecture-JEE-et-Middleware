package part2.mini_injection_framework.context;

import part2.mini_injection_framework.core.BeanException;

import java.util.HashMap;

public class BaseContext implements IContext{
    protected final HashMap<String, Object> beans = new HashMap<>();

    @Override
    public HashMap<String, Object> getAllBeans() {
        return beans;
    }

    @Override
    public Object getBean(Class<?> classType) throws BeanException {
        for (Object instance : beans.values()) {
            if (classType.isInstance(instance)) {
                return instance;
            }
        }
        throw new BeanException("Bean of this class was not found.");
    }

    @Override
    public Object getBean(String beanName) throws BeanException {
        Object instance = beans.get(beanName);
        if (instance == null) throw new BeanException("Bean of with this name was not found.");
        return instance;
    }
}
