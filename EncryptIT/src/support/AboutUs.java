package support;

/*
 this interface will be used to display the information about
 the software and its author 27-4-2015 (completed)
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AboutUs extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	AboutUs() {
		// adding current frame properties
		super("About EncryptIT v2.0");
		setSize(285, 250);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		// adding components to the frame like button, jtextarea etc
		addComp();
	}

	private void addComp() {
		// ceating outer panel that will hold other components
		JPanel jpanAuthor = new JPanel();
		// inner panel will hold JLabel : author, email
		jpanAuthor.setBackground(Color.blue);
		JPanel jpanInner = new JPanel(new GridLayout(3, 1, 1, 0));
		jpanInner.setBackground(Color.blue);

		JLabel jlabApp = new JLabel("EncryptIT");
		jlabApp.setFont(new Font("Tahoma", Font.BOLD, 20));
		jlabApp.setForeground(Color.white);
		jpanInner.add(jlabApp);

		// adding components to the inner panel
		jlabApp = new JLabel("Author   : Mohit Uniyal");
		jlabApp.setForeground(Color.white);
		jpanInner.add(jlabApp);
		jlabApp = new JLabel("Email Id : m7uniyal@gmail.com");
		jlabApp.setForeground(Color.white);
		jpanInner.add(jlabApp);

		// jpanInner.add(jpanName);
		jpanAuthor.add(jpanInner);

		add(jpanAuthor, BorderLayout.NORTH);

		// string for the jtextArea
		String str = "This program is free software; you can redistribute it "
				+ "and/or modify it there is no legal action will be taken "
				+ "even if you make a copy of this software or use its design.";

		// adding textarea for information about software
		JTextArea jtaAboutText = new JTextArea(str);
		jtaAboutText.setLineWrap(true);
		// making margin at all sides of the textarea
		jtaAboutText.setMargin(new Insets(10, 10, 10, 10));
		jtaAboutText.setEditable(false);

		// jtaAboutText.setEnabled(false);
		jtaAboutText.setWrapStyleWord(true);

		// creating scrollable text area
		JScrollPane jspTextArea = new JScrollPane(jtaAboutText);
		jspTextArea
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		add(jspTextArea, BorderLayout.CENTER);

		// adding panel for the lowest buttons
		JPanel jbtnPan = new JPanel();
		jbtnPan.setBackground(Color.blue);
		JButton jbtnExit = new JButton("Ok");
		jbtnPan.add(jbtnExit);
		add(jbtnPan, BorderLayout.SOUTH);

		jbtnExit.addActionListener(this);

		// removing close menu bar
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);

		// adding left and right pannels to create space
		jpanInner = new JPanel();
		jpanInner.add(new JLabel("    "));
		jpanInner.setBackground(Color.blue);
		add(jpanInner, BorderLayout.EAST);
		jpanInner = new JPanel();
		jpanInner.add(new JLabel("    "));
		jpanInner.setBackground(Color.blue);
		add(jpanInner, BorderLayout.WEST);
	}

	// method handle ok button event
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("Ok")) {
			setVisible(false);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new AboutUs().setVisible(true);
		});
	}
}
