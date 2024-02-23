package part2.presentation;

import part2.metier.IMetier;
import part2.mini_injection_framework.context.IContext;
import part2.mini_injection_framework.context.XMLContextImpl;
import part2.mini_injection_framework.core.BeanException;

public class XMLPresentation {
    public static void main(String[] args) throws BeanException {
        IContext context = new XMLContextImpl("configuration.xml");
        IMetier metier =(IMetier) context.getBean(IMetier.class);
        System.out.println(metier.calcul());
    }
}
