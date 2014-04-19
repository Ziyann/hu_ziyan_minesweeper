package hu.ziyan.minesweeper.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BoardJPanel extends JPanel {
	private static final long serialVersionUID = -3378369239204225291L;

	private static final String EMPTY = "";
	private static final String FLAG = "P";

	private int time = 0;
	private JLabel timerJLabel;
	private JButton[][] buttonField;
	private MinesweeperGUI gui;

	public BoardJPanel(MinesweeperGUI gui) {
		super();
		this.gui = gui;

		JPanel fieldPanel = getFieldPanel(gui.getController().getRows(), gui.getController().getColumns());
		this.add(fieldPanel, BorderLayout.CENTER);

		JPanel miscPanel = getMiscPanel();
		this.add(miscPanel, BorderLayout.PAGE_END);
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

		c.insets = new Insets(0, 0, 0, 2);
		c.gridy = gui.getController().getRows();
		c.anchor = GridBagConstraints.LINE_END;
		c.gridx = gui.getController().getColumns() - 3;
		c.gridwidth = 4;

		return panel;
	}

	private JPanel getMiscPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		ImageIcon pauseIcon = new ImageIcon("res/pause-512.png");
		JButton pauseButton = new JButton(pauseIcon);
		pauseButton.setPreferredSize(buttonField[0][0].getPreferredSize());
		panel.add(pauseButton);

		timerJLabel = new JLabel(Labels.time_label + "0");
		timerJLabel.setFont(timerJLabel.getFont().deriveFont(15.0f));
		panel.add(timerJLabel);

		JButton restartButton = new JButton("R");
		panel.add(restartButton);

		return panel;
	}

	public void increaseTimer() {
		timerJLabel.setText(Labels.time_label + ++time);
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
