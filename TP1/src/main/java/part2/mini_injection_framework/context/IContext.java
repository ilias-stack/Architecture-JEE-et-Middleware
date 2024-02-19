package part2.mini_injection_framework.context;

import part2.mini_injection_framework.core.BeanException;

import java.util.HashMap;

public interface IContext {
    HashMap<String,Object> getAllBeans();
    Object getBean(Class<?> classType) throws BeanException;
    public Object getBean(String beanName) throws BeanException;
}
