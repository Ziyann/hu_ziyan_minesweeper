package hu.ziyan.minesweeper.view;

import hu.ziyan.minesweeper.controller.MinesweeperController;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class JButtonField extends JButton implements MouseListener {
	private static final long serialVersionUID = 8816893946270507611L;

	private MinesweeperController controller;
	private boolean isMine;
	private boolean isFlagged;
	private int nearbyMines;
	private int row, column;

	public JButtonField(MinesweeperController controller, int row, int column) {
		super("");
		this.setPreferredSize(new Dimension(25, 25));
		this.setMargin(new Insets(1, 1, 1, 1));
		this.setFont(this.getFont().deriveFont(20.0f));
		this.addMouseListener(this);

		this.controller = controller;
		this.row = row;
		this.column = column;

		this.isMine = false;
		this.isFlagged = false;
	}

	public boolean isMine() {
		return this.isMine;
	}

	public void makeMine() {
		this.isMine = true;
	}

	public void setNearbyMines(int nearbyMines) {
		this.nearbyMines = nearbyMines;
	}

	public boolean isFlagged() {
		return this.isFlagged;
	}

	public boolean isEmpty() {
		if (this.nearbyMines == 0 && this.isEnabled() && !this.isMine) {
			return true;
		} else {
			return false;
		}
	}

	public void showFlaggedMine() {
		this.setContentAreaFilled(false);
		this.removeMouseListener(this);
		this.setFocusable(false);
		this.setText("<html><font color=red>P</font></html>");
	}
	
	public void reveal() {
		this.setContentAreaFilled(false);
		this.removeMouseListener(this);
		this.setFocusable(false);
		if (this.isEmpty()) {
			this.setEnabled(false);
			controller.revealNearbyEmptyFields(this.row, this.column);
		} else if (nearbyMines == 1) {
			this.setText("<html><font color=blue>1</font></html>");
		} else if (nearbyMines == 2) {
			this.setText("<html><font color=orange>2</font></html>");
		} else if (nearbyMines > 2) {
			this.setText("<html><font color=red>" + nearbyMines + "</font></html>");
		} else if (isMine) {
			this.setText("<html><font color=red>X</font></html>");
			controller.loseGame();
		}
	}
	
	private void placeFlag() {
		this.setText("P");
		this.isFlagged = true;
	}

	private void removeFlag() {
		this.setText("");
		this.isFlagged = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.isMetaDown() && this.isEnabled()) {
			this.placeFlag();
		} else if (this.isFlagged) {
			this.removeFlag();
		} else if (this.isEnabled()) {
			controller.revealField(row, column);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
