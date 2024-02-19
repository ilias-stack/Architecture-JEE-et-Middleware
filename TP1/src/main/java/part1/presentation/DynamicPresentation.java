package part1.presentation;

import part1.dao.IDao;
import part1.metier.IMetier;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Scanner;

public class DynamicPresentation {
    public static void main(String[] args) throws Exception {
        Scanner fileRetriever = new Scanner(Objects.requireNonNull(DynamicPresentation.class.getResourceAsStream("/config.txt")));
        String daoClassName = fileRetriever.nextLine();
        String metierClassName = fileRetriever.nextLine();

        Class<?> daoClass = Class.forName(daoClassName);
        Class<?> metierClass = Class.forName(metierClassName);

        IDao dao =(IDao) daoClass.getConstructor().newInstance();
        IMetier metier =(IMetier) metierClass.getConstructor().newInstance();

        Method setDao =metierClass.getMethod("setDao", IDao.class);
        setDao.invoke(metier,dao);

        System.out.println("The result is "+metier.calcul());

    }
}
