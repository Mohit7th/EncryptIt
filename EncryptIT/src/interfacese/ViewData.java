package interfacese;

/*
 *	this class provides the GUI interface to the user for viewing the data stored in the
 *	database that has been encrypted by the user
 *	date : 29/3/2015 - 15/5/2015
 */

//packages for GUI components and event handling interfaces
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ciphers.AffineCipher;
import ciphers.KeyTranCipher;
import ciphers.AutokeyCipher;
import backend.DbPrg;
import support.HandlingEvents;
import support.ShowWarning;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ViewData extends JFrame implements ActionListener {

	private JButton jbtnDec;
	private static final long serialVersionUID = 1L;
	// bottom buttons and comobobox holding panel
	private JPanel southPanel;
	// 5-3-2015
	HandlingEvents handler;
	static String UserName, password;
	ShowWarning warn;
	AutokeyCipher atk;

	ViewData() {
		// main encryption frame for encryption & decryption
		super("View Details");

		atk = new AutokeyCipher();
		warn = new ShowWarning();
		// 5-3-2015
		handler = new HandlingEvents(this, UserName, password);

		// setting main frame properties
		setLayout(new BorderLayout());
		setSize(640, 480);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// calling add Component method for placing components
		addComp();
		addToolBar();

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
		jbtnLock.setToolTipText("LogOut");
		jIconTool.add(jbtnLock);
		jbtnLock.setActionCommand("Lock");

		add(jIconTool, BorderLayout.NORTH);
		jbtnHome.addActionListener(handler);
		jbtnLock.addActionListener(handler);
		jbtnHelp.addActionListener(this);
	}

	// method to add different panel on to the Main Frame
	private void addComp() {
		southPanel = new JPanel();
		// calling methods for laying buttons and menu bar to the frame
		addMenuBar();
		addButtons();

		centerTempPan = new JPanel(new BorderLayout());
		JLabel jlabTopMsg = new JLabel("Records In Database");
		jlabTopMsg.setFont(new Font("Tahoma", Font.BOLD, 30));
		jlabTopMsg.setForeground(Color.blue);

		centerTempPan.add(jlabTopMsg, BorderLayout.NORTH);
		add(centerTempPan, BorderLayout.CENTER);

		DbPrg db = new DbPrg(UserName, password, "filedata1", 4);
		try {
			addTableToCenter(db.dataReturned());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("viewData (addComp) :" + e);
		}

	}

	JTable table;
	protected String setValue = null;
	static JPanel centerTempPan;

	private void addTableToCenter(Object data[][]) {
		String colHead[] = { "FileName", "Data" };
		// centerTempPan = new JPanel(new BorderLayout());
		table = new JTable(data, colHead);
		JScrollPane jspTable = new JScrollPane(table);
		jspTable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		centerTempPan.add(jspTable, BorderLayout.CENTER);
		// adding table to the main JFrame
		add(centerTempPan, BorderLayout.CENTER);

		// after adding new components repainting GUI
		revalidate();
		repaint();

		table.setCellSelectionEnabled(true);
		ListSelectionModel cellSelect = table.getSelectionModel();
		cellSelect.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// used to make table data selection possible, on user click the field
		// data will be stored
		// in a string that can be used for decryption
		cellSelect.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent lse) {
				// TODO Auto-generated method stub
				String selectedData = null;

				int[] selRow = table.getSelectedRows();
				int[] selCol = table.getSelectedColumns();

				for (int i = 0; i < selRow.length; i++) {
					for (int j = 0; j < selCol.length; j++) {
						selectedData = (String) table.getValueAt(selRow[i],
								selCol[j]);
					}
				}
				setValue = selectedData;
			}
		});
	}

	private void addButtons() {
		southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		// defining buttons for the south side of frame's BorderLayout
		jbtnDec = new JButton("Decrypt");
		// adding south side components to the sothPanel
		southPanel.add(jbtnDec);
		southPanel.setBackground(Color.blue);

		// adding bottom buttons Jpanel in the nottom
		add(southPanel, BorderLayout.SOUTH);

		// adding action listener for the south panel events
		jbtnDec.addActionListener(this);
	}

	private void addMenuBar() {
		// creating file menu and its items
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

		// adding menu items to menu
		jmFile.add(jmiExit);
		jmHelp.add(jmiHelp);
		jmHelp.add(jmiAbout);

		// adding file & help to menubar
		jmb.add(jmFile);
		jmb.add(jmHelp);

		setJMenuBar(jmb);

		// adding actionListener
		jmiExit.addActionListener(handler);
		jmiHelp.addActionListener(this);
		jmiAbout.addActionListener(handler);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String btnString = ae.getActionCommand();
		if (btnString.equals("Decrypt")) {
			if (setValue != null) {
				try {
					getKeyAndDecText(setValue);
					new ShowWarning(decText);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("viewData (actionPerformed) : " + e);
				}
			} else {
				warn.showWarnMsg(26);
			}
		} else if (btnString.equals("Help")) {
			String helpMsg = "View Data\ni) Click on the row where Data is present\n"
					+ "ii) Press Decrypt Button";
			new ShowWarning(helpMsg);
		}
	}

	private int key[] = new int[4];
	String decText = null;

	private void getKeyAndDecText(String fileText) throws IOException {
		// extracting encrypted key from the text file
		int index = fileText.lastIndexOf('$');
		String decKey = fileText.substring(index + 1, fileText.length());

		// decrypting the encrypted key with username length
		int keyTemp = UserName.length();
		decKey = atk.AutokeyMeth(decKey, keyTemp, 2);

		// checking wether user is the one who encrypted files or not
		int tindx = decKey.lastIndexOf("-");
		if (decKey.substring(tindx + 1, decKey.length()).equals(UserName)) {
			// now adding decrypted key to the text file
			fileText = fileText.substring(0, index) + "$" + decKey;

			String techType = decKey.substring(0, 1);

			// according to the extracted technique calling decryption
			// algorithm
			if (techType.equals("A")) {
				extractDecryptKey(fileText, 1, 3);
				fileText = new AffineCipher().affineMeth("None" + fileText,
						key, 2);
			} else if (techType.equals("T")) {
				extractDecryptKey(fileText, 2, 5);
				fileText = new KeyTranCipher().KeyTranMeth("None" + fileText,
						key, 2);
			} else {
				warn.showWarnMsg(6);
			}
			decText = fileText;
		} else {
			warn.showWarnMsg(12);
		}
	}

	private String extractDecryptKey(String fileText, int cipType, int noOfKeys) {
		// extracting the key from the text string
		String subKey = fileText.substring(fileText.lastIndexOf('$'),
				fileText.length());
		String whichCip = "", keyChar = "";
		int ct = 0;
		char tok = 'a';

		// collecting all keys
		for (int i = 0; ct < noOfKeys; ct++) {
			while (i < subKey.length()) {
				tok = subKey.charAt(i);
				if (tok != '$') {
					// Separator for two or more keys
					if (tok == '-') {
						i++;
						break;
					}
					keyChar += tok;
				}
				i++;
			}
			// extracting keys one by one and assigning it to keyvars
			switch (ct) {
			case 0:
				whichCip = keyChar;
				break;
			case 1:
				key[0] = Integer.parseInt(keyChar);
				break;
			case 2:
				key[1] = Integer.parseInt(keyChar);
				break;
			case 3:
				key[2] = Integer.parseInt(keyChar);
				break;
			case 4:
				key[3] = Integer.parseInt(keyChar);
				break;
			}
			keyChar = "";
			tok = 'a';
		}
		return whichCip;
	}

	public static void main(String[] args) {
		UserName = args[0];
		password = args[1];
		SwingUtilities.invokeLater(() -> {
			new ViewData().setVisible(true);
		});
	}

}
