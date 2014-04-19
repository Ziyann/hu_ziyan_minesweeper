package hu.ziyan.minesweeper.view;

import hu.ziyan.minesweeper.controller.MinesweeperController;
import hu.ziyan.minesweeper.model.Minefield;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MinesweeperGUI {

	private JFrame window;
	private MinesweeperController controller;
	private BoardJPanel board;

	public void startGUI() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private void createAndShowGUI() {
		window = new JFrame(Labels.gui_title);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(dim.width / 5, dim.height / 5);

		MinesweeperMenuBar mMenuBar = new MinesweeperMenuBar(this);
		window.setJMenuBar(mMenuBar);

		controller.newGame(Minefield.DIFFICULTY_BEGINNER);
	}

	public void showBoardPanel() {
		board = new BoardJPanel(this);
		setActualContent(board);
	}

	private void setActualContent(Container container) {
		window.setContentPane(container);
		window.pack();
		window.setVisible(true);
	}

	public JFrame getWindow() {
		return window;
	}

	public MinesweeperController getController() {
		return controller;
	}

	public MinesweeperGUI(MinesweeperController controller) {
		this.controller = controller;
	}

	public void setGameTime(int time) {
		board.setGameTime(time);
	}

	public void revealNumberField(int row, int column, int number) {
		board.revealNumberField(row, column, number);
	}

	public void revealMine(int row, int column) {
		board.revealMine(row, column);
	}

	public void revealEmptyPosition(int row, int column) {
		board.revealEmptyPosition(row, column);
	}

	public void removeFlag(int row, int column) {
		board.removeFlag(row, column);
	}

	public void placeFlag(int row, int column) {
		board.placeFlag(row, column);
	}
}
