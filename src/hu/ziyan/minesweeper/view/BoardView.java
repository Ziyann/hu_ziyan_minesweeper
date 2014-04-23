package hu.ziyan.minesweeper.view;

interface BoardView {
	/**
	 * Sets the elapsed game time.
	 * 
	 * @param time
	 *            elapsed time in seconds
	 */
	void setGameTime(final int time);

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
	void revealPosition(final int row, final int column, final int content);

	/**
	 * Updates the flags counter
	 * 
	 * @param flagsNumber
	 *            current number of flags
	 */
	void setFlagsNumber(final int flagsNumber);

	/**
	 * Removes the flag from the given position.
	 * 
	 * @param row
	 * @param column
	 * 
	 */
	void removeFlag(final int row, final int column);

	/**
	 * Places a flag to the given position.
	 * 
	 * @param row
	 * @param column
	 * 
	 */
	void placeFlag(final int row, final int column);
}
