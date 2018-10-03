import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ManagerClient
{
    public static void main(String[] args)
    {
        try
        {
            Registry CAregistry = LocateRegistry.getRegistry(2930);
            CenterServerInterface CA = (CenterServerInterface) CAregistry.lookup("Canada");

            Registry USregistry = LocateRegistry.getRegistry(2931);
            CenterServerInterface US = (CenterServerInterface) USregistry.lookup("USA");

            Registry UKregistry = LocateRegistry.getRegistry(2932);
            CenterServerInterface UK = (CenterServerInterface) UKregistry.lookup("UK");
        }
        catch (AccessException e)
        {
            System.out.println(e.getMessage());
        }
        catch (RemoteException e)
        {
            System.out.println(e.getMessage());
        }
        catch (NotBoundException e)
        {
            System.out.println("Error: Invalid server - " + e.getMessage());
        }
    }
}
