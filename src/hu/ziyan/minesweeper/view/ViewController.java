package hu.ziyan.minesweeper.view;

import hu.ziyan.minesweeper.controller.MinesweeperController;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class ViewController {

	private JFrame window;
	private MinesweeperController controller;
	private BoardViewImpl board;

	public void startGUI() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private void createAndShowGUI() {
		window = new JFrame(Labels.game_name);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(dim.width / 5, dim.height / 5);

		

		controller.newGame(9, 9, 10);
	}

	public void showBoardPanel() {
		BoardMenuBar menuBar = new BoardMenuBar(this);
		window.setJMenuBar(menuBar);
		board = new BoardViewImpl(this);
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

	public ViewController(MinesweeperController controller) {
		this.controller = controller;
	}

	public void setGameTime(int time) {
		board.setGameTime(time);
	}

	public void revealPosition(int row, int column, int content) {
		board.revealPosition(row, column, content);
	}

	public void removeFlag(int row, int column) {
		board.removeFlag(row, column);
		board.setFlagsNumber(getController().getFlagsNumber());
	}

	public void placeFlag(int row, int column) {
		board.placeFlag(row, column);
		board.setFlagsNumber(getController().getFlagsNumber());
	}

	public void setFlagsNumber(int flagsNumber) {
		board.setFlagsNumber(getController().getFlagsNumber());
	}
}
