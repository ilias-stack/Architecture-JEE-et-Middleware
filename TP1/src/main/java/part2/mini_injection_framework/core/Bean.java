package part2.mini_injection_framework.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Bean implements Serializable {
    @XmlAnyAttribute
    private String id;
    @XmlAnyAttribute
    private String className;
    private List<Property> properties;

    public String getId() {
        return id;
    }
    public String getClassName() {
        return className;
    }
    public List<Property> getProperties() {
        return properties;
    }
    public void setAll(String id, String className, List<Property> properties) {
        this.id = id;
        this.className = className;
        this.properties = properties;
    }
    public Bean(){}
}
