package part2.mini_injection_framework.context;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

public class XMLContextImpl extends BaseContext {

    public XMLContextImpl(String configLocation){
        InputStream fileStream = getClass().getClassLoader().getResourceAsStream(configLocation);
        if (fileStream!=null){
            try {
                this.handleXMLFile(fileStream);
                return;
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Configuration file location is not found inside resources");
    }

    private void handleXMLFile(InputStream inputStream) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance();
        Unmarshaller unmarshaller = context.createUnmarshaller();
    }
}
