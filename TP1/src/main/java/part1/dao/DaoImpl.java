package part1.dao;

import org.springframework.stereotype.Component;

@Component(value="dao")
public class DaoImpl implements IDao {
    @Override
    public double getData() {
        System.out.println("Data retrieved with Database.");
        return 20;
    }
}
