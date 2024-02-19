package part2.presentation;

import part1.dao.IDao;
import part2.mini_injection_framework.context.AnnotationContextImpl;
import part2.mini_injection_framework.context.IContext;

public class Main {
    public static void main(String[] args) throws Exception {
        IContext context = new AnnotationContextImpl("part2.dao","part2.metier");
//        IMetier metier =(IMetier) context.getBean(IMetier.class);
        IDao dao =(IDao) context.getBean(IDao.class);
//        System.out.println(metier.calcul());
        System.out.println(dao+" ");
    }
}
