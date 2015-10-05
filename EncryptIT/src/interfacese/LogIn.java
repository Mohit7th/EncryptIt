package interfacese;

/*	@author - Mohit Uniyal
 *	Login frame which will provide access to the encryption application
 *	date : 5/3/2015 - 15/5/2015 (8:29pm) completed 
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import backend.*;
import support.*;

public class LogIn extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	// components for the login frame
	JLabel jlabAppName;
	JTextField jtxtUserName, jtxtPassword;
	JButton jbtnLogin;

	public LogIn() {
		super("LogIn");
		setLayout(new BorderLayout());
		setSize(320, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// adding buttons and other controls to the frame
		addComp();
	}

	// adding controls to the
	private void addComp() {
		// label for app name at the top of the frame
		JPanel jpanGroupComp = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jpanGroupComp.setBackground(Color.blue);
		jlabAppName = new JLabel("EncryptIT");
		jlabAppName.setForeground(Color.white);
		jlabAppName.setFont(new Font("Tahoma", Font.BOLD, 30));
		jpanGroupComp.add(jlabAppName);
		add(jpanGroupComp, BorderLayout.NORTH);

		// adding buttons at the bottom of the frame
		jpanGroupComp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		jpanGroupComp.setBackground(Color.blue);
		jbtnLogin = new JButton("LogIn");

		JLabel loginMsg = new JLabel("Login Using root To Create a New User");
		loginMsg.setForeground(Color.white);
		jpanGroupComp.add(loginMsg);
		jpanGroupComp.add(jbtnLogin);
		add(jpanGroupComp, BorderLayout.SOUTH);

		// adding center components to the frame
		JPanel jpanOuter = new JPanel(new GridLayout(2, 1));
		jpanGroupComp = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jtxtUserName = new JTextField(15);
		jtxtUserName.setText("root");
		jlabAppName = new JLabel("UserName : ");
		jlabAppName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jpanGroupComp.add(jlabAppName);
		jpanGroupComp.add(jtxtUserName);
		jpanOuter.add(jpanGroupComp);

		// adding password JLabel and password text field
		jpanGroupComp = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jtxtPassword = new JPasswordField(15);
		jtxtPassword.setText("root");
		jlabAppName = new JLabel("Password : ");
		jlabAppName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jpanGroupComp.add(jlabAppName);
		jpanGroupComp.add(jtxtPassword);
		jpanOuter.add(jpanGroupComp);

		add(jpanOuter, BorderLayout.CENTER);

		// setting event listener for the buttons
		jbtnLogin.addActionListener(this);
	}

	// method to handle button events
	public void actionPerformed(ActionEvent ae) {

		String btnString = ae.getActionCommand();

		// checking for user details in the database
		if (btnString.equals("LogIn")) {
			String name = jtxtUserName.getText();
			String pass = jtxtPassword.getText();
			boolean success = false;
			ShowWarning warn;

			// checking if user left username and password field empty
			if (name.length() != 0 && pass.length() != 0) {
				DbPrg db = new DbPrg(name, pass, "users", 1);
				try {
					success = db.dbConnect();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("settings (actionPerformed) : " + e);
				}
				// on correct login details calling MainMenu
				if (success == true) {
					String details[] = { name, pass };
					setVisible(false);
					HomeMenu.main(details);
				} else {
					// waning message if user enters wrong user credentials
					warn = new ShowWarning();
					warn.showWarnMsg(10);
				}
			} else {
				// warning message when user clicks login without entering any
				// details in the text boxes
				warn = new ShowWarning();
				warn.showWarnMsg(11);
			}
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new LogIn().setVisible(true);
		});
	}
}
