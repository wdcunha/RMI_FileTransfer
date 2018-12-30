import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.io.BufferedInputStream;
import java.util.Base64.*;

public class FileServer  extends UnicastRemoteObject implements FileServerInt {

	private String file = "";

	protected FileServer() throws RemoteException {
		super();
	}

	public void setFile(String f){
		file = f;
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
				 fc.sendData(fileName, fileData, fileLen);
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


	public boolean createFile() throws RemoteException{
		try {
			String fileName = "file1k";
			RandomAccessFile f = new RandomAccessFile(fileName, "rw");
			int startLimit = 97; // letter 'a'
			int finalLimit = 122; // letter 'z'
			int targetStringLength = 2000;
			Random random = new Random();
			StringBuilder buffer = new StringBuilder(targetStringLength);

			// varialbes used for dividing in letter groups according to the random length
			int count = 0;
			int[] wordLen = {2,3,4,5,6,7};
			Random randomArr = new Random();
			int leng = wordLen[randomArr.nextInt(wordLen.length)];

			for (int i = 0; i < targetStringLength; i++) {
				int randomLimitedInt = startLimit + (int)
				(random.nextFloat() * (finalLimit - startLimit + 1));
				buffer.append((char) randomLimitedInt);

				// check if the randomly length (leng) was gotten
				// then count take a new lenght
				count++;
				if(count == leng){
					buffer.append('\u0009');
					leng = wordLen[randomArr.nextInt(wordLen.length)];
					count = 0;
				}
			}
			String generatedString = buffer.toString();

			System.out.println(generatedString);
			f.writeUTF(generatedString);
			f.setLength(1024);

			System.out.println(f);
			System.out.println("File created succesfully!");
			f.close();
			setFile(fileName);

		} catch(Exception e) {
			e.printStackTrace();
		}

		return true;
	}

}
