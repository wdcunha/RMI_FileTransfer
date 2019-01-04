import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.io.BufferedInputStream;

public class FileClient  extends UnicastRemoteObject implements FileClientInt {

  private String file = "";
  private String checksumServer = "";
  private String checksumClient = "";

  public  FileClient() throws RemoteException {
		super();
	}

  public void setFile(String f) throws RemoteException {
	  file = f;
  }

  public void setCheckSumServer(String chk) throws RemoteException {
	  checksumServer = chk;
  }

  public void setCheckSumClient(String chk) throws RemoteException {
	  checksumClient = chk;
  }

  public boolean clientReceiveData(String filename, byte[] data, int len, String checksum) throws RemoteException{
	  try{
		  setFile(filename);
		  setCheckSumServer(checksum);
		  File f = new File(filename);
		  f.createNewFile();
		  FileOutputStream out = new FileOutputStream(f,true);
		  out.write(data,0,len);
		  out.flush();
		  out.close();
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
		  MessageDigest digest = MessageDigest.getInstance("SHA1");
		  BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		  while ((count = bis.read(buffer)) > 0) {
			  digest.update(buffer, 0, count);
		  }
		  bis.close();
		  byte[] hash = digest.digest();
		  StringBuffer sb = new StringBuffer("");
		  for (int i = 0; i < hash.length; i++) {
			  sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
		  }
		  setCheckSumClient(sb.toString());
		  compareChecksum();
	  } catch(Exception e){
		  e.printStackTrace();
	  }
	  return true;
  }
  // TODO check 
  public boolean compareChecksum() throws RemoteException{
	  try {
		  if(checksumClient.equals(checksumServer)) {
			  System.out.println("Checksum test ok: " + checksumClient);
		  } else {
			  System.out.println("Checksum NOT passed: " + checksumClient + " x " + checksumServer);
		  }
	  } catch(Exception e) {
		  e.printStackTrace();
	  }
	  return true;
  }
  
}


// #BUMP
