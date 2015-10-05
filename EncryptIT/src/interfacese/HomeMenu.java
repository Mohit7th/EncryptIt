package interfacese;

/*
 *	Main Menu file to provide main frame for selecting different functions 
 *	of the application as a choice to user (saveToDb button is actully used to call the HelpDoc class)
 *	date : 15/5/2015
 */

import java.awt.*;
import support.HelpDoc;
import java.awt.event.*;
import javax.swing.*;

public class HomeMenu extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton jbtnEnAndDec;
	private static String UserName, password;

	HomeMenu() {
		super("Main Menu" + "  [" + UserName.toUpperCase() + "]");
		setLayout(new BorderLayout());
		createView();
		// adding jpanel to the frame and setting its properties
		setResizable(false);
		setSize(640, 480);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private void createView() {
		// adding panel to main Frame
		JPanel panMain = new JPanel(new GridLayout(2, 2, 10, 10));
		getContentPane().add(panMain);

		// adding button with constraints
		jbtnEnAndDec = new JButton("<html>"
				+ "Encrypt /\nDecrypt".replaceAll("\\n", "<br>") + "</html>");
		jbtnEnAndDec.setActionCommand("Encrypt");
		jbtnEnAndDec.setFont(new Font("Tahoma", Font.BOLD, 35));
		jbtnEnAndDec.setForeground(Color.white);
		jbtnEnAndDec.setBackground(Color.blue);
		panMain.add(jbtnEnAndDec);

		// adding another button with constraints
		JButton jbtnViewData = new JButton("View Data");
		jbtnViewData.setActionCommand("View Data");
		jbtnViewData.setFont(new Font("Tahoma", Font.BOLD, 35));
		jbtnViewData.setForeground(Color.white);
		jbtnViewData.setBackground(Color.blue);
		panMain.add(jbtnViewData);

		// adding another button with constraints
		JButton jbtnSaveToDb = new JButton("Help?");
		jbtnSaveToDb.setActionCommand("Save to DB");
		jbtnSaveToDb.setFont(new Font("Tahoma", Font.BOLD, 35));
		jbtnSaveToDb.setForeground(Color.white);
		jbtnSaveToDb.setBackground(Color.blue);
		panMain.add(jbtnSaveToDb);

		// adding another button with constraints
		JButton jbtnSettings = new JButton("Settings");
		jbtnSettings.setFont(new Font("Tahoma", Font.BOLD, 35));
		jbtnSettings.setForeground(Color.white);
		jbtnSettings.setBackground(Color.blue);
		panMain.add(jbtnSettings);

		add(panMain, BorderLayout.CENTER);
		add(new JLabel(" "), BorderLayout.NORTH);
		add(new JLabel("     "), BorderLayout.EAST);
		add(new JLabel("     "), BorderLayout.WEST);
		add(new JLabel(" "), BorderLayout.SOUTH);

		jbtnEnAndDec.addActionListener(this);
		jbtnViewData.addActionListener(this);
		jbtnSaveToDb.addActionListener(this);
		jbtnSettings.addActionListener(this);

		// when root login disabling the encrypt/decrypt and viewdata frames
		if ("root".equals(UserName)) {
			jbtnEnAndDec.setEnabled(false);
			jbtnViewData.setEnabled(false);
		}

	}

	public void actionPerformed(ActionEvent ae) {
		String menuStr = ae.getActionCommand();
		String args[] = { UserName, password };

		// displaying form on user click and closing current frame
		switch (menuStr) {
		case "Encrypt":
			EncryptTxt.main(args);
			break;
		case "View Data":
			ViewData.main(args);
			break;
		case "Save to DB":
			HelpDoc.main(args);
			break;
		case "Settings":
			Settings.main(args);
			break;
		}
		setVisible(false);
	}

	public static void main(String[] args) {
		UserName = args[0];
		password = args[1];
		SwingUtilities.invokeLater(() -> {
			new HomeMenu().setVisible(true);
		});
	}
}