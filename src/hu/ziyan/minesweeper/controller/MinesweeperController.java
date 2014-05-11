package hu.ziyan.minesweeper.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import hu.ziyan.minesweeper.model.Minefield;
import hu.ziyan.minesweeper.model.MinefieldImpl;
import hu.ziyan.minesweeper.view.Labels;
import hu.ziyan.minesweeper.view.ViewController;

public class MinesweeperController {
	private Minefield minefield;
	private ViewController gui;
	private final Timer timer;
	private int time;
	private boolean isRevealingRunning;

	public static final int MAX_ROWS = 24;
	public static final int MIN_ROWS = 9;
	public static final int MAX_COLUMNS = 30;
	public static final int MIN_COLUMNS = 9;
	public static final int MIN_MINES = 10;

	public static final int MINE = -1;

	public MinesweeperController() {
		final ActionListener listener = new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				gui.setGameTime(++time);
			}
		};
		timer = new Timer(1000, listener);
	}

	/**
	 * Starts the desktop GUI
	 */
	public void startDesktop() {
		gui = new ViewController(this);
		gui.startGUI();
	}

	public void newGame(int rows, int columns, int mines) {
		if (rows > MAX_ROWS) {
			rows = MAX_ROWS;
		} else if (rows < MIN_ROWS) {
			rows = MIN_ROWS;
		}
		if (columns > MAX_COLUMNS) {
			columns = MAX_COLUMNS;
		} else if (columns < MIN_COLUMNS) {
			columns = MIN_COLUMNS;
		}
		if (mines > ((rows * columns) * 0.9)) {
			mines = (int) ((rows * columns) * 0.9);
		} else if (mines < MIN_MINES) {
			mines = MIN_MINES;
		}

		this.minefield = new MinefieldImpl(rows, columns, mines, this);
		stopTimer();
		gui.showBoardPanel();
	}

	/**
	 * Reveals the specified position
	 * 
	 * @param row
	 * @param column
	 */
	public void revealPosition(final int row, final int column) {
		if (row >= 0 && row < getRows() && column >= 0 && column < getColumns()) {
			removeFlag(row, column);
			if (minefield.isMine(row, column)) {
				gui.revealPosition(row, column, MINE);
				loseGame();
			} else {
				gui.revealPosition(row, column, minefield.getNearbyMinesNumber(row, column));
				if (minefield.isHiddenAndEmpty(row, column)) {
					revealNearbyEmptyFields(row, column);
				}
				minefield.reveal(row, column);
			}
			winGameIfOver();
		}
	}

	private void winGameIfOver() {
		if (minefield.getRemainingFields() == 0 && timer.isRunning()) {
			stopTimer();
			showEndgameDialog();
		}
	}

	private void revealNearbyEmptyFields(final int row, final int column) {
		if (!isRevealingRunning) {
			isRevealingRunning = true;
			minefield.revealNearbyEmptyFields(row, column);
			isRevealingRunning = false;
		}
	}

	/**
	 * A click on a hidden position is passed here first, without any checks
	 * before the call.
	 * 
	 * @param row
	 * @param column
	 */
	public void fieldClick(final int row, final int column) {
		if (minefield.isFlagged(row, column)) {
			removeFlag(row, column);
		} else {
			if (!timer.isRunning()) {
				while (minefield.isMine(row, column)) {
					newGame(getRows(), getColumns(), getMines());
				}
				startTimer();
			}
			revealPosition(row, column);
		}
	}

	public void placeFlag(final int row, final int column) {
		minefield.placeFlag(row, column);
		gui.placeFlag(row, column);
	}

	private void removeFlag(final int row, final int column) {
		if (minefield.isFlagged(row, column)) {
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
		showGameLostDialog();
	}

	/**
	 * Shows a dialog with two options: new game or exit
	 */
	private void showGameLostDialog() {
		final Object[] options = { Labels.NEW_GAME, Labels.EXIT };
		final int valasztas = JOptionPane.showOptionDialog(gui.getWindow(), "Vesztettél! Mit kívánsz tenni?",
				Labels.MINESWEEPER, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (valasztas == JOptionPane.YES_OPTION) {
			newGame(getRows(), getColumns(), getMines());
		} else {
			System.exit(0);
		}
	}

	private void revealMines() {
		for (int row = 0; row < minefield.getRows(); row++) {
			for (int column = 0; column < minefield.getColumns(); column++) {
				if (minefield.isMine(row, column)) {
					gui.revealPosition(row, column, MINE);
				}
			}
		}
	}

	private void showEndgameDialog() {
		// TODO implement
	}

	private void startTimer() {
		time = 0;
		timer.start();
	}

	private void stopTimer() {
		if (timer != null && timer.isRunning()) {
			timer.stop();
		}
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
