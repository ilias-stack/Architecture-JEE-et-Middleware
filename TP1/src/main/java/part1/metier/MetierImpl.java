package part1.metier;

import org.springframework.beans.factory.annotation.Autowired;
import part1.dao.IDao;

@org.springframework.stereotype.Component("metier")
public class MetierImpl implements IMetier {
    @Autowired
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
