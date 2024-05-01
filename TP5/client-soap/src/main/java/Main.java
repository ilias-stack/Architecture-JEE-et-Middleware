import proxy.BanqueService;
import proxy.BanqueWS;
import proxy.Compte;

public class Main {
    public static void main(String[] args) {
        BanqueService proxy = new BanqueWS().getBanqueServicePort();
        System.out.println(proxy.conversionEuroToDH(10)+"\n");

        Compte cp12 = proxy.getCompte(12);
        System.out.println("--------------------------------");
        System.out.println(cp12.getCode());
        System.out.println(cp12.getSolde());
        System.out.println(cp12.getDateCreation());

        proxy.listComptes().forEach(cp ->{
            System.out.println("--------------------------------");
            System.out.println(cp.getCode());
            System.out.println(cp.getSolde());
            System.out.println(cp.getDateCreation());
        });

    }
}
