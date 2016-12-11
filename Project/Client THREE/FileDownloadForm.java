import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

public class FileDownloadForm extends JInternalFrame {
	private static final long serialVersionUID = -7021892162328263883L;
	int i = 0;
	int start = 0;
	int end = 0;
	int average = 0;

	public FileDownloadForm() {
		toInitialize();
		setInitialize();
	}

	private void setScreenSize() {
		localSysDimension = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((localSysDimension.width - 500) / 2, 20, 500, 400);
	}

	private void setInitialize() {
		setTitle("File List Provider Detail");
		loadFiles();

		setScreenSize();
		setVisible(true);
	}

	private void loadFiles() {
		try {
			new RemoteQueryClient();
			String fileLists[] = RemoteQueryClient.getFileLists();
			for (int i = 0; i < fileLists.length; i++) {
				modelView.addElement(fileLists[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getServerList(String fileName) {
		try {
			fileList = new ArrayList<String>();
			new RemoteQueryClient();
			fileList = RemoteQueryClient.getServerList(fileName);
			fileList.remove(FileClientApplicationMain.urlString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void toInitialize() {
		fileUrl = new Hashtable<Integer, Integer>();
		JPanel panelView = new JPanel();
		panelView.setBorder(new TitledBorder("File List Option"));
		panelView.setLayout(null);

		modelView = new DefaultListModel();
		listFile = new JList(modelView);
		scrollList = new JScrollPane(listFile);

		btnDownload = new JButton("Download");
		btnClose = new JButton("Close");

		panelView.add(scrollList);

		panelView.add(btnDownload);
		panelView.add(btnClose);

		btnDownload.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				i = 0;
				final String fileName = listFile.getSelectedValue().toString();
				getServerList(fileName);
				int total = RemoteQueryClient.getFileSize(fileName);
				average = total / fileList.size();
				start = 0;
				end = average;
				for (i = 0; i < fileList.size(); i++) {
					FileInformationForm.appendText("Part Name : " + fileName
							+ ".part" + i);
					try {
						FileOutputStream output = new FileOutputStream("data/"
								+ fileName + ".part" + i);
						FileInformationForm.appendText("Url : "
								+ fileList.get(i).trim());
						byte[] data = RemoteQueryClient.getFileContent(
								fileName, start, end, fileList.get(i).trim());
						fileUrl.put(i, start);
						output.write(data);
						output.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					start = end;
					if (total >= end + average + average) {
						end += average;
					} else {
						end = total;
					}

				}
				try {
					File[] files = new File("data").listFiles();
					FileOutputStream output = new FileOutputStream("files/"
							+ fileName);
					for (int i = 0; i < files.length; i++) {
						FileInputStream input = new FileInputStream(files[i]);
						byte[] dataByte = new byte[input.available()];
						if (input.available() >= average) {
							input.read(dataByte);
							input.close();
							output.write(dataByte);
							files[i].delete();
						} else {
							byte[] data = RemoteQueryClient.getFileContent(
									fileName, fileUrl.get(i), end, fileList
											.get(i).trim());
							output.write(data);
						}

					}
					output.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});

		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);

			}
		});

		scrollList.setBounds(20, 30, 300, 300);

		btnDownload.setBounds(340, 100, 100, 20);
		btnClose.setBounds(340, 140, 100, 20);

		getContentPane().setLayout(null);
		getContentPane().add(panelView);
		panelView.setBounds(10, 10, 470, 445);
	}

	JList listFile;
	JScrollPane scrollList;
	DefaultListModel modelView = null;
	ArrayList<String> fileList = null;
	JButton btnDownload, btnClose;
	Dimension localSysDimension = null;
	Hashtable<Integer, Integer> fileUrl = null;
}
