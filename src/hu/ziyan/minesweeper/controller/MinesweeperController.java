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

	/**
	 * true if at least one field has been revealed
	 */
	private boolean gameStarted;

	/**
	 * Starts the desktop GUI
	 */
	public void startDesktop() {
		gui = new MinesweeperGUI(this);
		gui.startGUI();
	}

	/**
	 * @return current difficulty
	 */
	public int getDifficulty() {
		return minefield.getDifficulty();
	}

	public Minefield getMinefield() {
		return this.minefield;
	}

	public void newGame(int fieldDifficulty) {
		this.minefield = new Minefield(fieldDifficulty);
		service = new MinefieldService(this, this.minefield);
		service.makeField();
		gui.showBoardPanel();
	}

	public void newGame(int rows, int columns, int mines) {
		this.minefield = new Minefield(rows, columns, mines);
		service = new MinefieldService(this, this.minefield);
		service.makeField();
		gui.showBoardPanel();
	}

	public boolean isGameStarted() {
		return gameStarted;
	}

	public void startGame() {
		this.gameStarted = true;
		startTimer();
	}

	public void revealField(int row, int column) {
		if (!isGameStarted()) {
			while (minefield.getButtonField(row, column).isMine()) {
				if (minefield.getDifficulty() != Minefield.DIFFICULTY_CUSTOM) {
					newGame(minefield.getDifficulty());
				} else {
					newGame(minefield.getColumns(), minefield.getRows(), minefield.getMines());
				}
			}
			startGame();
		}
		service.revealField(row, column);
	}

	/**
	 * Stops timer
	 */
	public void loseGame() {
		timer.stop();
		service.revealMinefield();
		Object[] options = { Labels.new_game, Labels.exit_label };
		int valasztas = JOptionPane.showOptionDialog(gui.getWindow(), "Vesztettél! Mit kívánsz tenni?",
				Labels.gui_title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (valasztas == JOptionPane.YES_OPTION) {
			if (minefield.getDifficulty() != Minefield.DIFFICULTY_CUSTOM) {
				newGame(minefield.getDifficulty());
			} else {
				newGame(minefield.getColumns(), minefield.getRows(), minefield.getMines());
			}
		} else {
			System.exit(0);
		}
	}

	/**
	 * Stops timer TODO show top ten
	 */
	public void winGame() {
		timer.stop();
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
}
