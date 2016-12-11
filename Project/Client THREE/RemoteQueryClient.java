import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class RemoteQueryClient {
	public static FileDownloadInterface fileDownload;

	public RemoteQueryClient() {
		toInitialize();
	}

	private void toInitialize() {
		try {
			fileDownload = (FileDownloadInterface) Naming
					.lookup("rmi://127.0.0.1/ftpfile");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setFileContent(String fileName, byte fileContent[])
			throws RemoteException {

	}

	public static ArrayList<String> getServerList(String fileName)
			throws RemoteException {
		return fileDownload.getServerList(fileName);
	}

	public static byte[] getFileContent(String fileName, int start, int end,
			String url) throws RemoteException {
		try {
			fileDownload = (FileDownloadInterface) Naming.lookup(url);
			return fileDownload.getFileContent(fileName, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String[] getFileLists() throws RemoteException {
		return fileDownload.getFileLists();
	}

	public static int getFileSize(String fileName) {
		try {
			return fileDownload.getFileSize(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
