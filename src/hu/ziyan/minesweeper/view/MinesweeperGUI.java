package hu.ziyan.minesweeper.view;

import hu.ziyan.minesweeper.controller.MinesweeperController;
import hu.ziyan.minesweeper.model.Minefield;

import java.awt.Container;

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
		String title = Labels.gui_title;

		window = new JFrame(title);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MinesweeperMenuBar mMenuBar = new MinesweeperMenuBar(this);
		window.setJMenuBar(mMenuBar);

		controller.newGame(Minefield.DIFFICULTY_BEGINNER);
	}
	

	public int getRows() {
		return controller.getMinefield().getRows();
	}
	
	public int getColumns() {
		return controller.getMinefield().getColumns();
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
	
	public void increaseTimer() {
		board.increaseTimer();
	}
}
