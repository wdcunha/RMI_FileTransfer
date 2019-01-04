import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileClientInt extends Remote{

	public void setFile(String f) throws RemoteException;
	public void setCheckSumServer(String chk) throws RemoteException;
	public void setCheckSumClient(String chk) throws RemoteException;
	public boolean clientReceiveData(String filename, byte[] data, int len, String checksum) throws RemoteException;
	public boolean checksum() throws RemoteException;
}
