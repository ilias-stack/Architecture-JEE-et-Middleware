package part1.presentation.spring_presentation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import part1.metier.IMetier;

public class AnnotationPresentation {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("part1.dao","part1.metier");
        IMetier metier = context.getBean(IMetier.class);

        System.out.println(metier.calcul());
    }
}
