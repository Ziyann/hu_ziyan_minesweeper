package hu.ziyan.minesweeper.controller;

import hu.ziyan.minesweeper.view.MinesweeperGUI;

public class MinesweeperController {

	/**
     * Starts the desktop GUI
     */
	public void startDesktop() {
		MinesweeperGUI vc = new MinesweeperGUI(this);

	    vc.startGUI();
	}

}
