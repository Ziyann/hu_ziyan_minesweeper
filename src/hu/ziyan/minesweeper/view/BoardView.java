package hu.ziyan.minesweeper.view;

interface BoardView {
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
	 * @param column
	 * 
	 * @param content
	 *            Content of the position. 0 to 8 indicates the nearby mines
	 *            number, or -1 means it's a mine.
	 */
	void revealPosition(int row, int column, int content);

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
	 * @param column
	 * 
	 */
	void removeFlag(int row, int column);

	/**
	 * Places a flag to the given position.
	 * 
	 * @param row
	 * @param column
	 * 
	 */
	void placeFlag(int row, int column);
}
