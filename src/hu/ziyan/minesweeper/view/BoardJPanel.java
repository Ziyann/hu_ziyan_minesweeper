package hu.ziyan.minesweeper.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class BoardJPanel extends JPanel {

	private static final long serialVersionUID = -3378369239204225291L;

	private int time;
	private JLabel idomero;
	private MinesweeperGUI gui;

	public BoardJPanel(MinesweeperGUI gui) {
		super();
		this.gui = gui;
		this.time = 0;
		idomero = new JLabel(Labels.time_label + ": 0");
		idomero.setFont(idomero.getFont().deriveFont(15.0f));
		createBoard();
	}
	
	public void increaseTimer() {
		idomero.setText(Labels.time_label + ": " + ++time);
	}

	private void createBoard() {
		JPanel foAblak = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		/*
		 * place buttons
		 */
		for (int row = 0; row < gui.getController().getMinefield().getRows(); row++) {
			for (int column = 0; column < gui.getController().getMinefield().getColumns(); column++) {
				c.gridx = column;
				c.gridy = row;
				foAblak.add(gui.getController().getMinefield().getButtonField(row, column), c);
			}
		}

		c.insets = new Insets(0, 0, 0, 2);
		c.gridy = gui.getController().getMinefield().getRows();
		c.anchor = GridBagConstraints.LINE_END;
		c.gridx = gui.getController().getMinefield().getColumns() - 3;
		c.gridwidth = 4;
		foAblak.add(idomero, c);

		add(foAblak, BorderLayout.CENTER);
	}
}
