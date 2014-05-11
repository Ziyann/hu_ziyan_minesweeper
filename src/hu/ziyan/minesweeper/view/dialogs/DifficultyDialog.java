package hu.ziyan.minesweeper.view.dialogs;

import static hu.ziyan.minesweeper.controller.MinesweeperController.MAX_COLUMNS;
import static hu.ziyan.minesweeper.controller.MinesweeperController.MAX_ROWS;
import static hu.ziyan.minesweeper.controller.MinesweeperController.MIN_COLUMNS;
import static hu.ziyan.minesweeper.controller.MinesweeperController.MIN_ROWS;
import hu.ziyan.minesweeper.view.Labels;
import hu.ziyan.minesweeper.view.ViewController;

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
	private final ViewController gui;
	private final ButtonGroup diffButtonGroup = new ButtonGroup();
	private JButton btnOk;
	private JButton btnCancel;
	private JRadioButton rdbtnCustom;
	private JRadioButton rdbtnBeginner;
	private JRadioButton rdbtnIntermediate;
	private JRadioButton rdbtnAdvanced;
	private JTextField textFieldHeight;
	private JTextField textFieldWidth;
	private JTextField textFieldMines;

	public DifficultyDialog(final ViewController gui, final boolean modal) {
		super(gui.getWindow(), modal);
		this.gui = gui;

		this.setTitle(Labels.DIFFICULTY);
		this.setResizable(false);

		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

		final JPanel diffPanel = createDiffPanel();
		final JPanel buttonsPanel = createButtonsPanel();

		this.add(diffPanel);
		this.add(buttonsPanel);

		this.pack();
		this.setLocationRelativeTo(gui.getWindow());
		this.setVisible(true);
	}

	private JPanel createButtonsPanel() {
		final JPanel buttonsPanel = new JPanel();

		btnOk = new JButton(Labels.OK);
		btnCancel = new JButton(Labels.CANCEL);

		btnOk.addActionListener(this);
		btnCancel.addActionListener(this);

		buttonsPanel.add(btnOk);
		buttonsPanel.add(btnCancel);

		return buttonsPanel;
	}

	private JPanel createCustomDiffPanel() {
		final JPanel customDiffPanel = new JPanel();

		customDiffPanel.setLayout(new GridBagLayout());
		final GridBagConstraints gbc = new GridBagConstraints();

		rdbtnCustom = new JRadioButton(Labels.CUSTOM);
		rdbtnCustom.addActionListener(this);
		diffButtonGroup.add(rdbtnCustom);

		final JLabel lblHeight = new JLabel(Labels.HEIGHT + " (" + MIN_ROWS + "-" + MAX_ROWS + "):");
		final JLabel lblWidth = new JLabel(Labels.WIDTH + " (" + MIN_COLUMNS + "-" + MAX_COLUMNS + "):");
		final JLabel lblMines = new JLabel(Labels.MINES + ": ");

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

		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.insets = new Insets(0, 12, 6, 2);

		gbc.gridx = 0;
		gbc.gridy = 0;
		customDiffPanel.add(rdbtnCustom, gbc);

		gbc.gridy = 1;
		gbc.gridx = 0;
		customDiffPanel.add(lblHeight, gbc);
		gbc.gridx = 1;
		customDiffPanel.add(textFieldHeight, gbc);

		gbc.gridy = 2;
		gbc.gridx = 0;
		customDiffPanel.add(lblWidth, gbc);
		gbc.gridx = 1;
		customDiffPanel.add(textFieldWidth, gbc);

		gbc.gridy = 3;
		gbc.gridx = 0;
		customDiffPanel.add(lblMines, gbc);
		gbc.gridx = 1;
		customDiffPanel.add(textFieldMines, gbc);

		return customDiffPanel;
	}

	private JPanel createDiffPanel() {
		final JPanel diffPanel = new JPanel();
		diffPanel.setLayout(new FlowLayout());
		final JPanel predefinedDiffPanel = new JPanel();
		final JPanel customDiffPanel = createCustomDiffPanel();

		predefinedDiffPanel.setLayout(new BoxLayout(predefinedDiffPanel, BoxLayout.PAGE_AXIS));

		rdbtnBeginner = new JRadioButton(Labels.DIFF_BEGINNER_DESC);
		rdbtnIntermediate = new JRadioButton(Labels.DIFF_INTERMEDIATE_DESC);
		rdbtnAdvanced = new JRadioButton(Labels.DIFF_ADVANCED_DESC);
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
	public void actionPerformed(final ActionEvent event) {
		if (event.getSource() == this.btnOk) {
			if (this.rdbtnBeginner.isSelected()) {
				gui.getController().newGame(9, 9, 10);
			} else if (this.rdbtnIntermediate.isSelected()) {
				gui.getController().newGame(16, 16, 40);
			} else if (this.rdbtnAdvanced.isSelected()) {
				gui.getController().newGame(16, 30, 99);
			} else { // custom difficulty
				int height = textFieldHeight.getText().isEmpty() ? 0 : Integer.parseInt(textFieldHeight.getText());
				int width = textFieldWidth.getText().isEmpty() ? 0 : Integer.parseInt(textFieldWidth.getText());
				int mines = textFieldMines.getText().isEmpty() ? 0 : Integer.parseInt(textFieldMines.getText());
				gui.getController().newGame(height, width, mines);
			}
			this.setVisible(false);
		} else if (event.getSource() == this.btnCancel) {
			this.setVisible(false);
		} else if (event.getSource() == this.rdbtnCustom) {
			this.textFieldHeight.setEnabled(true);
			this.textFieldWidth.setEnabled(true);
			this.textFieldMines.setEnabled(true);
		} else if (event.getSource() == this.rdbtnBeginner || event.getSource() == this.rdbtnAdvanced
				|| event.getSource() == this.rdbtnIntermediate) {
			this.textFieldHeight.setEnabled(false);
			this.textFieldWidth.setEnabled(false);
			this.textFieldMines.setEnabled(false);
		}
	}

}
