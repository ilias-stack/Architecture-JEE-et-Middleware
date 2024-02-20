package part2.mini_injection_framework.context;

import part2.mini_injection_framework.annotations.AutoWired;
import part2.mini_injection_framework.annotations.Component;
import part2.mini_injection_framework.annotations.Qualifier;
import part2.mini_injection_framework.core.BeanException;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Scanner;

public class AnnotationContextImpl implements IContext {

    private final HashMap<String, Object> beans = new HashMap<>();

    @Override
    public HashMap<String, Object> getAllBeans() {
        return beans;
    }

    public AnnotationContextImpl(String... basePackages) throws BeanException{
        for (String basePackage : basePackages) {
            this.scanPackage(basePackage);
        }
    }

    private void scanPackage(String packageName) throws BeanException{
        String packagePath = packageName.replace('.', '/');
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(packagePath);
            if (inputStream != null) {
                Scanner scanner = new Scanner(inputStream);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.endsWith(".class")) {
                        String className = packageName + "." + line.substring(0, line.lastIndexOf('.'));
                        try {
                            Class<?> clazz = Class.forName(className);
                            if (clazz.isAnnotationPresent(Component.class)) {
                                Component componentAnnotation = clazz.getAnnotation(Component.class);
                                String compValue = componentAnnotation.value();
                                Object instance = clazz.getDeclaredConstructor().newInstance();
                                if(compValue.isEmpty()) beans.put(className, instance);
                                else beans.put(compValue, instance);

                                for (Field field:clazz.getDeclaredFields()) {
                                    if(field.isAnnotationPresent(AutoWired.class)){
                                        field.setAccessible(true);
                                        Object value=null;
                                        if(field.isAnnotationPresent(Qualifier.class)){
                                            Qualifier qualifier = field.getAnnotation(Qualifier.class);
                                            if(qualifier.value().isEmpty()) value = this.getBean(field.getType());
                                            else value = this.getBean(qualifier.value());
                                        }
                                        else value = this.getBean(field.getType());

                                        field.set(instance,value);
                                    }
                                }
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                            throw new BeanException("Bean instantiation error.");
                        }
                    }
                }
                scanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
