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
	private boolean isRevealingRunning = false;

	/**
	 * true if at least one field has been revealed
	 */
	private boolean gameRunning = false;

	/**
	 * Starts the desktop GUI
	 */
	public void startDesktop() {
		gui = new MinesweeperGUI(this);
		gui.startGUI();
	}

	public void newGame(int fieldDifficulty) {
		this.minefield = new Minefield(fieldDifficulty);
		service = new MinefieldService(this, this.minefield);
		service.makeField();
		stopTimer();
		gui.showBoardPanel();
	}

	public void newGame(int rows, int columns, int mines) {
		this.minefield = new Minefield(rows, columns, mines);
		service = new MinefieldService(this, this.minefield);
		service.makeField();
		stopTimer();
		gui.showBoardPanel();
	}

	public void newGame() {
		service.makeField();
		stopTimer();
		gui.showBoardPanel();
	}

	public void revealField(int row, int column) {
		if (!gameRunning) {
			while (minefield.getField(row, column).isMine()) {
				newGame();
			}
			startGame();
		}
		if (minefield.getField(row, column).isHiddenAndEmpty()) {
			gui.revealEmptyPosition(row, column);
			revealNearbyEmptyFields(row, column);
		} else if (minefield.getField(row, column).isMine()) {
			gui.revealMine(row, column);
			loseGame();
		} else {
			gui.revealNumberField(row, column, minefield.getField(row, column).getNearbyMinesNumber());
		}
		minefield.reveal(row, column);
		if (minefield.getRemainingFields() == 0 && this.gameRunning) {
			this.gameRunning = false;
			winGame();
		}
	}

	public void revealNearbyEmptyFields(int row, int column) {
		if (!isRevealingRunning) {
			isRevealingRunning = true;
			service.revealNearbyEmptyFields(row, column);
			isRevealingRunning = false;
		}
	}
	
	public void fieldClick(int row, int column) {
		if(isFlagged(row, column)) {
			removeFlag(row, column);
		} else {
			revealField(row, column);
		}
	}

	public boolean isFlagged(int row, int column) {
		return minefield.isFlagged(row, column);
	}

	public void placeFlag(int row, int column) {
		minefield.placeFlag(row, column);
		gui.placeFlag(row, column);
	}

	public void removeFlag(int row, int column) {
		minefield.removeFlag(row, column);
		gui.removeFlag(row, column);
	}

	/**
	 * Stops timer, reveals minefield and displays a dialog (new game or exit)
	 */
	public void loseGame() {
		stopTimer();
		revealMines();
		Object[] options = { Labels.new_game, Labels.exit_label };
		int valasztas = JOptionPane.showOptionDialog(gui.getWindow(), "Vesztettél! Mit kívánsz tenni?",
				Labels.gui_title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (valasztas == JOptionPane.YES_OPTION) {
			newGame();
		} else {
			System.exit(0);
		}
	}
	
	public void revealMines() {
		for(int row = 0; row < minefield.getRows(); row++) {
			for(int column = 0; column < minefield.getColumns(); column++) {
				if (minefield.getField(row, column).isMine()) {
					gui.revealMine(row, column);
				}
			}
		}
	}

	private void startGame() {
		this.gameRunning = true;
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
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gui.increaseTimer();
			}
		};
		timer = new Timer(1000, listener);
		timer.start();
	}

	private void stopTimer() {
		if (timer != null && timer.isRunning()) {
			timer.stop();
		}
		this.gameRunning = false;
	}

	public int getRows() {
		return minefield.getRows();
	}

	public int getColumns() {
		return minefield.getColumns();
	}
}
