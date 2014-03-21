package hu.ziyan.minesweeper;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JFormattedTextField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

public class NehezsegValasztas extends JFrame {
	private static final long serialVersionUID = -8048440943970987333L;
	private JRadioButton rdbtnKezdo;
	private JRadioButton rdbtnKozepes;
	private JRadioButton rdbtnNehez;
	private ButtonGroup nehezsegGroup = new ButtonGroup();
	private JLabel lblMagassag;
	private JLabel lblSzelesseg;
	private JLabel lblAknak;
	private JTextField textFieldMagassag;
	private JTextField textFieldSzelesseg;
	private JTextField textFieldAknak;
	private JButton btnInditas = new JButton("Játék indítása");
	private JButton btnKilepes = new JButton("Kilépés");

	public NehezsegValasztas() {
		setTitle("Aknakeres\u0151");
		setResizable(false);
		setBounds(100, 100, 276, 306);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JLabel lblUdvozles = new JLabel(
				"<html>\u00DCdv\u00F6z\u00F6llek az aknakeres\u0151ben!<br>K\u00E9rlek v\u00E1lassz egy neh\u00E9zs\u00E9gi szintet.</html>");
		lblUdvozles.setHorizontalAlignment(SwingConstants.CENTER);
		lblUdvozles.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblUdvozles.setBounds(10, 1, 250, 41);
		getContentPane().add(lblUdvozles);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Neh\u00E9zs\u00E9gi szint", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 42, 250, 191);
		getContentPane().add(panel);
		panel.setLayout(null);

		rdbtnKezdo = new JRadioButton("<html>Kezdő<br>10 akna<br>9x9-es rács</html>");
		rdbtnKezdo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldMagassag.setEnabled(false);
				textFieldSzelesseg.setEnabled(false);
				textFieldAknak.setEnabled(false);
			}
		});
		nehezsegGroup.add(rdbtnKezdo);
		rdbtnKezdo.setBounds(17, 22, 81, 46);
		panel.add(rdbtnKezdo);
		rdbtnKezdo.setSelected(true);

		rdbtnKozepes = new JRadioButton("<html>Közepes<br>40 akna<br>16x16-os rács</html>");
		rdbtnKozepes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldMagassag.setEnabled(false);
				textFieldSzelesseg.setEnabled(false);
				textFieldAknak.setEnabled(false);
			}
		});
		nehezsegGroup.add(rdbtnKozepes);
		rdbtnKozepes.setBounds(17, 80, 93, 46);
		panel.add(rdbtnKozepes);

		rdbtnNehez = new JRadioButton("<html>Nehéz<br>99 akna<br>16x30-as rács</html>");
		rdbtnNehez.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldMagassag.setEnabled(false);
				textFieldSzelesseg.setEnabled(false);
				textFieldAknak.setEnabled(false);
			}
		});
		nehezsegGroup.add(rdbtnNehez);
		rdbtnNehez.setBounds(17, 138, 93, 46);
		panel.add(rdbtnNehez);

		JRadioButton rdbtnEgyeni = new JRadioButton("Egyéni");
		rdbtnEgyeni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldMagassag.setEnabled(true);
				textFieldSzelesseg.setEnabled(true);
				textFieldAknak.setEnabled(true);
			}
		});
		nehezsegGroup.add(rdbtnEgyeni);
		rdbtnEgyeni.setBounds(128, 20, 109, 23);
		panel.add(rdbtnEgyeni);

		textFieldMagassag = new JFormattedTextField(NumberFormat.getInstance());
		textFieldMagassag.setText("9");
		textFieldMagassag.setBounds(204, 50, 29, 20);
		panel.add(textFieldMagassag);
		textFieldMagassag.setColumns(10);
		textFieldMagassag.setEnabled(false);

		textFieldSzelesseg = new JFormattedTextField(NumberFormat.getInstance());
		textFieldSzelesseg.setText("9");
		textFieldSzelesseg.setBounds(204, 79, 29, 20);
		panel.add(textFieldSzelesseg);
		textFieldSzelesseg.setColumns(10);
		textFieldSzelesseg.setEnabled(false);

		textFieldAknak = new JFormattedTextField(NumberFormat.getInstance());
		textFieldAknak.setText("10");
		textFieldAknak.setBounds(204, 106, 29, 20);
		panel.add(textFieldAknak);
		textFieldAknak.setColumns(10);
		textFieldAknak.setEnabled(false);

		lblMagassag = new JLabel("Magass\u00E1g (6-40):");
		lblMagassag.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMagassag.setBounds(101, 50, 93, 20);
		panel.add(lblMagassag);

		lblSzelesseg = new JLabel("Sz\u00E9less\u00E9g (6-70):");
		lblSzelesseg.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSzelesseg.setBounds(101, 79, 93, 20);
		panel.add(lblSzelesseg);

		lblAknak = new JLabel("Akn\u00E1k:");
		lblAknak.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAknak.setBounds(139, 106, 55, 20);
		panel.add(lblAknak);

		JLabel lblSegtsgKrseShift = new JLabel("<html>Seg\u00EDts\u00E9g: shift + kattint\u00E1s. Ez 30 mp b\u0171ntet\u00E9st jelent.</html>");
		lblSegtsgKrseShift.setBounds(116, 150, 134, 41);
		panel.add(lblSegtsgKrseShift);

		btnInditas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnKezdo.isSelected()) {
					JFrame tabla = new Tabla(9, 9, 10, "Kezdő");
					tabla.setVisible(true);
					setVisible(false);
					/*
					 JFrame eredmenyek = new Eredmenyek("Kezdő", 155);
					 eredmenyek.setVisible(true);
					 */
				} else if (rdbtnKozepes.isSelected()) {
					JFrame tabla = new Tabla(16, 16, 40, "Közepes");
					setVisible(false);
					tabla.setVisible(true);
				} else if (rdbtnNehez.isSelected()) {
					JFrame tabla = new Tabla(16, 30, 99, "Nehéz");
					setVisible(false);
					tabla.setVisible(true);
				} else {
					if (!textFieldMagassag.getText().equals("")
							&& !textFieldSzelesseg.getText().equals("")
							&& !textFieldAknak.getText().equals("")
							&& Integer.parseInt(textFieldMagassag.getText()) > 5
							&& Integer.parseInt(textFieldMagassag.getText()) < 41
							&& Integer.parseInt(textFieldSzelesseg.getText()) > 5
							&& Integer.parseInt(textFieldSzelesseg.getText()) < 71
							&& Integer.parseInt(textFieldAknak.getText()) < (Integer.parseInt(textFieldSzelesseg.getText()) * Integer
									.parseInt(textFieldMagassag.getText()))) {
						JFrame tabla = new Tabla(Integer.parseInt(textFieldMagassag.getText()), Integer.parseInt(textFieldSzelesseg.getText()),
								Integer.parseInt(textFieldAknak.getText()), "Egyedi");
						setVisible(false);
						tabla.setVisible(true);
					}
				}
			}
		});
		btnInditas.setBounds(161, 244, 99, 23);
		getContentPane().add(btnInditas);

		btnKilepes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnKilepes.setBounds(10, 244, 89, 23);
		getContentPane().add(btnKilepes);
	}
}
