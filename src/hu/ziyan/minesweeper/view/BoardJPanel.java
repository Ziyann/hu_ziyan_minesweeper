package hu.ziyan.minesweeper.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

class BoardJPanel extends JPanel implements BoardGUI {
	private static final long serialVersionUID = -3378369239204225291L;

	private JButton[][] buttonField;
	private MinesweeperGUI gui;
	private JLabel flagsLabel;
	private JLabel timeLabel;

	BoardJPanel(MinesweeperGUI gui) {
		super();
		this.setLayout(new BorderLayout());
		this.gui = gui;

		JPanel fieldPanel = getFieldPanel(gui.getController().getRows(), gui.getController().getColumns());
		this.add(fieldPanel, BorderLayout.LINE_START);

		JPanel miscPanel = getMiscPanel();
		miscPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.add(miscPanel, BorderLayout.LINE_END);
	}

	private JPanel getFieldPanel(final int rows, final int columns) {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		/*
		 * place buttons
		 */
		this.buttonField = new JButton[rows][columns];
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				c.gridx = column;
				c.gridy = row;
				buttonField[row][column] = createFieldButton(row, column);
				panel.add(buttonField[row][column], c);
			}
		}

		return panel;
	}

	private JPanel getMiscPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		ImageIcon flagIcon = new ImageIcon(getClass().getResource("/res/img/flag-32.png"));
		flagsLabel = new JLabel("0/10", flagIcon, JLabel.CENTER);
		flagsLabel.setHorizontalTextPosition(JLabel.CENTER);
		flagsLabel.setVerticalTextPosition(JLabel.BOTTOM);
		flagsLabel.setFont(flagsLabel.getFont().deriveFont(14.0f));
		panel.add(flagsLabel, BorderLayout.PAGE_START);
		
		ImageIcon timeIcon = new ImageIcon(getClass().getResource("/res/img/time-32.png"));
		timeLabel = new JLabel("0:00", timeIcon, JLabel.CENTER);
		timeLabel.setHorizontalTextPosition(JLabel.CENTER);
		timeLabel.setVerticalTextPosition(JLabel.BOTTOM);
		timeLabel.setFont(timeLabel.getFont().deriveFont(14.0f));
		panel.add(timeLabel, BorderLayout.CENTER);

		ImageIcon restartIcon = new ImageIcon(getClass().getResource("/res/img/restart-32.png"));
		JButton restartButton = new JButton(restartIcon);
		restartButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				gui.getController().newGame(gui.getController().getRows(), gui.getController().getColumns(),
						gui.getController().getMines());
			}
		});
		panel.add(restartButton, BorderLayout.PAGE_END);

		return panel;
	}

	private JButton createFieldButton(final int row, final int column) {
		JButton button = new JButton();

		button.setPreferredSize(new Dimension(25, 25));
		button.setMargin(new Insets(1, 1, 1, 1));
		button.setFont(this.getFont().deriveFont(20.0f));

		button.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (buttonField[row][column].isFocusable()) {
					if (event.isMetaDown()) {
						gui.getController().placeFlag(row, column);
					} else {
						gui.getController().fieldClick(row, column);
					}
				}
			}
		});

		return button;
	}

	public void setFlagsNumber(int flagsNumber) {
		StringBuilder stb = new StringBuilder(String.valueOf(flagsNumber));
		stb.append("/");
		stb.append(gui.getController().getMines());

		flagsLabel.setText(stb.toString());
	}

	public void setGameTime(int time) {
		StringBuilder stb = new StringBuilder(String.valueOf(time / 60));
		stb.append(":");
		if (time % 60 < 10) {
			stb.append("0");
		}
		stb.append(time % 60);

		timeLabel.setText(stb.toString());
	}

	public void revealPosition(int row, int column, int nearbyMines) {
		buttonField[row][column].setContentAreaFilled(false);
		buttonField[row][column].setFocusable(false);
		if (nearbyMines == -1) {
			buttonField[row][column].setText("<html><font color=red>X</font></html>");
		} else if (nearbyMines == 1) {
			buttonField[row][column].setText("<html><font color=blue>1</font></html>");
		} else if (nearbyMines == 2) {
			buttonField[row][column].setText("<html><font color=orange>2</font></html>");
		} else if (nearbyMines > 2) {
			buttonField[row][column].setText("<html><font color=red>" + nearbyMines + "</font></html>");
		}
	}

	public void removeFlag(int row, int column) {
		buttonField[row][column].setIcon(null);
	}

	public void placeFlag(int row, int column) {
		ImageIcon flagIcon = new ImageIcon(getClass().getResource("/res/img/flag-20.png"));
		buttonField[row][column].setIcon(flagIcon);
	}
}
