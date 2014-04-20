package hu.ziyan.minesweeper.view.dialogs;

import hu.ziyan.minesweeper.view.Labels;
import hu.ziyan.minesweeper.view.MinesweeperGUI;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class DifficultyDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 2377416291709037701L;
	private MinesweeperGUI gui;
	private ButtonGroup diffButtonGroup = new ButtonGroup();
	private JButton btnOk;
	private JButton btnCancel;
	private JRadioButton rdbtnCustom;
	private JRadioButton rdbtnBeginner;
	private JRadioButton rdbtnIntermediate;
	private JRadioButton rdbtnAdvanced;
	private JTextField textFieldHeight;
	private JTextField textFieldWidth;
	private JTextField textFieldMines;

	public DifficultyDialog(MinesweeperGUI gui, boolean modal) {
		super(gui.getWindow(), modal);
		this.gui = gui;
		
		this.setTitle(Labels.difficulty);
		this.setResizable(false);

		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

		JPanel diffPanel = createDiffPanel();
		JPanel buttonsPanel = createButtonsPanel();

		this.add(diffPanel);
		this.add(buttonsPanel);

		this.pack();
		this.setLocationRelativeTo(gui.getWindow());
		this.setVisible(true);
	}

	private JPanel createButtonsPanel() {
		JPanel buttonsPanel = new JPanel();

		btnOk = new JButton(Labels.ok);
		btnCancel = new JButton(Labels.cancel);

		btnOk.addActionListener(this);
		btnCancel.addActionListener(this);

		buttonsPanel.add(btnOk);
		buttonsPanel.add(btnCancel);

		return buttonsPanel;
	}

	private JPanel createCustomDiffPanel() {
		JPanel customDiffPanel = new JPanel();

		customDiffPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		rdbtnCustom = new JRadioButton(Labels.diff_custom_label);
		rdbtnCustom.addActionListener(this);
		diffButtonGroup.add(rdbtnCustom);

		JLabel lblHeight = new JLabel(Labels.diff_height);
		JLabel lblWidth = new JLabel(Labels.diff_width);
		JLabel lblMines = new JLabel(Labels.diff_mines);

		textFieldHeight = new JFormattedTextField(NumberFormat.getInstance());
		textFieldWidth = new JFormattedTextField(NumberFormat.getInstance());
		textFieldMines = new JFormattedTextField(NumberFormat.getInstance());
		textFieldHeight.setText("15");
		textFieldHeight.setColumns(3);
		textFieldHeight.setEnabled(false);
		textFieldWidth.setText("9");
		textFieldWidth.setColumns(3);
		textFieldWidth.setEnabled(false);
		textFieldMines.setText("10");
		textFieldMines.setColumns(3);
		textFieldMines.setEnabled(false);

		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(0, 12, 6, 2);

		c.gridx = 0;
		c.gridy = 0;
		customDiffPanel.add(rdbtnCustom, c);

		c.gridy = 1;
		c.gridx = 0;
		customDiffPanel.add(lblHeight, c);
		c.gridx = 1;
		customDiffPanel.add(textFieldHeight, c);

		c.gridy = 2;
		c.gridx = 0;
		customDiffPanel.add(lblWidth, c);
		c.gridx = 1;
		customDiffPanel.add(textFieldWidth, c);

		c.gridy = 3;
		c.gridx = 0;
		customDiffPanel.add(lblMines, c);
		c.gridx = 1;
		customDiffPanel.add(textFieldMines, c);

		return customDiffPanel;
	}

	private JPanel createDiffPanel() {
		JPanel diffPanel = new JPanel();
		diffPanel.setLayout(new FlowLayout());
		JPanel predefinedDiffPanel = new JPanel();
		JPanel customDiffPanel = createCustomDiffPanel();

		predefinedDiffPanel.setLayout(new BoxLayout(predefinedDiffPanel, BoxLayout.PAGE_AXIS));

		rdbtnBeginner = new JRadioButton(Labels.diff_beginner_label);
		rdbtnIntermediate = new JRadioButton(Labels.diff_intermediate_label);
		rdbtnAdvanced = new JRadioButton(Labels.diff_advanced_label);
		rdbtnBeginner.addActionListener(this);
		rdbtnIntermediate.addActionListener(this);
		rdbtnAdvanced.addActionListener(this);
		diffButtonGroup.add(rdbtnBeginner);
		diffButtonGroup.add(rdbtnIntermediate);
		diffButtonGroup.add(rdbtnAdvanced);

		rdbtnBeginner.setSelected(true);

		predefinedDiffPanel.add(rdbtnBeginner);
		predefinedDiffPanel.add(rdbtnIntermediate);
		predefinedDiffPanel.add(rdbtnAdvanced);

		diffPanel.add(predefinedDiffPanel);

		diffPanel.add(customDiffPanel);

		return diffPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnOk) {
			if (this.rdbtnBeginner.isSelected()) {
				gui.getController().newGame(9, 9, 10);
			} else if (this.rdbtnIntermediate.isSelected()) {
				gui.getController().newGame(16, 16, 40);
			} else if (this.rdbtnAdvanced.isSelected()) {
				gui.getController().newGame(16, 30, 99);
			} else { // custom difficulty
				int height = Integer.parseInt(textFieldHeight.getText());
				int width = Integer.parseInt(textFieldWidth.getText());
				int mines = Integer.parseInt(textFieldMines.getText());
				if (height > 24) {
					height = 24;
				} else if (height < 9) {
					height = 9;
				}
				if (width > 30) {
					width = 30;
				} else if (width < 9) {
					width = 9;
				}
				if (mines > ((width * height) * 0.93)) {
					mines = (int) ((width * height) * 0.93);
				} else if (mines < 10) {
					mines = 10;
				}
				gui.getController().newGame(height, width, mines);
			}
			this.setVisible(false);
		} else if (e.getSource() == this.btnCancel) {
			this.setVisible(false);
		} else if (e.getSource() == this.rdbtnCustom) {
			this.textFieldHeight.setEnabled(true);
			this.textFieldWidth.setEnabled(true);
			this.textFieldMines.setEnabled(true);
		} else if (e.getSource() == this.rdbtnBeginner || e.getSource() == this.rdbtnAdvanced
				|| e.getSource() == this.rdbtnIntermediate) {
			this.textFieldHeight.setEnabled(false);
			this.textFieldWidth.setEnabled(false);
			this.textFieldMines.setEnabled(false);
		}
	}

}
