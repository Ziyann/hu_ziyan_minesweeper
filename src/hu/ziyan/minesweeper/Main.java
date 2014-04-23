package hu.ziyan.minesweeper;

import hu.ziyan.minesweeper.controller.MinesweeperController;

public final class Main {

	public static void main(final String[] args) {
		startApp();
	}

	/**
	 * Avoid instantiating.
	 */
	private Main() {

	}

	/**
	 * Starts the app.
	 */
	private static void startApp() {
		final MinesweeperController control = new MinesweeperController();

		control.startDesktop();
	}
}
