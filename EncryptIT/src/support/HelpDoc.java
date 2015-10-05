package support;

/*
 *	This frame will provide help content for user to use the content
 *	better understand the functionality related to the software.
 *	date : 5/3/2015 (8:31 pm)
 */

import interfacese.HomeMenu;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class HelpDoc extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static String userName;
	private static String password;
	// components for the login frame
	JButton jbtnHome;
	HandlingEvents handler;

	HelpDoc() {
		super("Help Document");
		setLayout(new BorderLayout());
		setSize(640, 480);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		addComp();
		// handler = new HandlingEvents(this, userName, password);
	}

	// adding controls to the
	private void addComp() {
		JLabel jlabFrameName;
		// label for app name at the top of the frame
		JPanel jpanGroupComp = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jpanGroupComp.setBackground(Color.blue);
		jlabFrameName = new JLabel("How To use");
		jlabFrameName.setForeground(Color.white);
		jlabFrameName.setFont(new Font("Tahoma", Font.BOLD, 30));
		jpanGroupComp.add(jlabFrameName);
		add(jpanGroupComp, BorderLayout.NORTH);

		// adding buttons at the bottom of the frame
		jpanGroupComp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		jpanGroupComp.setBackground(Color.blue);
		jbtnHome = new JButton("Home");
		jpanGroupComp.add(jbtnHome);
		add(jpanGroupComp, BorderLayout.SOUTH);

		// setting event listener for the buttons
		jbtnHome.addActionListener(this);

		// adding JtextArea to display details related to the software
		JTextArea jtaHelpContent = new JTextArea();

		jtaHelpContent.setFont(new Font("Tahoma", Font.PLAIN, 20));
		jtaHelpContent.setLineWrap(true);
		jtaHelpContent.setMargin(new Insets(10, 10, 10, 10));
		// word will come to nextline if it doesnot fit at the end
		jtaHelpContent.setWrapStyleWord(true);
		jtaHelpContent.setTabSize(4);
		jtaHelpContent.setEditable(false);
		jtaHelpContent.setBackground(Color.cyan);

		JScrollPane jtaScroll = new JScrollPane(jtaHelpContent);
		jtaScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(jtaScroll, BorderLayout.CENTER);

		String helpTxt = "1. LogIn as root\ni) Go to Setting and create a new user.\n"
				+ "ii) After creating a new user click logout button on the toolbar.\niii) Now login as newly created user.\n\n"
				+ "*User Name should be greater than 4 letters and smaller than 15.\n*Password must be 15 characters long.\n"
				+ "*can only use unique name for new user."
				+ "\n\n2. Encrypt/Decrypt \n"
				+ "i) Select a Text File from MenuBar\nii) Set a mode (encryption/decryption) from menu\n"
				+ "iii) Select a encryption technique from combobox\niv) Set keys for the technique\n"
				+ "v) Press Encrypt button\nvi) (optional) click save to db button to save text to Database"
				+ "\n\n3. View Data\ni) Click on the row where Data is present\n"
				+ "ii) Press Decrypt Button\n\n4. Setting -> Add User\ni) Enter name and password and Press Ok"
				+"\n\n4. Setting -> Change Password\ni) Enter Old password and new Password\nii) Press ok to save it";
		jtaHelpContent.setText(helpTxt);
		jtaHelpContent.setCaretPosition(0);
	}

	// actionperformed method to handle button events
	public void actionPerformed(ActionEvent ae) {
		String btnString = ae.getActionCommand();
		if (btnString.equals("Ok")) {
			this.setVisible(false);
		} else if (btnString.equals("Home")) {
			setVisible(false);
			HomeMenu.main(new String[] { userName, password });
		}
	}

	public static void main(String[] args) {
		userName = args[0];
		password = args[1];
		SwingUtilities.invokeLater(() -> {
			new HelpDoc().setVisible(true);
		});
	}
}