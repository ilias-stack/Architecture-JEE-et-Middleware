package part2.presentation;

import part2.mini_injection_framework.context.IContext;
import part2.mini_injection_framework.context.XMLContextImpl;

public class XMLPresentation {
    public static void main(String[] args) {
        IContext context = new XMLContextImpl("configuration.xml");
//        beans
    }
}
