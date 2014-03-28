package hu.ziyan.minesweeper.view;

import hu.ziyan.minesweeper.controller.MinesweeperController;

import java.awt.Container;

import javax.swing.JFrame;

public class MinesweeperGUI {

	private JFrame window;
	private MinesweeperController controller;

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

		showBoardPanel(9, 9, 10, "Kezdõ");
	}

	public void showBoardPanel(int sorokSzama, int oszlopokSzama,
			int aknakSzama, String nehezsegiSzint) {
		BoardJPanel board = new BoardJPanel(this, sorokSzama, oszlopokSzama,
				aknakSzama, nehezsegiSzint);
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
}
