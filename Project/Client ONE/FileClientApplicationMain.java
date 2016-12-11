import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.rmi.Naming;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

public class FileClientApplicationMain extends JFrame {
	private static final long serialVersionUID = 371252150052665721L;

	public FileClientApplicationMain() {
		toInitialize();
		setInitialize();
		deskView.add(new FileInformationForm());
		fileServerMain = null;
	}

	private void setScreenSize() {
		localSysDimension = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(localSysDimension.width, localSysDimension.height - 30);
	}

	private void setInitialize() {
		setTitle("File Download Client Application");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setContentPane(deskView);
		setJMenuBar(addMenuBar());
		setResizable(false);
		setScreenSize();
		setVisible(true);
	}

	public JMenuBar addMenuBar() {
		JMenuBar localsysMenuBar = new JMenuBar();
		JMenu mnuFile, mnuMain;
		JMenuItem mtmExit, mtmStart, mtmDownload;

		mnuFile = new JMenu("File");
		mtmExit = new JMenuItem("Exit");
		mnuFile.setMnemonic('F');
		mnuFile.add(mtmExit);

		mtmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnuMain = new JMenu("Main Menu");
		mtmStart = new JMenuItem("Start Server");
		mtmDownload = new JMenuItem("Download File");
		mnuMain.setMnemonic('M');

		mnuMain.add(mtmStart);
		mnuMain.add(mtmDownload);
		mtmStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					FileDownloadInterface fileDownloadInterface = (FileDownloadInterface) Naming
							.lookup("rmi://127.0.0.1/ftpfile");
					new FileDownloadImple();
					File[] fileList = new File("files").listFiles();
					for (int i = 0; i < fileList.length; i++) {
						fileDownloadInterface.addClient(fileList[i].getName(),
								urlString);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		mtmDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deskView.add(new FileDownloadForm());
			}
		});

		localsysMenuBar.add(mnuFile);
		localsysMenuBar.add(mnuMain);

		return localsysMenuBar;
	}

	private void toInitialize() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		deskView = new JDesktopPane();
		deskView.setBackground(new Color(237, 237, 237));

	}

	public static void main(String[] args) {
		new FileClientApplicationMain();
	}

	public static String urlString = "";
	public static FileClientApplicationMain fileServerMain = null;
	public static JDesktopPane deskView = null;
	Dimension localSysDimension = null;

}
