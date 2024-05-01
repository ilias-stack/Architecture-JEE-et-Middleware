import jakarta.xml.ws.Endpoint;
import ws.BanqueService;

public class ServerJWS {
    public static void main(String[] args) {
        final var URL ="http://0.0.0.0:9090/";
        Endpoint.publish(URL, new BanqueService());
        System.out.println("WEB SERVICE deployed in "+URL);
    }
}
