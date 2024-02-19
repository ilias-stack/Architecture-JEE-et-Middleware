package part1.presentation;

import part1.dao.DaoImpl;
import part1.dao.IDao;
import part1.metier.MetierImpl;

public class StaticPresentation {
    public static void main(String[] args) {
        IDao dao = new DaoImpl();
        MetierImpl metier = new MetierImpl();

        metier.setDao(dao);
        System.out.println("The result is "+metier.calcul());

    }
}
