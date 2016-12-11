import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Hashtable;

public class FileDownloadImple extends UnicastRemoteObject implements
		FileDownloadInterface {
	private static final long serialVersionUID = -3095454947035183612L;

	public FileDownloadImple() throws Exception {
		int port = 1099;
		FileInformationForm.appendText("Create Registry @  " + port);
		LocateRegistry.createRegistry(port);
		FileInformationForm.appendText("Done.............................");
		FileInformationForm.appendText("Create Ulr @ rmi://127.0.0.1:" + port
				+ "/ftpfile");
		urlHash = new Hashtable<String, ArrayList<String>>();
		try {
			Naming.bind("ftpfile", this);
		} catch (Exception e) {

			e.printStackTrace();
		}
		FileInformationForm.appendText("Done.............................");
		File[] fileList = new File("files").listFiles();
		for (int i = 0; i < fileList.length; i++) {
			addClient(fileList[i].getName(), "rmi://127.0.0.1/ftpfile");
		}
	}

	public byte[] getFileContent(String fileName, int start, int end)
			throws RemoteException {
		try {
			FileInformationForm.appendText(" File Name : " + fileName);
			FileInformationForm.appendText(" Start : " + start);
			FileInformationForm.appendText(" End : " + end);
			FileInputStream inputData = new FileInputStream("files/" + fileName);
			byte[] dataByte = new byte[end - start];
			inputData.read(dataByte);
			inputData.close();

			return dataByte;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setFileContent(String fileName, byte[] fileContent)
			throws RemoteException {
		try {
			FileOutputStream outputData = new FileOutputStream("files/"
					+ fileName);
			outputData.write(fileContent);
			outputData.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String[] getFileLists() throws RemoteException {
		return new File("files").list();
	}

	public int getFileSize(String fileName) throws RemoteException {
		FileInformationForm.appendText(" File Size : "
				+ (int) new File("files/" + fileName).length());
		return (int) new File("files/" + fileName).length();
	}

	public void addClient(String partDetail, String client)
			throws RemoteException {
		if (urlHash.containsKey(partDetail)) {
			ArrayList<String> urlData = urlHash.get(partDetail);
			urlData.add(client);
			urlHash.remove(partDetail);
			urlHash.put(partDetail, urlData);
		} else {
			ArrayList<String> urlData = new ArrayList<String>();
			urlData.add(client);
			urlHash.put(partDetail, urlData);
		}
	}

	public ArrayList<String> getServerList(String fileName)
			throws RemoteException {
		return urlHash.get(fileName);
	}

	public Hashtable<String, ArrayList<String>> urlHash = null;
}
