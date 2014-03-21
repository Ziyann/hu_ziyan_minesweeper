/**
 * @author Járai Dániel
 *
 */
package hu.ziyan.minesweeper;

import hu.ziyan.minesweeper.controller.MinesweeperController;

public class Main {

	public static void main(String[] args) {
		startApp();
	}
	
	/**
     * Starts the app.
     */
	private static void startApp() {
        MinesweeperController control = new MinesweeperController();

        control.startDesktop();
    }
}
