import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileClientInt extends Remote{

	public boolean clientReceiveData(String filename, byte[] data, int len) throws RemoteException;

}
