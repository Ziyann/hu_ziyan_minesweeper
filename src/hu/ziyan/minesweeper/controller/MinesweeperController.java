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

	public Minefield getMinefield() {
		return this.minefield;
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
			while (minefield.getButtonField(row, column).isMine()) {
				newGame();
			}
			startGame();
		}
		service.revealField(row, column);
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

	/**
	 * Stops timer and displays a dialog (new game or exit)
	 */
	public void loseGame() {
		stopTimer();
		service.revealMinefield();
		Object[] options = { Labels.new_game, Labels.exit_label };
		int valasztas = JOptionPane.showOptionDialog(gui.getWindow(), "Vesztettél! Mit kívánsz tenni?",
				Labels.gui_title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (valasztas == JOptionPane.YES_OPTION) {
			newGame();
		} else {
			System.exit(0);
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
}
