package part2.mini_injection_framework.context;

import part2.mini_injection_framework.core.Beans;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.lang.reflect.Field;

public class XMLContextImpl extends BaseContext {

    public XMLContextImpl(String configLocation){
        InputStream fileStream = getClass().getClassLoader().getResourceAsStream(configLocation);
        if (fileStream!=null){
            try {
                this.handleXMLFile(fileStream);
                return;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Configuration file location is not found inside resources");
    }

    private void handleXMLFile(InputStream inputStream) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Beans.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        // Getting all the beans in the list
        var allBeans = ((Beans) unmarshaller.unmarshal(inputStream)).getBeans();
        for (var thisBean : allBeans){
            String beanId = thisBean.getId();
            Class<?> clazz = Class.forName(thisBean.getClassName());
            var beanInstance = clazz.getConstructor().newInstance();
            this.beans.put(beanId.isEmpty()? thisBean.getClassName():beanId,beanInstance);

            var props = thisBean.getProperties();
            if(props==null)continue;

            // Setting the fields via properties
            var fields = clazz.getDeclaredFields();
            for(var prop:props){
                for (Field field:fields){
                    if(field.getName().equals(prop.getName())){
                        field.setAccessible(true);
                        Object beanVal = null;
                        if(!beanId.isEmpty()) beanVal = getBean(prop.getRef());
                        field.set(beanInstance,beanVal);
                    }
                }
            }
        }
    }
}
