package part2.mini_injection_framework.core;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;
@XmlRootElement(name = "beans")
@XmlAccessorType(XmlAccessType.FIELD)
public class Beans implements Serializable {
    @XmlElement
    List<Bean> bean;

    public void setBeans(List<Bean> beans) {
        this.bean = beans;
    }

    public List<Bean> getBeans() {
        return bean;
    }
}
