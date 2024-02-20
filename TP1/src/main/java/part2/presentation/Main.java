package part2.presentation;

import part2.metier.IMetier;
import part2.mini_injection_framework.context.AnnotationContextImpl;
import part2.mini_injection_framework.context.IContext;

public class Main {
    public static void main(String[] args) throws Exception {
        IContext context = new AnnotationContextImpl("part2.dao","part2.metier");
        IMetier metier =(IMetier) context.getBean(IMetier.class);
        System.out.println(metier.calcul());

    }
}
