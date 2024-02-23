package part2.mini_injection_framework.core;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
@XmlRootElement(name = "property")
@XmlAccessorType(XmlAccessType.FIELD)
public class Property implements Serializable {
    @XmlAttribute
    private String name;
    @XmlAttribute
    private String ref;

    public String getName() {
        return name;
    }

    public String getRef() {
        return ref;
    }

    public void setAll(String name, String ref) {
        this.name = name;
        this.ref = ref;
    }
}
