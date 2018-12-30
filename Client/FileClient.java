import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.security.MessageDigest;
import java.io.BufferedInputStream;

public class FileClient  extends UnicastRemoteObject implements FileClientInt {

  private String file = "";

  public  FileClient() throws RemoteException {
		super();
	}

	public void setFile(String f){
		file = f;
	}

	public boolean clientReceiveData(String filename, byte[] data, int len) throws RemoteException{
    try{
      setFile(filename);
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
    	digest.update(data, 0, len);
      File f = new File(filename);
    	f.createNewFile();
    	FileOutputStream out = new FileOutputStream(f,true);
    	out.write(data,0,len);
    	out.flush();
    	out.close();
      byte[] hash = digest.digest();
      System.out.println("MessageDigest: " + hash.toString());
    	System.out.println("Done writing file!");
      checksum();
    } catch(Exception e){
    	e.printStackTrace();
    }
		return true;
	}

  public boolean checksum() throws RemoteException{
		try {
			byte[] buffer= new byte[1024];
			int count;
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			while ((count = bis.read(buffer)) > 0) {
				digest.update(buffer, 0, count);
			}
			bis.close();
			byte[] hash = digest.digest();
			String base64encodedString = Base64.getEncoder().encodeToString(hash);
			// System.out.println(new Base64.Encoder().encode(hash));
			System.out.println("MessageDigest: " + hash.toString());
			System.out.println("base64encodedString: " + base64encodedString);
		} catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}

}
