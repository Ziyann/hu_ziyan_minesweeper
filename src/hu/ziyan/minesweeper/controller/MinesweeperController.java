package hu.ziyan.minesweeper.controller;

import hu.ziyan.minesweeper.view.MinesweeperGUI;

import javax.swing.UIManager;

public class MinesweeperController {

	/**
     * Starts the desktop GUI
     */
	public void startDesktop() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.err
					.println("Couldn't get specified look and feel for some reason.");
			System.err.println("Using the default look and feel.");
			e.printStackTrace();
		}
		
		MinesweeperGUI vc = new MinesweeperGUI(this);

	    vc.startGUI();
	}

}
