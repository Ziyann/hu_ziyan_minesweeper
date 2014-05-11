package hu.ziyan.minesweeper.view;

import hu.ziyan.minesweeper.controller.MinesweeperController;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class ViewController {
	private JFrame window;
	private final MinesweeperController controller;
	private BoardViewImpl board;

	public void startGUI() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private void createAndShowGUI() {
		window = new JFrame(Labels.MINESWEEPER);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(dim.width / 5, dim.height / 5);

		controller.newGame(9, 9, 10);
	}

	public void showBoardPanel() {
		final BoardMenuBar menuBar = new BoardMenuBar(this);
		window.setJMenuBar(menuBar);
		board = new BoardViewImpl(this);
		setActualContent(board);
	}

	private void setActualContent(final Container container) {
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

	public ViewController(final MinesweeperController controller) {
		this.controller = controller;
	}

	public void setGameTime(final int time) {
		board.setGameTime(time);
	}

	public void revealPosition(final int row, final int column, final int content) {
		board.revealPosition(row, column, content);
	}

	public void removeFlag(final int row, final int column) {
		board.removeFlag(row, column);
		board.setFlagsNumber(getController().getFlagsNumber());
	}

	public void placeFlag(final int row, final int column) {
		board.placeFlag(row, column);
		board.setFlagsNumber(getController().getFlagsNumber());
	}
}
