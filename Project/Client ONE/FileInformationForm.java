import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public class FileInformationForm extends JInternalFrame {
	private static final long serialVersionUID = -504967291353029920L;

	public FileInformationForm() {
		toInitialize();
		setInitialize();

	}

	private void setScreenSize() {
		localSysDimension = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, localSysDimension.height - 280,
				localSysDimension.width - 24, 350);
	}

	private void setInitialize() {
		((BasicInternalFrameUI) this.getUI()).setNorthPane(null);
		setScreenSize();
		setBorder(null);
		setVisible(true);
	}

	public static void appendText(String strMsg) {
		txtMonitor.append("\r\n" + strMsg);
	}

	private void toInitialize() {
		localSysDimension = Toolkit.getDefaultToolkit().getScreenSize();
		txtMonitor = new JTextArea();
		txtMonitor.setAutoscrolls(true);
		scrollMonitor = new JScrollPane(txtMonitor);
		scrollMonitor.setBackground(new Color(237, 237, 237));

		getContentPane().setBackground(new Color(237, 237, 237));
		getContentPane().setLayout(null);
		getContentPane().add(scrollMonitor);

		txtMonitor.setBackground(Color.black);
		txtMonitor.setForeground(Color.green);
		TitledBorder border = new TitledBorder("Monitor Information");
		border.setTitleColor(Color.black);

		scrollMonitor.setBorder(new CompoundBorder(border, new EtchedBorder()));
		scrollMonitor.setBounds(18, 10, localSysDimension.width - 50, 180);
	}

	public static JTextArea txtMonitor;

	JScrollPane scrollMonitor;

	public static Dimension localSysDimension = null;
}
