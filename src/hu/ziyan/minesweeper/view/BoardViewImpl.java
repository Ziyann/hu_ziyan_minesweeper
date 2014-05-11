package hu.ziyan.minesweeper.view;

import static hu.ziyan.minesweeper.controller.MinesweeperController.MINE;

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

class BoardViewImpl extends JPanel implements BoardView {
	private static final long serialVersionUID = -3378369239204225291L;

	private JButton[][] buttonField;
	private final ViewController gui;
	private JLabel flagsLabel;
	private JLabel timeLabel;

	BoardViewImpl(final ViewController gui) {
		super();
		this.setLayout(new BorderLayout());
		this.gui = gui;

		final JPanel fieldPanel = getFieldPanel(gui.getController().getRows(), gui.getController().getColumns());
		this.add(fieldPanel, BorderLayout.LINE_START);

		final JPanel miscPanel = getMiscPanel();
		miscPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.add(miscPanel, BorderLayout.LINE_END);
	}

	private JPanel getFieldPanel(final int rows, final int columns) {
		final JPanel panel = new JPanel(new GridBagLayout());
		final GridBagConstraints gbc = new GridBagConstraints();

		/*
		 * place buttons
		 */
		this.buttonField = new JButton[rows][columns];
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				gbc.gridx = column;
				gbc.gridy = row;
				buttonField[row][column] = createFieldButton(row, column);
				panel.add(buttonField[row][column], gbc);
			}
		}

		return panel;
	}

	private JPanel getMiscPanel() {
		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		final ImageIcon flagIcon = new ImageIcon(getClass().getResource("/res/img/flag-32.png"));
		flagsLabel = new JLabel("0/10", flagIcon, JLabel.CENTER);
		flagsLabel.setHorizontalTextPosition(JLabel.CENTER);
		flagsLabel.setVerticalTextPosition(JLabel.BOTTOM);
		flagsLabel.setFont(flagsLabel.getFont().deriveFont(14.0f));
		panel.add(flagsLabel, BorderLayout.PAGE_START);

		final ImageIcon timeIcon = new ImageIcon(getClass().getResource("/res/img/time-32.png"));
		timeLabel = new JLabel("0:00", timeIcon, JLabel.CENTER);
		timeLabel.setHorizontalTextPosition(JLabel.CENTER);
		timeLabel.setVerticalTextPosition(JLabel.BOTTOM);
		timeLabel.setFont(timeLabel.getFont().deriveFont(14.0f));
		panel.add(timeLabel, BorderLayout.CENTER);

		final ImageIcon restartIcon = new ImageIcon(getClass().getResource("/res/img/restart-32.png"));
		final JButton restartButton = new JButton(restartIcon);
		restartButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(final MouseEvent event) {
				gui.getController().newGame(gui.getController().getRows(), gui.getController().getColumns(),
						gui.getController().getMines());
			}
		});
		panel.add(restartButton, BorderLayout.PAGE_END);

		return panel;
	}

	private JButton createFieldButton(final int row, final int column) {
		final JButton button = new JButton();

		button.setPreferredSize(new Dimension(25, 25));
		button.setMargin(new Insets(1, 1, 1, 1));
		button.setFont(this.getFont().deriveFont(20.0f));

		button.addMouseListener(new MouseAdapter() {
			boolean pressed;

			@Override
			public void mouseExited(MouseEvent e) {
				pressed = false;
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				pressed = true;
			}

			public void mouseReleased(final MouseEvent event) {
				if (pressed) {
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

	public void setFlagsNumber(final int flagsNumber) {
		final StringBuilder stb = new StringBuilder(String.valueOf(flagsNumber));
		stb.append('/');
		stb.append(gui.getController().getMines());

		flagsLabel.setText(stb.toString());
	}

	public void setGameTime(final int time) {
		final StringBuilder stb = new StringBuilder(String.valueOf(time / 60));
		stb.append(':');
		if (time % 60 < 10) {
			stb.append('0');
		}
		stb.append(time % 60);

		timeLabel.setText(stb.toString());
	}

	public void revealPosition(final int row, final int column, final int content) {
		buttonField[row][column].setContentAreaFilled(false);
		buttonField[row][column].setFocusable(false);
		if (content == MINE) {
			final ImageIcon mineIcon = new ImageIcon(getClass().getResource("/res/img/mine-20-red.png"));
			buttonField[row][column].setIcon(mineIcon);
		} else if (content == 1) {
			buttonField[row][column].setText("<html><font color=blue>1</font></html>");
		} else if (content == 2) {
			buttonField[row][column].setText("<html><font color=orange>2</font></html>");
		} else if (content > 2) {
			buttonField[row][column].setText("<html><font color=red>" + content + "</font></html>");
		}
	}

	public void removeFlag(final int row, final int column) {
		buttonField[row][column].setIcon(null);
	}

	public void placeFlag(final int row, final int column) {
		final ImageIcon flagIcon = new ImageIcon(getClass().getResource("/res/img/flag-20.png"));
		buttonField[row][column].setIcon(flagIcon);
	}
}
