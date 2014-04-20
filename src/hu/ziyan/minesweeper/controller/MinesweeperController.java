package hu.ziyan.minesweeper.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import hu.ziyan.minesweeper.model.Minefield;
import hu.ziyan.minesweeper.model.services.MinefieldService;
import hu.ziyan.minesweeper.view.Labels;
import hu.ziyan.minesweeper.view.MinesweeperGUI;

public class MinesweeperController {
	private Minefield minefield;
	private MinefieldService service;
	private MinesweeperGUI gui;
	private Timer timer;
	private int time;
	private boolean isRevealingRunning = false;

	/**
	 * true if at least one field has been revealed
	 */
	private boolean gameStarted = false;

	/**
	 * Starts the desktop GUI
	 */
	public void startDesktop() {
		gui = new MinesweeperGUI(this);
		gui.startGUI();
	}

	public void newGame(int rows, int columns, int mines) {
		this.minefield = new Minefield(rows, columns, mines);
		service = new MinefieldService(this, this.minefield);
		service.makeField();
		stopTimer();
		gui.showBoardPanel();
	}

	/**
	 * Reveals the specified position
	 * 
	 * @param row
	 * @param column
	 */
	public void revealPosition(int row, int column) {
		removeFlag(row, column);
		if (minefield.getField(row, column).isMine()) {
			gui.revealPosition(row, column, -1);
			loseGame();
		} else {
			gui.revealPosition(row, column, minefield.getField(row, column).getNearbyMinesNumber());
			if (minefield.getField(row, column).isHiddenAndEmpty()) {
				revealNearbyEmptyFields(row, column);
			}
			minefield.reveal(row, column);
		}
		winGameIfOver();
	}

	private void winGameIfOver() {
		if (minefield.getRemainingFields() == 0 && this.gameStarted) {
			this.gameStarted = false;
			winGame();
		}
	}

	private void revealNearbyEmptyFields(int row, int column) {
		if (!isRevealingRunning) {
			isRevealingRunning = true;
			service.revealNearbyEmptyFields(row, column);
			isRevealingRunning = false;
		}
	}

	/**
	 * A click on a hidden position is passed here first, without any checks
	 * before the call.
	 * 
	 * @param row
	 *            the clicked row
	 * @param column
	 *            the clicked column
	 */
	public void fieldClick(int row, int column) {
		if (isFlagged(row, column)) {
			removeFlag(row, column);
		} else {
			if (!gameStarted) {
				while (minefield.getField(row, column).isMine()) {
					newGame(getRows(), getColumns(), getMines());
				}
				startGame();
			}
			revealPosition(row, column);
		}
	}

	private boolean isFlagged(int row, int column) {
		return minefield.isFlagged(row, column);
	}

	public void placeFlag(int row, int column) {
		minefield.placeFlag(row, column);
		gui.placeFlag(row, column);
	}

	private void removeFlag(int row, int column) {
		if (isFlagged(row, column)) {
			minefield.removeFlag(row, column);
			gui.removeFlag(row, column);
		}
	}

	/**
	 * Stops timer, reveals minefield and displays a dialog (new game or exit)
	 */
	private void loseGame() {
		stopTimer();
		revealMines();
		Object[] options = { Labels.new_game, Labels.exit_label };
		int valasztas = JOptionPane.showOptionDialog(gui.getWindow(), "Vesztettél! Mit kívánsz tenni?",
				Labels.gui_title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (valasztas == JOptionPane.YES_OPTION) {
			newGame(getRows(), getColumns(), getMines());
		} else {
			System.exit(0);
		}
	}

	private void revealMines() {
		for (int row = 0; row < minefield.getRows(); row++) {
			for (int column = 0; column < minefield.getColumns(); column++) {
				if (minefield.getField(row, column).isMine()) {
					gui.revealPosition(row, column, -1);
				}
			}
		}
	}

	private void startGame() {
		this.gameStarted = true;
		startTimer();
	}

	/**
	 * Stops timer TODO show top ten
	 */
	private void winGame() {
		stopTimer();
		System.out.println("You won!");
	}

	private void startTimer() {
		time = 0;
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gui.setGameTime(++time);
			}
		};
		timer = new Timer(1000, listener);
		timer.start();
	}

	private void stopTimer() {
		if (timer != null && timer.isRunning()) {
			timer.stop();
		}
		this.gameStarted = false;
	}

	public int getRows() {
		return minefield.getRows();
	}

	public int getColumns() {
		return minefield.getColumns();
	}

	public int getMines() {
		return minefield.getMines();
	}

	public int getFlagsNumber() {
		return minefield.getFlagsNumber();
	}
}
