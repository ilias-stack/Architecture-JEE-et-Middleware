package part2.mini_injection_framework.context;

import part2.mini_injection_framework.annotations.AutoWired;
import part2.mini_injection_framework.annotations.Component;
import part2.mini_injection_framework.annotations.Qualifier;
import part2.mini_injection_framework.core.BeanException;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Scanner;

public class AnnotationContextImpl extends BaseContext {

    public AnnotationContextImpl(String... basePackages) throws BeanException{
        for (String basePackage : basePackages) {
            this.scanPackage(basePackage);
        }
    }

    private int identifyConstructorIndex(Constructor<?>[] constructorList,Class<?>[] idealConstructor){
        for (int i=0;i<constructorList.length;i++) {
            var constructor = constructorList[i].getParameterTypes();
            if(Arrays.equals(constructor, idealConstructor)) return i;
        }
        return 0;
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
                            // checking if is annotated with Component notation
                            if (clazz.isAnnotationPresent(Component.class)) {
                                Component componentAnnotation = clazz.getAnnotation(Component.class);
                                String compValue = componentAnnotation.value();
                                Constructor<?>[] constructors = clazz.getDeclaredConstructors();
                                Class<?>[] idealConstructor = {};
                                Object[] parameters;
                                Object instance = null;
                                // Checking for the optimal constructor for constructor injection
                                for (Constructor<?> item : constructors) {
                                    var constructor = item.getParameterTypes();
                                    if (constructor.length > idealConstructor.length) idealConstructor = constructor;
                                }
                                parameters = new Object[idealConstructor.length];
                                for (int i=0;i< parameters.length;i++)
                                    parameters[i] = this.getBean(idealConstructor[i]);

                                instance = clazz.getDeclaredConstructors()[this.identifyConstructorIndex(constructors,idealConstructor)].newInstance(parameters);

                                // Adding the bean based on class or name
                                if(compValue.isEmpty()) beans.put(className, instance);
                                else beans.put(compValue, instance);

                                // Checking for Autowired fields
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
}
