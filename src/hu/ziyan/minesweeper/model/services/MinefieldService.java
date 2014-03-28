package hu.ziyan.minesweeper.model.services;

import hu.ziyan.minesweeper.controller.MinesweeperController;
import hu.ziyan.minesweeper.model.Minefield;
import hu.ziyan.minesweeper.view.JButtonField;

import java.util.Random;

public class MinefieldService {
	private Minefield minefield;
	private MinesweeperController controller;

	public MinefieldService(MinesweeperController controller, Minefield minefield) {
		this.controller = controller;
		this.minefield = minefield;
	}
	
	public JButtonField getFieldButton(int row, int culomn) {
		return null;
	}
	
	public void makeField() {
		/*
		 * Make 2D array
		 */
		JButtonField[][] field = new JButtonField[minefield.getRows()][minefield.getColumns()];
		for (int row = 0; row < minefield.getRows(); ++row) {
			for (int column = 0; column < minefield.getColumns(); ++column) {
				field[row][column] = new JButtonField(this.controller, row, column);
			}
		}
		
		/*
		 * Place mines
		 */
		for (int i = 0; i < minefield.getMines(); ++i) {
			Random veletlen = new Random();
			while (true) {
				int randomRow = veletlen.nextInt(minefield.getRows());
				int randomColumn = veletlen.nextInt(minefield.getColumns());
				if (field[randomRow][randomColumn].isMine() == false) {
					field[randomRow][randomColumn].makeMine();
					break;
				}
			}
		}
		
		/*
		 * Place numbers indicating nearby mines
		 */
		for (int row = 0; row < minefield.getRows(); row++) {
			for (int column = 0; column < minefield.getColumns(); column++) {
				if (field[row][column].isMine()) { // skip mines
					continue;
				}
				int nearbyMines = 0;
				if (row < minefield.getRows() - 1 && column < minefield.getColumns() - 1 && field[row + 1][column + 1].isMine()) {
					++nearbyMines;
				}
				if (row < minefield.getRows() - 1 && field[row + 1][column].isMine()) {
					++nearbyMines;
				}
				if (column > 0 && row < minefield.getRows() - 1 && field[row + 1][column - 1].isMine()) {
					++nearbyMines;
				}
				if (row > 0 && column > 0 && field[row - 1][column - 1].isMine()) {
					++nearbyMines;
				}
				if (row > 0 && column < minefield.getColumns() - 1 && field[row - 1][column + 1].isMine()) {
					++nearbyMines;
				}
				if (row > 0 && field[row - 1][column].isMine()) {
					++nearbyMines;
				}
				if (column < minefield.getColumns() - 1 && field[row][column + 1].isMine()) {
					++nearbyMines;
				}
				if (column > 0 && field[row][column - 1].isMine()) {
					++nearbyMines;
				}
				field[row][column].setNearbyMines(nearbyMines);
			}
		}
		
		minefield.setField(field);
	}

	public void revealField(int row, int column) {
		minefield.decreaseRemainingFields();
		minefield.getButtonField(row, column).reveal();
	}
	
	public void revealMinefield() {
		JButtonField[][] field = minefield.getButtonField();
		for (int i = 0; i < minefield.getRows(); i++) {
			for (int j = 0; j < minefield.getColumns(); j++) {
				if (field[i][j].isFlagged() && field[i][j].isMine()) {
					field[i][j].showFlaggedMine();
				} else if (field[i][j].isMine()) {
					field[i][j].reveal();
				}
			}
		}
	}
}