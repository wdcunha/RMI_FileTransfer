import java.io.File;
import java.io.FileInputStream;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.io.BufferedInputStream;

public class FileServer  extends UnicastRemoteObject implements FileServerInt {

	private String file = "";
	private String checksumServer = "";

	protected FileServer() throws RemoteException {
		super();
	}

	public void setFile(String f){
		file = f;
	}

	public void setChecksum(String chk){
		checksumServer = chk;
	}

	public boolean sendFile(FileClientInt fc) throws RemoteException{
		/* send the file...*/
		 try{
			 File f1 = new File(file);
			 FileInputStream in = new FileInputStream(f1);
			 byte [] fileData = new byte[1024];
			 int fileLen = in.read(fileData);
			 String fileName = "files/"+f1.getName();
			 while(fileLen > 0){
				 fc.clientReceiveData(fileName, fileData, fileLen, checksumServer);
				 fileLen = in.read(fileData);
			 }

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
			System.out.println("Checksum for the File: " + sb.toString());
			setChecksum(sb.toString());
		} catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}

	public boolean createFile() throws RemoteException{
		try {
			String fileName = "file1k";
			RandomAccessFile f = new RandomAccessFile(fileName, "rw");
			int startLimit = 97; // letter 'a'
			int finalLimit = 122; // letter 'z'
			int targetStringLength = 2000;
			Random random = new Random();
			StringBuilder buffer = new StringBuilder(targetStringLength);

			// Variables used for dividing in letter groups according to the random length
			int count = 0;
			int[] wordLen = {2,3,4,5,6,7};
			Random randomArr = new Random();
			int leng = wordLen[randomArr.nextInt(wordLen.length)];

			for (int i = 0; i < targetStringLength; i++) {
				int randomLimitedInt = startLimit + (int)
				(random.nextFloat() * (finalLimit - startLimit + 1));
				buffer.append((char) randomLimitedInt);

				// check if the randomly length (leng) was gotten
				// then count take a new length
				count++;
				if(count == leng){
					buffer.append('\u0009');
					leng = wordLen[randomArr.nextInt(wordLen.length)];
					count = 0;
				}
			}
			String generatedString = buffer.toString();

			f.writeUTF(generatedString);
			f.setLength(1024);
			f.close();
			setFile(fileName);
			System.out.println("File created succesfully: " + fileName);

		} catch(Exception e) {
			e.printStackTrace();
		}

		return true;
	}

}
