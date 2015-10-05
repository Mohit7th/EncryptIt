package interfacese;

/*
 *	setting frame will provide functionality to the user to change password and add a new user
 *	Date : 26-04-2015 - 14/5/2015
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import backend.DbPrg;
import support.HandlingEvents;
import support.ShowWarning;

public class Settings extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JPanel jpanOuter;
	JTextField jtxtName;
	JPasswordField jtxtPass;

	JTextField jtxtOldPass;
	JPasswordField jtxtNewPass;

	// 5-3-2015
	HandlingEvents handler;
	static String UserName, password;
	ShowWarning warn;
	// index that will handle the tabbed pane
	static int paneIndex = 0;

	Settings() {
		// setting form properties
		super("Settings");
		warn = new ShowWarning();
		// 5-3-2015
		handler = new HandlingEvents(this, UserName, password);

		setSize(640, 480);
		setResizable(false);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// adding menu bar and the other components to frame
		createMenu();
		createTabbedPane();
		addToolBar();
	}

	private void createTabbedPane() {
		// adding outer panel to create component center oriented
		jpanOuter = new JPanel(new GridBagLayout());
		// internal panel will help to lay components row by row
		JPanel jpanInner = new JPanel(new GridLayout(4, 1, 1, 1));

		// panel that will work as container for two components
		JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel jlabApp = new JLabel("Add New User");
		jlabApp.setFont(new Font("Tahoma", Font.BOLD, 30));
		jlabApp.setForeground(Color.blue);
		row.add(jlabApp);
		jpanInner.add(row);

		row = new JPanel(new FlowLayout(FlowLayout.CENTER));
		row.add(new JLabel("UserName "));
		jtxtName = new JTextField(15);
		row.add(jtxtName);
		jpanInner.add(row);

		// panel that will work as container for two components(label & new
		// password)
		row = new JPanel(new FlowLayout(FlowLayout.CENTER));
		row.add(new JLabel("Password"));
		jtxtPass = new JPasswordField(15);
		row.add(jtxtPass);
		jpanInner.add(row);

		// creating blue color bottom for the frame
		JPanel jtemp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		jtemp.setBackground(Color.blue);
		JButton jbtnOk = new JButton("Ok");
		jtemp.add(jbtnOk);
		jtemp.add(new JButton("Cancel"));
		add(jtemp, BorderLayout.SOUTH);

		// adding inner panel to the outer gridlayout panel
		jpanOuter.add(jpanInner);

		// using tabbed pane to create two page like setting in same form
		JTabbedPane jtp = new JTabbedPane();
		jtp.addTab("Add User", jpanOuter);

		// adding outer panel to create component center oriented
		jpanOuter = new JPanel(new GridBagLayout());
		// internal panel will help to lay components row by row
		jpanInner = new JPanel(new GridLayout(4, 1, 1, 1));

		// panel that will work as container for two components
		row = new JPanel(new FlowLayout(FlowLayout.CENTER));
		jlabApp = new JLabel("Change Password");
		jlabApp.setFont(new Font("Tahoma", Font.BOLD, 30));
		jlabApp.setForeground(Color.blue);
		row.add(jlabApp);
		jpanInner.add(row);

		row = new JPanel(new FlowLayout(FlowLayout.CENTER));
		row.add(new JLabel("Old Password "));
		jtxtOldPass = new JTextField(15);
		row.add(jtxtOldPass);
		jpanInner.add(row);

		// panel that will work as container for two components(label & new
		// password)
		row = new JPanel(new FlowLayout(FlowLayout.CENTER));
		row.add(new JLabel("New Password"));
		jtxtNewPass = new JPasswordField(15);
		row.add(jtxtNewPass);
		jpanInner.add(row);

		jpanOuter.add(jpanInner);

		//adding second Panel to the tabbedPane
		jtp.addTab("Change Password", jpanOuter);
		add(jtp, BorderLayout.CENTER);

		jbtnOk.addActionListener(this);

		// adding jtabbed pane change events
		ChangeListener chListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				JTabbedPane tab = (JTabbedPane) ce.getSource();
				paneIndex = tab.getSelectedIndex();
			}
		};

		jtp.addChangeListener(chListener);
		// disabling change password tab in the setting frame
		if (UserName.equals("root")) {
			jtp.setEnabledAt(1, false);
		}
	}

	// creating menu bar for the form
	private void createMenu() {
		JMenuBar jmb = new JMenuBar();
		JMenu jmFile = new JMenu("File");
		JMenuItem jmiExit = new JMenuItem("Exit");

		// setting colors for the Menubar items and Menu
		jmb.setBackground(Color.blue);
		jmFile.setForeground(Color.white);
		jmiExit.setBackground(Color.blue);
		jmiExit.setForeground(Color.white);

		// creating help menu
		JMenu jmHelp = new JMenu("Help");
		JMenuItem jmiHelp = new JMenuItem("Help");
		JMenuItem jmiAbout = new JMenuItem("About Encrypt It");

		// setting color for menu item
		jmHelp.setForeground(Color.white);
		jmiHelp.setBackground(Color.blue);
		jmiHelp.setForeground(Color.white);
		jmiAbout.setBackground(Color.blue);
		jmiAbout.setForeground(Color.white);

		jmFile.add(jmiExit);
		jmHelp.add(jmiHelp);
		jmHelp.add(jmiAbout);

		// adding menu to the menu and to the main frame

		jmb.add(jmFile);
		jmb.add(jmHelp);
		setJMenuBar(jmb);

		// adding action listener to the menu items
		jmiExit.addActionListener(handler);
		jmiHelp.addActionListener(this);
		jmiAbout.addActionListener(handler);
	}

	private void addToolBar() {
		JToolBar jIconTool = new JToolBar("Tools");

		// home menu
		JButton jbtnHome = new JButton(new ImageIcon("./Icons/home.png"));
		jbtnHome.setToolTipText("Home");
		jIconTool.add(jbtnHome);
		jbtnHome.setActionCommand("Home");

		// User name
		JButton jbtnUser = new JButton(new ImageIcon("./Icons/User.png"));
		jbtnUser.setToolTipText("Logged In User : " + UserName.toUpperCase());
		jIconTool.add(jbtnUser);
		jbtnUser.setActionCommand("user");

		// help menu
		JButton jbtnHelp = new JButton(new ImageIcon("./Icons/help.png"));
		jbtnHelp.setToolTipText("Help");
		jIconTool.add(jbtnHelp);
		jbtnHelp.setActionCommand("Help");

		// lock tool
		JButton jbtnLock = new JButton(new ImageIcon("./Icons/lock.png"));
		jbtnLock.setToolTipText("Lock");
		jIconTool.add(jbtnLock);
		jbtnLock.setActionCommand("Lock");

		add(jIconTool, BorderLayout.NORTH);
		jbtnHome.addActionListener(handler);
		jbtnLock.addActionListener(handler);
		jbtnHelp.addActionListener(this);
	}

	public void actionPerformed(ActionEvent ae) {
		String btnString = ae.getActionCommand();
		if (btnString.equals("Ok")) {
			if (paneIndex == 0) {
				// adding a new user and password in the database table users
				String uname = jtxtName.getText();
				String passwd = new String(jtxtPass.getPassword());

				int passLen = passwd.length();
				int nameLen = uname.length();
				if (!(UserName.equals(uname) && password.equals(passwd))) {
					if (passLen != 0 && nameLen != 0) {
						// checking weather username and password values are
						// valid
						// or not
						if (passLen == 15 && nameLen > 4) {
							DbPrg db = new DbPrg(uname, passwd, "users", 2);
							try {
								// on successfully saving name and password
								if (db.addNewUser()) {
									warn.showWarnMsg(16);
								} else {
									// enter else part if duplicate username is
									// entered the database
									warn.showWarnMsg(17);
								}
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								System.out
										.println("Setting (actionPerformed) : "
												+ e);
							}
						} else {
							warn.showWarnMsg(15);
						}
					} else {
						warn.showWarnMsg(11);
					}
				} else {
					warn.showWarnMsg(17); // warning current user creating new
											// user with his name
				}

			} else {
				// calling password updation method of DbPrg.class
				String pass = new String(jtxtNewPass.getPassword());
				String name = UserName;
				if (pass.length() != 0 && name.length() != 0) {
					if (pass.length() == 15) {
						// checking weather old password is valid or not
						if (password.equals(jtxtOldPass.getText())) {
							// checking new password is differnet from the old
							// password
							if (!(jtxtOldPass.getText().equals(pass))) {
								DbPrg db = new DbPrg(name, pass, "users", 3);

								try {
									if (db.changeMyPassword()) {
										warn.showWarnMsg(19);
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									System.out
											.println("setting (actionPerformed) : "
													+ e);
								}
							} else {
								warn.showWarnMsg(18);
							}
						} else {
							warn.showWarnMsg(21);
						}
					} else {
						warn.showWarnMsg(24);
					}
				} else {
					warn.showWarnMsg(27);
				}
			}
			// when help button is clicked from the menu or from the toolbar
		} else if (btnString.equals("Help")) {
			String helpMsg = "Setting\nAdd User\ni) Enter name and password and Press Ok"
					+ "\n\nChange Password\ni) Enter Old password and new Password\nii) Press ok to save it";
			new ShowWarning(helpMsg);
		}
	}

	public static void main(String[] args) {
		UserName = args[0];
		password = args[1];
		SwingUtilities.invokeLater(() -> {
			new Settings().setVisible(true);
		});
	}
}
