import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Server
{
    public static void main(String[] args) throws Exception
    {
        CenterServer CA = new CenterServer();
        Registry CAregistry = LocateRegistry.createRegistry(2930);
        CAregistry.bind("Canada",CA);
        System.out.println("Canada server has started");

        CenterServer US = new CenterServer();
        Registry USregistry = LocateRegistry.createRegistry(2931);
        USregistry.bind("USA", US);
        System.out.println("USA server has started");

        CenterServer UK = new CenterServer();
        Registry UKresgistry = LocateRegistry.createRegistry(2932);
        UKresgistry.bind("UK", UK);
        System.out.println("UK server has started");
    }
}
