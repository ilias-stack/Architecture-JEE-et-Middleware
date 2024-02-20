package part2.dao;

import part2.mini_injection_framework.annotations.Component;

@Component
public class DaoImpl implements IDao {
    @Override
    public double getData() {
        System.out.println("Data retrieved with Database.");
        return 20;
    }
}
