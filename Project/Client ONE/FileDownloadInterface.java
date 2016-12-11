import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface FileDownloadInterface extends Remote {
	public void setFileContent(String fileName, byte fileContent[])
			throws RemoteException;

	public byte[] getFileContent(String fileName, int start, int end)
			throws RemoteException;

	public String[] getFileLists() throws RemoteException;

	public int getFileSize(String fileName) throws RemoteException;

	public void addClient(String partDetail, String url) throws RemoteException;

	public ArrayList<String> getServerList(String fileName)
			throws RemoteException;
}
