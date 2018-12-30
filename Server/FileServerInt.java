import java.rmi.*;

public interface FileServerInt extends Remote{

	public boolean sendFile(FileClientInt fc) throws RemoteException;
	public boolean checksum() throws RemoteException;
	public boolean createFile() throws RemoteException;
	public void setFile(String f) throws RemoteException;

}
