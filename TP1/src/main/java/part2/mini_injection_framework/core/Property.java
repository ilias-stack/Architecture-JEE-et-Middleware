package part2.mini_injection_framework.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Property implements Serializable {
    @XmlAnyAttribute
    private String name;
    @XmlAnyAttribute
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
