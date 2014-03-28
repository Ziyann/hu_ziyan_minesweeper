package hu.ziyan.minesweeper.view;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JButton;

public class JButtonField extends JButton {
	private static final long serialVersionUID = 8816893946270507611L;

	public JButtonField() {
		super("");
		this.setPreferredSize(new Dimension(25, 25));
		this.setMargin(new Insets(1, 1, 1, 1));
		this.setFont(this.getFont().deriveFont(20.0f));
	}

	public void placeFlag() {
		this.setText("P");
	}

	public void removeFlag() {
		this.setText("");
	}

	public void reveal(int mines) {
		this.setEnabled(false);
		if (mines == 1) {
			this.setText("<html><font color=\"blue\">1</font></html>");
		} else if (mines == 2) {
			this.setText("<html><font color=\"orange\">2</font></html>");
		} else if (mines > 2) {
			this.setText("<html><font color=\"red\">" + "number" + "</font></html>");
		}
	}
}
