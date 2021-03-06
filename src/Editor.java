import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 
 * @author Pawel Paszki This application changes unreadable characters in polish
 *         subtitles into proper polish characters
 * @version 16/06/2016
 */
public class Editor implements ActionListener {

	private JFrame mainWindow; // main frame
	private JButton loadButton; // button used to load subtitles
	private final FileNameExtensionFilter filter = new FileNameExtensionFilter("txt files", "txt", "srt");
	private Scanner scanner; // scanner used to save content of text file as a String
	private JTextField result; // text field displaying result of changing subtitles
	private int counter; // counter - determining if character change occurred

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Editor().initialise();
				} catch (Exception e) {

				}
			}
		});
	}

	/**
	 * init of all components
	 */
	private void initialise() {
		mainWindow = new JFrame("Subs changer (pawelpaszki@gmail.com)");
		mainWindow.setSize(400, 110);
		mainWindow.setResizable(false);
		mainWindow.setLocationRelativeTo(null);// window is centred
		mainWindow.setVisible(true); 
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.getContentPane().setBackground(Color.black);
		mainWindow.setLayout(null);

		loadButton = new JButton("choose file");
		loadButton.setBounds(20, 20, 120, 40);
		loadButton.setFocusPainted(false);
		loadButton.addActionListener(this);
		mainWindow.getContentPane().add(loadButton);

		result = new JTextField();
		result.setBounds(180, 30, 190, 20);
		result.setEditable(false);
		result.setVisible(false);
		result.setFont(new Font("Arial", Font.BOLD, 16));
		result.setForeground(new Color(0, 255, 35)); 
		result.setBackground(Color.black);
		result.setHorizontalAlignment(JTextField.CENTER);
		mainWindow.getContentPane().add(result);

	}

	/**
	 * action taken, if the button is pressed - file chooser initialised
	 * and file saved to a string
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equals("choose file")) {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(mainWindow);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String content = null;
				try {
					scanner = new Scanner(new File(chooser.getSelectedFile().getAbsolutePath()), "Unicode");
					content = scanner.useDelimiter("\\Z").next();
				} catch (FileNotFoundException e) {

				}
				if (content != null) {

					String changed = substituteChars(content);
					result.setVisible(true);
					if (getCounter() > 0) {
						try {
							PrintWriter out = new PrintWriter(chooser.getSelectedFile().getAbsolutePath());
							out.println(changed);
							out.close();
							result.setText("subtitles changed");
						} catch (FileNotFoundException e) {
							result.setText("error occurred");
						}
					} else {
						result.setText("no characters changed");
					}
				}
			}
		}

	}

	/**
	 * @param content - String to be changed 
	 * and returned
	 */
	private String substituteChars(String content) {
		StringBuilder sb = new StringBuilder();
		sb.append(content);
		setCounter(0);
		for (int i = 0; i < sb.length(); i++) {
			switch (sb.charAt(i)) {
			case 'œ':
				sb.setCharAt(i, 'ś');
				setCounter(getCounter() + 1);
				break;
			case '³':
				sb.setCharAt(i, 'ł');
				setCounter(getCounter() + 1);
				break;
			case 'ê':
				sb.setCharAt(i, 'ę');
				setCounter(getCounter() + 1);
				break;
			case '¿':
				sb.setCharAt(i, 'ż');
				setCounter(getCounter() + 1);
				break;
			case '¯':
				sb.setCharAt(i, 'Ż');
				setCounter(getCounter() + 1);
				break;
			case '¹':
				sb.setCharAt(i, 'ą');
				setCounter(getCounter() + 1);
				break;
			case 'æ':
				sb.setCharAt(i, 'ć');
				setCounter(getCounter() + 1);
				break;
			case 'ñ':
				sb.setCharAt(i, 'ń');
				setCounter(getCounter() + 1);
				break;
			case 'Œ':
				sb.setCharAt(i, 'Ś');
				setCounter(getCounter() + 1);
				break;
			case 'Ÿ':
				sb.setCharAt(i, 'ź');
				setCounter(getCounter() + 1);
				break;
			}
		}

		return sb.toString();
	}

	/**
	 * 
	 * @return counter
	 */
	public int getCounter() {
		return counter;
	}

	/**
	 * not used
	 */
	public void setCounter(int counter) {
		this.counter = counter;
	}
}
