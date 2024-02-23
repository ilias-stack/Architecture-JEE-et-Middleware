package part2.mini_injection_framework.core;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "bean")
@XmlAccessorType(XmlAccessType.FIELD)
public class Bean implements Serializable {
    @XmlAttribute
    private String id;
    @XmlAttribute
    private String className;
    private List<Property> property;

    public String getId() {
        return id;
    }
    public String getClassName() {
        return className;
    }
    public List<Property> getProperties() {
        return property;
    }
    public void setAll(String id, String className, List<Property> properties) {
        this.id = id;
        this.className = className;
        this.property = properties;
    }
}
