package part2.metier;

import part2.dao.IDao;
import part2.mini_injection_framework.annotations.AutoWired;
import part2.mini_injection_framework.annotations.Component;

@Component
public class MetierImpl implements IMetier {
    @AutoWired
    IDao dao;

    public void setDao(IDao dao) {
        this.dao = dao;
    }

    public MetierImpl(){}
    public MetierImpl(IDao dao){this.dao=dao;}
    @Override
    public double calcul() {
        return dao.getData()*1.1d;
    }
}
