package interfacese;

/*
 * @author - Mohit Uniyal
 *	this class provides the GUI interface to the user for selecting a file
 *	and encrypting it also provide user facilties to view and change the content
 *	17-4-2015 -15-5-2015
 */

//packages for GUI components and event handling interfaces
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import backend.DbPrg;
import readwrite.FileOperReadWrite;
import support.HandlingEvents;
import support.ShowWarning;
import ciphers.AffineCipher;
import ciphers.AutokeyCipher;
import ciphers.KeyTranCipher;

public class EncryptTxt extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private static boolean set_key_status = false;
	// menu bar buttons
	JRadioButtonMenuItem jmiEnc, jmiDec;
	// bottom buttons and comobobox holding panel
	private JPanel southPanel;
	// components for MainFrame ie inside constructor
	private String encCipher;
	private JComboBox<String> jcb;
	public JTextArea jta;
	// file chooser for saving and opening dialog box
	private JFileChooser jFileChs;

	public volatile String filename = "";
	// components key frame window
	private JFrame keyFrm;
	JButton jbtnClick, jbtnCancel;
	JPasswordField jpf;
	// multiplicative inverse key values for the set of 34 characters
	private Integer keys[] = { 1, 3, 5, 7, 9, 13, 15, 17, 19, 21, 23, 25, 27,
			29, 31, 35, 37, 39, 41, 43, 45, 47, 49, 51, 53, 57, 59, 61, 63, 65,
			67, 69, 71, 73, 75, 79, 81, 83, 85, 87 };
	private JComboBox<Integer> k0, k1, tk0, tk1, tk2, tk3;
	// selected keys will be stored in this array
	private int key[] = new int[4];
	private JToggleButton jbtnKey;
	private JButton jbtnDec, jbtnEnc;
	private ShowWarning warn;

	// 5-3-2015, this class will handle common events from all frames
	HandlingEvents handler;
	// 11-5-2015, logged in user details will be stored in it
	static String UserName, password;

	EncryptTxt() {
		// main encryption frame for encryption & decryption
		super("Encrypt & Decryption");
		warn = new ShowWarning(this);
		encCipher = new String();

		// 5-3-2015
		handler = new HandlingEvents(this, UserName, password);
		// setting main frame properties
		setLayout(new BorderLayout());
		setSize(640, 480);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jFileChs = new JFileChooser();
		// calling add Component method for placing components
		addComp();
	}

	// method to add different panel on to the Main Frame
	private void addComp() {
		southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		// calling methods for laying buttons and menu bar to the frame
		addMenuBar();
		addButtons();
		addToolBar();

		// textArea for center of the frame
		jta = new JTextArea();
		// setting properties of text area
		jta.setFont(new Font("Tahoma", Font.BOLD, 20));
		jta.setLineWrap(true);
		jta.setMargin(new Insets(10, 10, 10, 10));
		// word will come to nextline if it doesnot fit at the end
		jta.setWrapStyleWord(true);
		jta.setTabSize(4);

		// making JtextArea scrollable
		JScrollPane jsptextPad = new JScrollPane(jta);
		jsptextPad
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// adding textArea to the main JFrame
		add(jsptextPad, BorderLayout.CENTER);
		// 29-4-2015 adding textarea default font color green
		jta.setForeground(Color.green);
	}

	// creating left side toollbar to the menu
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
		
		// save to database
		JButton jbtnDb = new JButton(new ImageIcon("./Icons/downloads.png"));
		jbtnDb.setToolTipText("Save to Database");
		jIconTool.add(jbtnDb);
		jbtnDb.setActionCommand("database");
		
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
		jbtnUser.addActionListener(handler);
		jbtnHelp.addActionListener(this);
		jbtnDb.addActionListener(this);
	}

	// adding menu bar that provide functionality to save, open, exit etc...
	private void addMenuBar() {
		// creating file menu and its items
		JMenuBar jmb = new JMenuBar();
		JMenu jmFile = new JMenu("File");
		JMenuItem jmiNew = new JMenuItem("New", KeyEvent.VK_N);
		JMenuItem jmiOpen = new JMenuItem("Open", KeyEvent.VK_O);
		JMenuItem jmiSave = new JMenuItem("Save", KeyEvent.VK_S);
		JMenuItem jmiExit = new JMenuItem("Exit", KeyEvent.VK_E);

		// accelrator for mouse shortcuts
		jmiNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				InputEvent.CTRL_DOWN_MASK));
		jmiOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				InputEvent.CTRL_DOWN_MASK));
		jmiSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_DOWN_MASK));
		jmiExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				InputEvent.CTRL_DOWN_MASK));

		// setting colors for the Menubar items and Menu
		jmb.setBackground(Color.blue);
		jmFile.setForeground(Color.white);
		jmiOpen.setBackground(Color.blue);
		jmiOpen.setForeground(Color.white);
		jmiExit.setBackground(Color.blue);
		jmiExit.setForeground(Color.white);
		jmiNew.setBackground(Color.blue);
		jmiNew.setForeground(Color.white);
		jmiSave.setBackground(Color.blue);
		jmiSave.setForeground(Color.white);

		// 17-4-2015 creating the Mode Menu
		JMenu jmMode = new JMenu("Mode");
		jmiEnc = new JRadioButtonMenuItem("Encryption", true);
		jmiDec = new JRadioButtonMenuItem("Decryption");

		jmMode.setForeground(Color.white);
		jmiEnc.setBackground(Color.blue);
		jmiDec.setBackground(Color.blue);
		jmiEnc.setForeground(Color.white);
		jmiDec.setForeground(Color.white);

		// adding jradiomenuitem to the group
		ButtonGroup menuRadMenuItGroup = new ButtonGroup();
		menuRadMenuItGroup.add(jmiEnc);
		menuRadMenuItGroup.add(jmiDec);

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
		jmFile.add(jmiNew);
		jmFile.add(jmiOpen);
		jmFile.add(jmiSave);
		jmFile.add(jmiExit);
		jmHelp.add(jmiHelp);
		jmHelp.add(jmiAbout);

		jmMode.add(jmiEnc);
		jmMode.add(jmiDec);

		// adding file & help to menubar
		jmb.add(jmFile);
		jmb.add(jmMode);
		jmb.add(jmHelp);

		// adding menubar to the main layout of the JFrame
		setJMenuBar(jmb);

		// adding actionListener
		jmiNew.addActionListener(this);
		jmiOpen.addActionListener(this);
		jmiSave.addActionListener(this);
		jmiExit.addActionListener(handler);
		jmiHelp.addActionListener(this);
		jmiAbout.addActionListener(handler);
		jmiEnc.addActionListener(this);
		jmiDec.addActionListener(this);
	}

	private void addButtons() {
		// creating combobox for selecting encryption technique
		jcb = new JComboBox<String>();
		jcb.addItem("------Select-----");
		jcb.addItem("Affine");
		jcb.addItem("Transposition");

		// defining buttons for the south side of frame's BorderLayout
		jbtnEnc = new JButton("Encrypt");
		jbtnDec = new JButton("Decrypt");
		jbtnKey = new JToggleButton("Set Key");

		// adding tooltip text
		jbtnEnc.setToolTipText("Click to Encrypt selected Text");
		jbtnDec.setToolTipText("Click to Decrypt text");
		jbtnKey.setToolTipText("Set a key for Encryption");

		// adding south side components to the sothPanel
		southPanel.add(jcb);
		southPanel.add(jbtnKey);
		southPanel.add(jbtnEnc);
		southPanel.add(jbtnDec);
		southPanel.setBackground(Color.blue);

		// adding bottom buttons Jpanel in the nottom
		add(southPanel, BorderLayout.SOUTH);

		// adding action listener for the south panel events
		jbtnDec.addActionListener(this);
		jbtnEnc.addActionListener(this);
		jbtnKey.addActionListener(this);

		// setting default mode to encrypt by disabling decrypt button
		jbtnDec.setEnabled(false);
	}

	// method to create new form for entering key values
	private void newKeyForm(String cipher) {
		// creating border layout and setting its properties
		keyFrm = new JFrame("Enter Key");
		keyFrm.setLayout(new GridLayout(2, 1));
		keyFrm.setSize(290, 125);
		keyFrm.setLocationRelativeTo(null);

		// button and password field
		jbtnClick = new JButton("Click");
		jbtnClick.setFont(new Font("Tahoma", Font.BOLD, 15));
		jbtnCancel = new JButton("Cancel");
		jbtnCancel.setFont(new Font("Tahoma", Font.BOLD, 15));

		// creating pannel for two or one key
		JPanel keyPan = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel lab = new JLabel("Select Keys : ");

		lab.setFont(new Font("Tahoma", Font.BOLD, 15));
		keyPan.add(lab);

		// creating pannel for two buttons
		JPanel jpanbtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		jpanbtn.add(jbtnClick);
		jpanbtn.add(jbtnCancel);
		jpanbtn.setBackground(Color.blue);

		if (cipher.equals("Affine")) {
			keyFrm.setVisible(true);
			keyPan.setLayout(new FlowLayout(FlowLayout.CENTER));
			// below are two keys for affine cipher
			k0 = new JComboBox<Integer>();
			for (int i = 1; i < 89; i++)
				k0.addItem(i);
			// creating jcombobox to hold multiplicative inverse key domain
			k1 = new JComboBox<Integer>(keys);
			keyPan.add(k0);
			keyPan.add(k1);
		} else if (cipher.equals("Transposition")) {
			keyFrm.setVisible(true);
			// below four keys for Transpositon cipher
			Integer k[] = { 0, 1, 2, 3 };
			tk0 = new JComboBox<Integer>(k);
			tk1 = new JComboBox<Integer>(k);
			tk2 = new JComboBox<Integer>(k);
			tk3 = new JComboBox<Integer>(k);

			// adding JComboBox's to JPanel
			keyPan.add(tk0);
			keyPan.add(tk1);
			keyPan.add(tk2);
			keyPan.add(tk3);
		} else {
			// if user clicks any other button before selecting any technique
			// (cipher)
			jbtnKey.setSelected(false);
			warn.showWarnMsg(0);
		}

		// adding fields to the new frame
		keyFrm.add(keyPan);
		keyFrm.add(jpanbtn);

		// adding action listener for the button which will be pressed after
		// entering key
		jbtnClick.addActionListener(this);
		jbtnCancel.addActionListener(this);
	}

	// method to handle the event generated at button clicks or menu item
	// selection
	@SuppressWarnings("null")
	public void actionPerformed(ActionEvent ae) {

		int dialRetVal = 10;
		File f = null;
		// getting the encryption cipher technique name
		encCipher = (String) jcb.getSelectedItem();

		// getting the button name or string
		String btnPressStr = ae.getActionCommand();
		// calculating new length of the textArea
		int oldTxtLen = fileText.length();
		int newTxtLen = jta.getText().length();
		FileOperReadWrite forw = new FileOperReadWrite();

		switch (btnPressStr) {
		case "New":
			// dialog box to create new file
			dialRetVal = jFileChs.showSaveDialog(this);
			jbtnKey.setSelected(false);
			// if user clicked ok button
			if (dialRetVal == 0) {
				// checking whether code has changed by user or not by comparing
				// old length and new length
				if ((oldTxtLen == newTxtLen) || oldTxtLen == 0) {
					jta.setText(" ");

					if (filename.equals("")) {
						// current filename and new file should have different
						// name
						if (!f.getName().equals(filename)) {
							f = jFileChs.getSelectedFile();
						} else {
							warn.showWarnMsg(7);
						}
						filename = f.getPath();
					}
				} else {
					f = jFileChs.getSelectedFile();
					String pthParent = f.getParent();
					File t = new File(pthParent);
					String s[] = t.list();
					// cheking for sanem file names
					for (String st : s) {
						if (st.equals(filename)) {
							warn.showWarnMsg(7);
							break;
						}
					}
					forw.writeDataToFile(filename, fileText);
					jta.setText(" ");
					oldTxtLen = 0;
				}
			}
			break;
		case "Open":
			jbtnKey.setSelected(false);
			dialRetVal = jFileChs.showOpenDialog(this);
			if (dialRetVal == 0) {
				f = jFileChs.getSelectedFile();
				filename = f.getPath();
				originalFile = f.getName();
				// calling readFileData to read content from file & display it
				// to text area
				String str = forw.readFileData(filename);
				// so that textarea will not display the key as the text
				if (str.lastIndexOf('$') != -1)
					str = str.substring(0, str.lastIndexOf('$'));
				jta.setText(str);
				oldTxtLen = str.length();
			}
			break;
		case "Save":
			if (originalFile == null)
				originalFile = filename;
			// if user has not modified the content of string
			if ((oldTxtLen != newTxtLen) && oldTxtLen != 0)
				warn.showWarnMsg(5);
			else if (filename.equals("")) {
				// it is for a new file to be saved
				dialRetVal = jFileChs.showSaveDialog(this);
				if (dialRetVal == 0) {
					f = jFileChs.getSelectedFile();
					// checking weather file already exists or not
					if (!f.exists()) {
						filename = f.getPath();
						originalFile = f.getName();
						forw.writeDataToFile(filename, jta.getText());
					} else {
						warn.showWarnMsg(14);
					}
				}
			} else {
				forw.writeDataToFile(filename, jta.getText());
			}
			break;
		case "Encrypt":
			// if to check whether user is trying to encrypt without setting
			// keys
			if (set_key_status != false) {
				// once the text has been encrypted it is not allowed to edit it
				jta.setEditable(false);
				// checking whether a file is being selected
				if (filename.equals(""))
					warn.showWarnMsg(2);
				else {
					callCipherMethod(filename, key, 1);
					jbtnKey.setSelected(false);
					// 29-4-2015
					// switching back to decrytion mode on user choice
					if (warn.showWarnMsg(8) == 0) {
						setEncDecMode("Dec");
						jmiDec.setSelected(true);
						jta.setForeground(Color.red);
					}
				}
			} else {
				warn.showWarnMsg(3);
			}
			break;
		case "Decrypt":
			// if to check whether user is trying to encrypt without setting
			// keys
			jta.setEditable(true);
			if (filename.equals(""))
				warn.showWarnMsg(2);
			else {
				callCipherMethod(filename, key, 2);
				jbtnKey.setSelected(false);
				// 29-4-2015 after decryption , setting encryption mode on user
				// choice
				if (warn.showWarnMsg(9) == 0) {
					setEncDecMode("Enc");
					jmiEnc.setSelected(true);
					jta.setForeground(Color.green);
				}
			}
			set_key_status = false;
			break;
		case "Set Key":
			newKeyForm(encCipher);
			break;
		case "Click":
			// two keys for affine cipher
			boolean fg = true;
			if (encCipher.equals("Affine")) {
				key[0] = (int) k0.getSelectedItem();
				key[1] = (int) k1.getSelectedItem();
				// if transposition cipher adding two more keys in text
			} else if (encCipher.equals("Transposition")) {
				key[0] = (int) tk0.getSelectedItem();
				key[1] = (int) tk1.getSelectedItem();
				key[2] = (int) tk2.getSelectedItem();
				key[3] = (int) tk3.getSelectedItem();
				if ((key[0] == key[1] || key[0] == key[2] || key[0] == key[3])
						|| (key[1] == key[2] || key[1] == key[3] || key[2] == key[3])) {
					warn.showWarnMsg(1);
					fg = false;
				}
			}
			// if user enterd unique key values
			if (fg != false) {
				set_key_status = true;
				keyFrm.setVisible(false);
			}
			break;
		case "Encryption":
			// 29-4-2015, switching to decryption mode
			setEncDecMode("Enc");
			break;
		case "Decryption":
			// 29-4-2015, switching to encryption mode
			setEncDecMode("Dec");
			break;
		case "database":
			// saving file data into database
			if (jta.getText().length() != 0) {
				DbPrg db = new DbPrg(UserName, password, "filedata", 4);
				try {
					// extracing only file name from the text
					db.saveFilesEncryptedDataToDb(originalText, originalFile);
					warn.showWarnMsg(22);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("EncryptTxt (actionPerformed) : " + e);
				}
			}else{
				warn.showWarnMsg(25);
			}
				
			break;
		case "Help":
			String helpMsg = "Encrypt/Decrypt \n"
				+ "i) Select a Text File from MenuBar\nii) Set a mode (encryption/decryption) from menu\n"
				+ "iii) Select a encryption technique from combobox\niv) Set keys for the technique\n"
				+ "v) Press Encrypt button\nvi) (optional) click save to db button to save text to Database";
			new ShowWarning(helpMsg);
			break;
		case "Cancel":
			keyFrm.setVisible(false);
			jbtnKey.setSelected(false);
			break;
		}
	}

	// 29-4-2015 method to setting modes encryption or decryption
	private void setEncDecMode(String selectedModeStr) {
		if (selectedModeStr.equals("Enc")) {
			// disabling and enabling components according to mode
			jbtnDec.setEnabled(false);
			jbtnEnc.setEnabled(true);
			jbtnKey.setEnabled(true);
			jcb.setEnabled(true);
			jta.setForeground(Color.green);
		} else {
			// disabling all the encryption related buttons from the frame
			jbtnDec.setEnabled(true);
			jbtnEnc.setEnabled(false);
			jbtnKey.setEnabled(false);
			jcb.setEnabled(false);
			jta.setForeground(Color.red);
		}
	}

	private String fileText = "";
	public static String originalText = "";
	public static String originalFile = "";

	// method that will call encryption or decryption algorthim of affine or
	// transposition cipher
	// on the value of type=(1,2), and key will contain key for algorithm
	// accordingly
	public void callCipherMethod(String filename, int key[], int type) {
		FileOperReadWrite frw = new FileOperReadWrite();
		AutokeyCipher atk = new AutokeyCipher();
		// condition for decryption if selected no need for key and technique
		// name
		boolean hadError = false;
		int lenWithoutKey = 0;
		if (type == 1) {
			if (encCipher.equals("Affine")) {
				try {
					fileText = new AffineCipher().affineMeth(filename, key,
							type);
					lenWithoutKey = fileText.length();
					// adding encKey with ciphername, keys and username
					String encKey = "A-" + key[0] + "-" + key[1] + "-"
							+ UserName;

					// encrypting the key and adding it with dollar $ symbol
					int keyTemp = UserName.length();
					encKey = atk.AutokeyMeth(encKey, keyTemp, type);
					fileText += "$" + encKey;

					// 5-14-2016, this originaltext will be saved to database
					originalText = fileText;
				} catch (Exception ex) {
					System.out.println("EncryptTxt (callCipherMeth) : " + ex);
				}
			} else if (encCipher.equals("Transposition")) {
				try {
					fileText = new KeyTranCipher().KeyTranMeth(filename, key,
							type);
					lenWithoutKey = fileText.length();
					// adding transposition cipher key at the end of file
					String encKey = "T-" + key[0] + "-" + key[1] + "-" + key[2]
							+ "-" + key[3] + "-" + UserName;

					// encrypting the key and adding it with dollar $ symbol
					int keyTemp = UserName.length();
					encKey = atk.AutokeyMeth(encKey, keyTemp, type);
					fileText += "$" + encKey;
					originalText = fileText;
				} catch (Exception ex) {
					System.out.println("EncryptTxt (callCipherMeth) : " + ex);
				}
			} else {
				warn.showWarnMsg(0);
				hadError = true;
			}
		} else if (type == 2) {
			try {
				// getting text from the encrypted file
				fileText = frw.readFileData(filename);

				// extracting encrypted key from the text file
				int index = fileText.lastIndexOf('$');
				String decKey = fileText
						.substring(index + 1, fileText.length());

				// decrypting the encrypted key with username length
				int keyTemp = UserName.length();
				decKey = atk.AutokeyMeth(decKey, keyTemp, type);

				// checking wether user is the one who encrypted files or not
				int tindx = decKey.lastIndexOf("-");
				if (decKey.substring(tindx + 1, decKey.length()).equals(
						UserName)) {
					// now adding decrypted key to the text file
					fileText = fileText.substring(0, index) + "$" + decKey;
					frw.writeDataToFile(filename, fileText);

					String techType = decKey.substring(0, 1);

					// according to the extracted technique calling decryption
					// algorithm
					if (techType.equals("A")) {
						extractDecryptKey(fileText, 1, 3);
						fileText = new AffineCipher().affineMeth(filename, key,
								type);
					} else if (techType.equals("T")) {
						extractDecryptKey(fileText, 2, 5);
						fileText = new KeyTranCipher().KeyTranMeth(filename,
								key, type);
					} else {
						warn.showWarnMsg(6);
						hadError = true;
					}
				} else {
					warn.showWarnMsg(12);
					hadError = true;
				}
			} catch (Exception e) {
				System.out.println("EncryptTxt (callCipherMeth) : " + e);
			}
		}
		// only writing if no error
		if (hadError == false) {
			frw.writeDataToFile(filename, fileText);
			if (type == 1) {
				jta.setText(fileText.substring(0, lenWithoutKey));
			} else {
				jta.setText(fileText);
			}
		}
	}

	// method to extract the keys from the text file
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
			// extracting keys one by one and assinging it to keyvars
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
			new EncryptTxt().setVisible(true);
		});
	}
}
