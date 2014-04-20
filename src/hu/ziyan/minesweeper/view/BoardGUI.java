package hu.ziyan.minesweeper.view;

interface BoardGUI {
	/**
	 * Sets the elapsed game time.
	 * 
	 * @param time
	 *            elapsed time in seconds
	 */
	void setGameTime(int time);

	/**
	 * Reveals a position, indicating the number of nearby mines.
	 * 
	 * @param row
	 *            row of the field
	 * @param column
	 *            column of the field
	 * @param nearbyMines
	 *            nearby mines around the position (can be 0)
	 */
	void revealPosition(int row, int column, int nearbyMines);

	/**
	 * Updates the flags counter
	 * 
	 * @param flagsNumber
	 *            current number of flags
	 */
	void setFlagsNumber(int flagsNumber);

	/**
	 * Removes the flag from the given position.
	 * 
	 * @param row
	 *            row of the field
	 * @param column
	 *            column of the field
	 */
	void removeFlag(int row, int column);

	/**
	 * Places a flag to the given position.
	 * 
	 * @param row
	 *            row of the field
	 * @param column
	 *            column of the field
	 */
	void placeFlag(int row, int column);

	/**
	 * Reveals a mine at the given position.
	 * 
	 * @param row
	 *            row of the field
	 * @param column
	 *            column of the field
	 */
	void revealMine(int row, int column);
}
