package hu.ziyan.minesweeper.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class ChooseDifficultyJPanel extends JPanel {
	private static final long serialVersionUID = 3842457598140735608L;
	
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
	private JButton btnInditas = new JButton(Labels.start_game_label);
	private JButton btnKilepes = new JButton(Labels.exit_label);
	
	MinesweeperGUI gui;
	
	public ChooseDifficultyJPanel(MinesweeperGUI gui) {
		super();
		this.gui = gui;
		fill();
	}
	
	private void fill() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbcThis = new GridBagConstraints();

		JLabel lblUdvozles = new JLabel(
				"<html>\u00DCdv\u00F6z\u00F6llek az aknakeres\u0151ben!<br>K\u00E9rlek v\u00E1lassz egy neh\u00E9zs\u00E9gi szintet.</html>");
		lblUdvozles.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		gbcThis.gridx = 0;
		gbcThis.gridy = 0;
		gbcThis.gridwidth = 3;
		this.add(lblUdvozles, gbcThis);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Neh\u00E9zs\u00E9gi szint", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		gbcThis.gridx = 0;
		gbcThis.gridy = 1;
		gbcThis.gridwidth = 3;
		this.add(panel, gbcThis);
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbcPanel = new GridBagConstraints();

		rdbtnKezdo = new JRadioButton("<html>Kezdő<br>10 akna<br>9x9-es rács</html>");
		rdbtnKezdo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldMagassag.setEnabled(false);
				textFieldSzelesseg.setEnabled(false);
				textFieldAknak.setEnabled(false);
			}
		});
		nehezsegGroup.add(rdbtnKezdo);
		
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 0;
		panel.add(rdbtnKezdo, gbcPanel);
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
		
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 1;
		panel.add(rdbtnKozepes, gbcPanel);

		rdbtnNehez = new JRadioButton("<html>Nehéz<br>99 akna<br>16x30-as rács</html>");
		rdbtnNehez.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldMagassag.setEnabled(false);
				textFieldSzelesseg.setEnabled(false);
				textFieldAknak.setEnabled(false);
			}
		});
		nehezsegGroup.add(rdbtnNehez);
		
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 2;
		panel.add(rdbtnNehez, gbcPanel);

		JRadioButton rdbtnEgyeni = new JRadioButton(Labels.diff_custom_label);
		rdbtnEgyeni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldMagassag.setEnabled(true);
				textFieldSzelesseg.setEnabled(true);
				textFieldAknak.setEnabled(true);
			}
		});
		nehezsegGroup.add(rdbtnEgyeni);
		gbcPanel.gridx = 1;
		gbcPanel.gridy = 0;
		panel.add(rdbtnEgyeni, gbcPanel);

		lblMagassag = new JLabel("Magass\u00E1g (6-40):");
		textFieldMagassag = new JFormattedTextField(NumberFormat.getInstance());
		textFieldMagassag.setText("9");
		textFieldMagassag.setColumns(3);
		textFieldMagassag.setEnabled(false);
		gbcPanel.gridx = 1;
		gbcPanel.gridy = 1;
		panel.add(lblMagassag);
		panel.add(textFieldMagassag, gbcPanel);
		
		lblSzelesseg = new JLabel("Sz\u00E9less\u00E9g (6-70):");
		textFieldSzelesseg = new JFormattedTextField(NumberFormat.getInstance());
		textFieldSzelesseg.setText("9");
		textFieldSzelesseg.setColumns(3);
		textFieldSzelesseg.setEnabled(false);
		gbcPanel.gridx = 1;
		gbcPanel.gridy = 2;
		panel.add(lblSzelesseg);
		panel.add(textFieldSzelesseg, gbcPanel);

		lblAknak = new JLabel("Akn\u00E1k:");
		textFieldAknak = new JFormattedTextField(NumberFormat.getInstance());
		textFieldAknak.setText("10");
		textFieldAknak.setColumns(3);
		textFieldAknak.setEnabled(false);
		gbcPanel.gridx = 1;
		gbcPanel.gridy = 3;
		panel.add(lblAknak);
		panel.add(textFieldAknak);

		

		JLabel lblSegtsgKrseShift = new JLabel("<html>Seg\u00EDts\u00E9g: shift + kattint\u00E1s. Ez 30 mp b\u0171ntet\u00E9st jelent.</html>");
		panel.add(lblSegtsgKrseShift);

		btnInditas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnKezdo.isSelected()) {
					gui.showBoardPanel(9, 9, 10, "Kezd�");
				} else if (rdbtnKozepes.isSelected()) {
					gui.showBoardPanel(16, 16, 40, "K�zepes");
				} else if (rdbtnNehez.isSelected()) {
					gui.showBoardPanel(16, 30, 99, "Neh�z");
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
						gui.showBoardPanel(Integer.parseInt(textFieldMagassag.getText()), Integer.parseInt(textFieldSzelesseg.getText()),
								Integer.parseInt(textFieldAknak.getText()), "Egyedi");
					}
				}
			}
		});
		
		gbcThis.gridx = 2;
		gbcThis.gridy = 2;
		gbcThis.gridwidth = 1;
		this.add(btnInditas, gbcThis);

		btnKilepes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		gbcThis.gridx = 0;
		gbcThis.gridy = 2;
		gbcThis.gridwidth = 1;
		this.add(btnKilepes, gbcThis);
	}
}
