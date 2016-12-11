import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

public class FileDownloadServerMain extends JFrame {
	private static final long serialVersionUID = 2383816662581629708L;

	public FileDownloadServerMain() {
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
		setTitle("File Download Server Application");
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
		JMenuItem mtmExit, mtmStart;

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
		mnuMain.setMnemonic('M');

		mnuMain.add(mtmStart);
		mtmStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new FileDownloadImple();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
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
		new FileDownloadServerMain();
	}

	public static FileDownloadServerMain fileServerMain = null;
	public static JDesktopPane deskView = null;
	Dimension localSysDimension = null;

}
