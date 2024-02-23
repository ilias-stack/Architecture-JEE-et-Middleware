package part2.mini_injection_framework.core;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Beans implements Serializable {
    @XmlElement
    List<Bean> beans;

    public void setBeans(List<Bean> beans) {
        this.beans = beans;
    }

    public List<Bean> getBeans() {
        return beans;
    }
}
