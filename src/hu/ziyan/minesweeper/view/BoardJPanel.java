package hu.ziyan.minesweeper.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class BoardJPanel extends JPanel implements BoardGUI {
	private static final long serialVersionUID = -3378369239204225291L;

	private static final String EMPTY = "";
	private static final String FLAG = "P";

	private JButton[][] buttonField;
	private MinesweeperGUI gui;

	private JButton timeButton;

	public BoardJPanel(MinesweeperGUI gui) {
		super();
		this.gui = gui;

		JPanel fieldPanel = getFieldPanel(gui.getController().getRows(), gui.getController().getColumns());
		this.add(fieldPanel);

		JPanel miscPanel = getMiscPanel();
		this.add(miscPanel);
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
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		ImageIcon flagIcon = new ImageIcon("res/flag-32.png");
		JButton flagsButton = new JButton("0/10", flagIcon);
		flagsButton.setHorizontalTextPosition(JButton.CENTER);
		flagsButton.setVerticalTextPosition(JButton.BOTTOM);
		flagsButton.setFont(flagsButton.getFont().deriveFont(14.0f));
		panel.add(flagsButton);

		panel.add(Box.createVerticalGlue());

		ImageIcon restartIcon = new ImageIcon("res/restart-32.png");
		timeButton = new JButton("0:00", restartIcon);
		timeButton.setHorizontalTextPosition(JButton.CENTER);
		timeButton.setVerticalTextPosition(JButton.BOTTOM);
		timeButton.setFont(timeButton.getFont().deriveFont(14.0f));
		timeButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				gui.getController().newGame();
			}
		});
		panel.add(timeButton);

		return panel;
	}

	public void setGameTime(int time) {
		StringBuilder stb = new StringBuilder(String.valueOf(time / 60));
		stb.append(":");
		if (time % 60 < 10) {
			stb.append("0");
		}
		stb.append(time % 60);

		timeButton.setText(stb.toString());
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

	public void revealNumberField(int row, int column, int nearbyMines) {
		buttonField[row][column].setContentAreaFilled(false);
		buttonField[row][column].setFocusable(false);
		if (nearbyMines == 1) {
			buttonField[row][column].setText("<html><font color=blue>1</font></html>");
		} else if (nearbyMines == 2) {
			buttonField[row][column].setText("<html><font color=orange>2</font></html>");
		} else if (nearbyMines > 2) {
			buttonField[row][column].setText("<html><font color=red>" + nearbyMines + "</font></html>");
		}
	}

	public void removeFlag(int row, int column) {
		buttonField[row][column].setText(EMPTY);
	}

	public void placeFlag(int row, int column) {
		buttonField[row][column].setText(FLAG);
	}

	public void revealMine(int row, int column) {
		buttonField[row][column].setContentAreaFilled(false);
		buttonField[row][column].setFocusable(false);
		buttonField[row][column].setText("<html><font color=red>X</font></html>");
	}

	public void revealEmptyPosition(int row, int column) {
		buttonField[row][column].invalidate();
	}
}
